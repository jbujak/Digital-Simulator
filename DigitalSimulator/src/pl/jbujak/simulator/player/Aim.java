package pl.jbujak.simulator.player;

import static org.lwjgl.opengl.GL11.*;
import pl.jbujak.simulator.gui.IDrawable;

public class Aim implements IDrawable {
	
	private final double sizeOfAim = 0.0006;

	@Override
	public void draw() {
		glPushMatrix();
		glLoadIdentity();

		glColor3d(0, 0, 0);
		glBegin(GL_QUADS);
		glVertex3d(sizeOfAim, -sizeOfAim, -0.2);
		glVertex3d(sizeOfAim, sizeOfAim, -0.2);
		glVertex3d(-sizeOfAim, sizeOfAim, -0.2);
		glVertex3d(-sizeOfAim, -sizeOfAim, -0.2);
		glEnd();
		glColor3d(1, 1, 1);

		glPopMatrix();


	}

}
