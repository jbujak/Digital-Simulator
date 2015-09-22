package pl.jbujak.simulator.gui;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GLContext;

public class CameraEngine {

	private double xPos;
	private double yPos;
	private double zPos;
	private double phi;
	private double theta;

	public CameraEngine() {
		GLContext.createFromCurrent();
		updateCamera();
	}
	
	public void rotateTo(double phi, double theta) {
		this.phi = phi;
		this.theta = theta;

		updateCamera();
	}

	public void translateTo(double xPos, double yPos, double zPos) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.zPos = zPos;
		
		updateCamera();
	}
	
	private void updateCamera()
	{
		glLoadIdentity();
		glRotated(phi, 0, 1, 0);
		glRotated(theta, Math.cos(Math.toRadians(phi)), 0, Math.sin(Math.toRadians(phi)));
		glTranslated(-xPos, -yPos, -zPos);
	}
	
}
