package pl.jbujak.simulator.gui;

import static org.lwjgl.opengl.GL11.*;

import pl.jbujak.simulator.Simulation;
import pl.jbujak.simulator.input.CursorProcessor;
import pl.jbujak.simulator.utils.Position;
import pl.jbujak.simulator.world.LoadManager;
import pl.jbujak.simulator.world.SaveManager;
import pl.jbujak.simulator.world.World;

public class Menu implements IDrawable {
	private final String fontPath = "/fonts/DroidSansMono.ttf";
	private static Menu instance;
	private Font font;
	private int windowWidth;
	
	private static boolean isOpen;
	private static double menuPositionHeight;
	private static double menuPositionWidth;
	private static double spaceBetweenMenuElements;
	

	public static Menu getInstance() {
		if (instance == null) {
			instance = new Menu();
		}
		return instance;
	}
	
	public static void open() {
		Simulation.pause();
		isOpen = true;

		Simulation.showCursor();
		Simulation.centerCursor();
	}
	
	public static void close() {
		Simulation.unpause();
		isOpen = false;

		Simulation.hideCursor();
	}
	
	public static boolean isOpen() {
		return isOpen;
	}

	@Override
	public void draw(int windowWidth, int windowHeight) {
		if(!isOpen) {
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
			SaveManager.save(World.instance);
			Menu.close();
			break;
		case LOAD:
			LoadManager.load(World.instance);
			Menu.close();
			break;
		case EXIT:
			Simulation.exit();
			break;
		default:
			break;
		}
	}
}
