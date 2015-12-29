package pl.jbujak.simulator.gui;

import static org.lwjgl.opengl.GL11.*;

import pl.jbujak.simulator.Simulation;

public class Menu implements IDrawable {
	private final String fontPath = "/fonts/DroidSansMono.ttf";
	private static boolean isCreated = false;
	private static Menu instance;
	private String[] menuElements;
	private Font font;

	public static Menu getInstance() {
		if (!isCreated) {
			instance = new Menu();
		}
		isCreated = true;
		return instance;
	}

	@Override
	public void draw(int windowWidth, int windowHeight) {
		if (!Simulation.isMenuOpen()) {
			return;
		}

		glDisable(GL_TEXTURE_2D);
		double menuPositionWidth = 0.7 * windowWidth;
		double menuPositionHeight = windowHeight / (2 * menuElements.length + 1);
		double spaceBetweenMenuElements = (windowHeight - menuElements.length * menuPositionHeight)
				/ (menuElements.length + 1);

		glPushMatrix();
		glLoadIdentity();
		glTranslated(windowWidth / 2, 0, 0);
		glTranslated(0, menuPositionHeight/2, 0);

		glColor3f(0, 0, 0);
		
		
		for (int i = 0; i < menuElements.length; i++) {
			glTranslated(0, spaceBetweenMenuElements, 0);

			glPushMatrix();
			glEnable(GL_TEXTURE_2D);
			glColor3d(0, 0, 0);
			glTranslated(-font.getStringWidth(menuElements[i])/2, -font.getCharHeight()/2, 0);
			font.drawText(menuElements[i], 0, 0);
			glDisable(GL_TEXTURE_2D);
			glPopMatrix();

			glColor3d(0.5, 0.5, 0.5);
			glBegin(GL_QUADS);
			glVertex2d(-menuPositionWidth / 2, -menuPositionHeight / 2);
			glVertex2d(menuPositionWidth / 2, -menuPositionHeight / 2);
			glVertex2d(menuPositionWidth / 2, menuPositionHeight / 2);
			glVertex2d(-menuPositionWidth / 2, menuPositionHeight / 2);
			glEnd();
			
			glTranslated(0, menuPositionHeight, 0);
		}

		glPopMatrix();

	}

	private Menu() {
		menuElements = new String[] { "NEW WORLD", "SAVE", "LOAD", "HELP", "EXIT" };
		try {
			font = new Font(fontPath, 50);
		} catch (Exception e) {
			e.printStackTrace();
		}


		
	}
}
