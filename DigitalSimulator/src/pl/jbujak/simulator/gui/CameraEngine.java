package pl.jbujak.simulator.gui;

import static org.lwjgl.opengl.GL11.*;

import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import pl.jbujak.simulator.utils.*;

public class CameraEngine {

	private double xPos;
	private double yPos;
	private double zPos;
	private double phi;
	private double theta;
	private ModelViewMatrix matrix;

	public CameraEngine() {
		GLContext.createFromCurrent();
		matrix = new ModelViewMatrix();
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
		
		updateModelViewMatrix(); //TODO
	}
	
	private void updateModelViewMatrix() {
		DoubleBuffer buffer = BufferUtils.createDoubleBuffer(16 * 4);
		GL11.glGetDoublev(GL11.GL_MODELVIEW_MATRIX, buffer);
		buffer.rewind();
		
		matrix.set(buffer);

		System.out.println("Matrix:");
		System.out.print(matrix);
		System.out.println("Vector:");
		System.out.println(matrix.multiplyByVector(new Vector(1,1,1,1)) + "\n");
	}
	
}
