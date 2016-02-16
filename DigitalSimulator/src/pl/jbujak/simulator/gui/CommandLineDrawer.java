package pl.jbujak.simulator.gui;

import pl.jbujak.simulator.cmd.CommandLine;
import static org.lwjgl.opengl.GL11.*;

public class CommandLineDrawer implements IDrawable{


	private final String fontPath = "/fonts/DroidSansMono.ttf";
	private final String prompt = "$";
	private final int fontSize = 20;
	private final double consoleHeight = 0.75;
	private Font font;
	
	public CommandLineDrawer() {
		try {
			font = new Font(fontPath, fontSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void draw(int windowWidth, int windowHeight) {
		if(!CommandLine.isOpen()) {
			return;
		}
		glPushMatrix();
		
		glTranslated(0, windowHeight, 0);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_ALPHA_TEST);
		glColor4d(0.2, 0.2, 0.2, 0.6);
		glBegin(GL_QUADS);
			glVertex2d(0, -windowHeight * consoleHeight);

			glVertex2d(0, 0);

			glVertex2d(windowWidth, 0);
			
			glVertex2d(windowWidth, -windowHeight * consoleHeight);
		glEnd();
		drawText(windowHeight);

		glPopMatrix();
	}
	
	private void drawText(int windowHeight) {
		double startHeight = 2.5 * font.getCharHeight();
		
		glDisable(GL_DEPTH_TEST);
		glTranslated(0, -startHeight, 0);
		glEnable(GL_TEXTURE_2D);
		glColor4d(0, 0, 0, 1);
		font.drawText(prompt, 0, 0);
		font.drawText(CommandLine.getCommand(), (int)(prompt.length() * font.getCharHeight()), 0);
		
		int spaceForHistory = (int)((windowHeight * consoleHeight) - startHeight);
		int linesForHistory = (int)(spaceForHistory / font.getCharHeight());
		
		String history = CommandLine.getHistory();
		if(history == null) {
			return;
		}
		//history = history.substring(5);
		String[] historyLine = history.split("\n");
		
		for(int i = historyLine.length-1; i >= 0; i--) {
			if(historyLine.length - 1 - i >= linesForHistory)
				break;

			glTranslated(0, -font.getCharHeight(), 0);
			font.drawText(historyLine[i], 0, 0);
		}

		glDisable(GL_TEXTURE_2D);
	}
}
