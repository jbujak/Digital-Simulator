package pl.jbujak.simulator.gui;

import pl.jbujak.simulator.Simulation;
import pl.jbujak.simulator.player.Inventory;
import static org.lwjgl.opengl.GL11.*;

public class InventoryDrawer implements IDrawable {
	
	public InventoryDrawer(Inventory inventory) {
	}

	@Override
	public void draw(int windowWidth, int windowHeight) {
		if(!Simulation.isInventoryOpen()) {
			return;
		}
		
		double sizeX = 500;
		double sizeY = 300;
		glPushMatrix();
		glLoadIdentity();
		glTranslated(windowWidth/2, windowHeight/2, 0);
		
		glColor3d(0, 0, 0);
		
		glBegin(GL_QUADS);
		glVertex2d(-sizeX/2, -sizeY/2);
		glVertex2d(-sizeX/2, sizeY/2);
		glVertex2d(sizeX/2, sizeY/2);
		glVertex2d(sizeX/2, -sizeY/2);
		glEnd();
		
		
		glColor3d(1, 1, 1);

		glPopMatrix();
	}

}
