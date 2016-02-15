package pl.jbujak.simulator.gui;

import pl.jbujak.simulator.cmd.CommandLine;
import static org.lwjgl.opengl.GL11.*;

public class CommandLineDrawer implements IDrawable{


	private final String fontPath = "/fonts/DroidSansMono.ttf";
	private final String prompt = "$";
	private final int fontSize = 40;
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
			glVertex2d(0, -windowHeight/2);

			glVertex2d(0, 0);

			glVertex2d(windowWidth, 0);
			
			glVertex2d(windowWidth, -windowHeight/2);
		glEnd();
		glDisable(GL_DEPTH_TEST);
		glTranslated(0, -2 * fontSize, 0);
		glEnable(GL_TEXTURE_2D);
		glColor4d(0, 0, 0, 1);
		font.drawText(prompt, 0, 0);
		font.drawText(CommandLine.getCommand(), prompt.length() * fontSize, 0);
		glDisable(GL_TEXTURE_2D);

		glPopMatrix();
	}
}
