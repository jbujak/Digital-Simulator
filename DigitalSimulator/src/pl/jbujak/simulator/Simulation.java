package pl.jbujak.simulator;

import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_FALSE;

import java.awt.EventQueue;

import org.lwjgl.opengl.GLContext;

import pl.jbujak.simulator.environment.GravityEngine;
import pl.jbujak.simulator.environment.World;
import pl.jbujak.simulator.gui.CameraEngine;
import pl.jbujak.simulator.gui.RenderEngine;
import pl.jbujak.simulator.gui.Window;
import pl.jbujak.simulator.input.CallbackProcessor;
import pl.jbujak.simulator.input.KeyboardProcessor;

public class Simulation {

	static private Window mainWindow;
	static private RenderEngine renderEngine;
	static private CameraEngine cameraEngine;
	static private World world;
	@SuppressWarnings("unused")
	// callbackProcessor created to avoid garbage collecting
	private static CallbackProcessor callbackProcessor;

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				mainWindow = new Window(700, 1400);
				cameraEngine = new CameraEngine(mainWindow.getWindowHandle());
				world = new World(cameraEngine);
				renderEngine = new RenderEngine(mainWindow.getWindowHandle(), world);
				callbackProcessor = new CallbackProcessor(renderEngine,
						mainWindow.getWindowHandle(), world.getPlayer());

				mainLoop();
			}
		});
	}

	private static void mainLoop() {


		long windowHandle = mainWindow.getWindowHandle();
		GLContext.createFromCurrent();
		KeyboardProcessor keyboardProcessor = new KeyboardProcessor(
				windowHandle, world.getPlayer());
		GravityEngine gravityEngine = new GravityEngine(world.getPlayer());
		
		while (glfwWindowShouldClose(windowHandle) == GL_FALSE) {

			glfwPollEvents();
			keyboardProcessor.process();
			gravityEngine.process();
			renderEngine.render();
		}

	}

}

class FPSCounter {
	double lastTime;
	int nbFrames;

	public FPSCounter() {
		lastTime = glfwGetTime();
		nbFrames = 0;
	}

	public void oneStep() {
		double currentTime = glfwGetTime();
		nbFrames++;
		if (currentTime - lastTime >= 1.0 ) { 
			System.out.println(1000.0 / (double) nbFrames + "ms/frame ("
					+ nbFrames + " FPS).");
			nbFrames = 0;
			lastTime += 1.0;
		}

	}
}