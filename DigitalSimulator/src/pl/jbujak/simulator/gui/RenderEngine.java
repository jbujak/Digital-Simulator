package pl.jbujak.simulator.gui;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GLContext;

import pl.jbujak.simulator.environment.IWorld;

public class RenderEngine {
	private long windowHandle;
	private IWorld world;
	
	private VBOEngine vboEngine;
	
	public RenderEngine(long windowHandle, IWorld world) {
		this.windowHandle = windowHandle;
		this.world = world;

		GLContext.createFromCurrent();
		
		this.vboEngine = new VBOEngine(world);
		vboEngine.prepareTextures();

		updateScene();
		render();
	}

	public void updateScene() {
		IntBuffer widthBuffer = BufferUtils.createIntBuffer(4);
		IntBuffer heightBuffer = BufferUtils.createIntBuffer(4);
		glfwGetWindowSize(windowHandle, widthBuffer, heightBuffer);
		
		int currentWidth = widthBuffer.get(0);
		int currentHeight = heightBuffer.get(0);

		glViewport(0, 0, currentWidth, currentHeight);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		double aspect = currentHeight / (double) currentWidth;
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
		
		vboEngine.draw();
		
		DrawEngine.drawSelectedBlockBorder(world.getSelectedBlock());
		
		glPushMatrix();
		glLoadIdentity();
		DrawEngine.drawAim();
		glPopMatrix();
		
		glfwSwapBuffers(windowHandle);
	}
}