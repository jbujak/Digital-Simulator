package pl.jbujak.simulator.gui;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GLContext;

import pl.jbujak.simulator.utils.Position;


public class CameraEngine {

	Position position;
	long windowHandle;
	private double phi;
	private double theta;

	public CameraEngine(long windowHandle) {
		GLContext.createFromCurrent();
		this.windowHandle = windowHandle;
		position = new Position();
		updateCamera();
	}
	
	public void rotateTo(double phi, double theta) {
		this.phi = phi;
		this.theta = theta;

		updateCamera();
	}

	public void translateTo(Position position) {
		this.position = position;
		
		updateCamera();
	}
	
	private void updateCamera()
	{
		glLoadIdentity();
		glRotated(phi, 0, 1, 0);
		glRotated(theta, Math.cos(Math.toRadians(phi)), 0, Math.sin(Math.toRadians(phi)));
		glTranslated(-position.x, -position.y, -position.z);
	}
}
