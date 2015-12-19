package pl.jbujak.simulator.player;

import static org.lwjgl.opengl.GL11.*;

import pl.jbujak.simulator.Simulation;
import pl.jbujak.simulator.gui.IDrawable;

public class Aim implements IDrawable {
	
	private final double sizeOfAim;
	
	public Aim() {
		sizeOfAim = 3;
	}

	@Override
	public void draw(int windowWidth, int windowHeight) {
		if(Simulation.isInventoryOpen()) {
			return;
		}
		glPushMatrix();
		glLoadIdentity();
		glTranslated(windowWidth/2, windowHeight/2, 0);
	
		glColor4d(0, 0, 0, 1);

		glBegin(GL_QUADS);
		glVertex2d(-sizeOfAim, -sizeOfAim);
		glVertex2d(-sizeOfAim, sizeOfAim);
		glVertex2d(sizeOfAim, sizeOfAim);
		glVertex2d(sizeOfAim, -sizeOfAim);
		glEnd();

		glColor3d(1, 1, 1);

		glPopMatrix();
	}
}
