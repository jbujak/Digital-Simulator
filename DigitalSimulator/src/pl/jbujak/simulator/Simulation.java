package pl.jbujak.simulator;

import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.glfw.GLFW.*;

import java.awt.EventQueue;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GLContext;

import pl.jbujak.simulator.cmd.CommandLine;
import pl.jbujak.simulator.gui.Menu;
import pl.jbujak.simulator.gui.RenderEngine;
import pl.jbujak.simulator.gui.Window;
import pl.jbujak.simulator.input.CallbackProcessor;
import pl.jbujak.simulator.input.KeyboardProcessor;
import pl.jbujak.simulator.player.Inventory;
import pl.jbujak.simulator.utils.PowerableUtils;
import pl.jbujak.simulator.world.World;
import pl.jbujak.simulator.world.WorldGenerator;

public class Simulation {
	private static boolean isPaused = false;
	private static boolean terminated = false;

	private static Window mainWindow;
	private static RenderEngine renderEngine;
	private static World world;
	@SuppressWarnings("unused")
	// callbackProcessor created to avoid garbage collecting
	private static CallbackProcessor callbackProcessor;

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				mainWindow = new Window(700, 1400);
				world = World.create(128, 64, 128);
				WorldGenerator.generate(world);
				renderEngine = new RenderEngine(mainWindow.getWindowHandle(), world);
				callbackProcessor = new CallbackProcessor(renderEngine,
						mainWindow.getWindowHandle(), world.getPlayer());
				new CommandLine();
				mainLoop();
			}
		});
	}
	
	public static boolean isPaused() {
		return isPaused;
	}
	
	public static void pause() {
		isPaused = true;
	}
	
	public static void unpause() {
		isPaused = false;
	}
	
	public static void newWorld(int xSize, int ySize, int zSize) {
		world.changeSize(xSize, ySize, zSize);
		newWorld();
	}
	
	public static void newWorld() {
		WorldGenerator.generate(world);
		world.getPlayer().restart();
		Menu.close();
		Inventory.close();
	}
	
	public static void exit() {
		terminated = true;
		mainWindow.close();
	}
	
	public static boolean isTerminated() {
		return terminated;
	}

	public static void showCursor() {
		glfwSetInputMode(mainWindow.getWindowHandle(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);
	}
	
	public static void hideCursor() {
		glfwSetInputMode(mainWindow.getWindowHandle(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
	}

	public static void centerCursor() {
		IntBuffer widthBuffer = BufferUtils.createIntBuffer(4);
		IntBuffer heightBuffer = BufferUtils.createIntBuffer(4);
		glfwGetWindowSize(mainWindow.getWindowHandle(), widthBuffer, heightBuffer);

		int windowWidth= widthBuffer.get(0);
		int windowHeight= heightBuffer.get(0);

		glfwSetCursorPos(mainWindow.getWindowHandle(), windowWidth/2, windowHeight/2);

	}
	
	private static void mainLoop() {
		long windowHandle = mainWindow.getWindowHandle();
		GLContext.createFromCurrent();
		KeyboardProcessor keyboardProcessor = new KeyboardProcessor(
				windowHandle, world.getPlayer());
		
		while (glfwWindowShouldClose(windowHandle) == GL_FALSE) {

			glfwPollEvents();
			keyboardProcessor.process();
			renderEngine.render();
			world.getPlayer().processGravity();
			if(!isPaused) {
				PowerableUtils.updateState();
			}
		}
	}
}

class FPSCounter {
	private double lastTime;
	private int nbFrames;

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