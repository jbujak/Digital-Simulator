package pl.jbujak.simulator.gui;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GLContext;

import pl.jbujak.simulator.world.IWorld;

public class RenderEngine {
	private long windowHandle;
	private IWorld world;
	private int windowWidth;
	private int windowHeight;
	
	private VBOEngine vboEngine;
	
	public RenderEngine(long windowHandle, IWorld world) {
		this.windowHandle = windowHandle;
		this.world = world;

		GLContext.createFromCurrent();
		
		this.vboEngine = new VBOEngine(world);

		updateScene();
		render();
	}

	public void updateScene() {
		IntBuffer widthBuffer = BufferUtils.createIntBuffer(4);
		IntBuffer heightBuffer = BufferUtils.createIntBuffer(4);
		glfwGetWindowSize(windowHandle, widthBuffer, heightBuffer);
		
		windowWidth= widthBuffer.get(0);
		windowHeight= heightBuffer.get(0);

		glViewport(0, 0, windowWidth, windowHeight);
		setTo3D();
	}
	
	private void setTo2D() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0.0, windowWidth, windowHeight, 0.0, -1.0, 10.0);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}
	
	private void setTo3D() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		double aspect = windowHeight / (double) windowWidth;
		glFrustum(-0.1, 0.1, aspect * -0.1, aspect * 0.1, 0.09, 100.0);

		glClearColor(1, 1, 1, 1);

		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_DEPTH_TEST);

	}
	
	public void render() {
		if(world.blocksChanged()) {
			vboEngine.update();
		}
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		setTo3D();
		vboEngine.draw();
		DrawEngine.draw3D(windowWidth, windowHeight);
		setTo2D();
		DrawEngine.draw2D(windowWidth, windowHeight);
		
		glfwSwapBuffers(windowHandle);
	}
}