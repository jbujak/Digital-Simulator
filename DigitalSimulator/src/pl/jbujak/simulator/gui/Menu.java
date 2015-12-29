package pl.jbujak.simulator.gui;

import static org.lwjgl.opengl.GL11.*;

import pl.jbujak.simulator.Simulation;
import pl.jbujak.simulator.input.CursorProcessor;
import pl.jbujak.simulator.utils.Position;

public class Menu implements IDrawable {
	private final String fontPath = "/fonts/DroidSansMono.ttf";
	private static boolean isCreated = false;
	private static Menu instance;
	private Font font;
	private int windowWidth;
	private static double menuPositionHeight;
	private static double menuPositionWidth;
	private static double spaceBetweenMenuElements;
	

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
		
		this.windowWidth = windowWidth;

		glDisable(GL_TEXTURE_2D);
		menuPositionWidth = 0.7 * windowWidth;
		menuPositionHeight = windowHeight / (2 * MenuElement.values().length + 1);
		spaceBetweenMenuElements = (windowHeight - MenuElement.values().length * menuPositionHeight)
				/ (MenuElement.values().length + 1);

		glPushMatrix();
		glLoadIdentity();
		glTranslated(windowWidth / 2, 0, 0);
		glTranslated(0, menuPositionHeight/2, 0);

		glColor3f(0, 0, 0);
		
		
		for (MenuElement element: MenuElement.values()) {
			glTranslated(0, spaceBetweenMenuElements, 0);

			glPushMatrix();
			glEnable(GL_TEXTURE_2D);
			glColor3d(0, 0, 0);
			glTranslated(-font.getStringWidth(element.toString())/2, -font.getCharHeight()/2, 0);
			font.drawText(element.toString(), 0, 0);
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
	
	public void menuClick() {
		if(clickedElement() == null) return;
		clickedElement().executeClick();
	}
	
	private Menu() {
		try {
			font = new Font(fontPath, 50);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private MenuElement clickedElement() {
		Position clickPosition = CursorProcessor.getCursorPosition();
		if(clickPosition.x < (windowWidth - menuPositionWidth) / 2) 
			//Too far on the left
			return null;

		if(clickPosition.x > (windowWidth + menuPositionWidth) / 2)
			//Too far on the right
			return null;
		
		double blockHeight = menuPositionHeight + spaceBetweenMenuElements;
		
		if ((clickPosition.y % blockHeight) < spaceBetweenMenuElements) {
			//Click between elements
			return null;
		}
		
		int clickedElement = (int) ((clickPosition.y - (clickPosition.y % blockHeight)) / (blockHeight));

		return MenuElement.values()[clickedElement];
	}
}

enum MenuElement {
	NEW_WORLD, SAVE, LOAD, EXIT;
	
	@Override
	public String toString() {
		switch(this) {
		case NEW_WORLD:
			return "NEW WORLD";
		case SAVE:
			return "SAVE";
		case LOAD:
			return "LOAD";
		case EXIT:
			return "EXIT";
		default:
			return "";
		}
	}
	
	public void executeClick() {
		switch (this) {
		case NEW_WORLD:
			Simulation.newWorld();
			break;
		case SAVE:
			break;
		case LOAD:
			break;
		case EXIT:
			Simulation.exit();
			break;
		default:
			break;
		}
	}
}
