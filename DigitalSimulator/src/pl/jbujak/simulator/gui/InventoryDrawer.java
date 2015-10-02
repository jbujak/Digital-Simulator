package pl.jbujak.simulator.gui;

import pl.jbujak.simulator.Simulation;
import pl.jbujak.simulator.player.Inventory;
import static org.lwjgl.opengl.GL11.*;

public class InventoryDrawer implements IDrawable {
	
	public InventoryDrawer(Inventory inventory) {
	}

	@Override
	public void draw() {
		if(!Simulation.isInventoryOpen()) {
			return;
		}
		
		double sizeX = 0.2;
		double sizeY = 0.1;
		glPushMatrix();
		glLoadIdentity();
		
		glColor3d(0, 0, 0);
		
		glBegin(GL_QUADS);
		glVertex3d(sizeX/2, -sizeY/2, -0.1);
		glVertex3d(sizeX/2, sizeY/2, -0.1);
		glVertex3d(-sizeX/2, sizeY/2, -0.1);
		glVertex3d(-sizeX/2, -sizeY/2, -0.1);
		glEnd();
		
		
		glColor3d(1, 1, 1);

		glPopMatrix();
	}

}
