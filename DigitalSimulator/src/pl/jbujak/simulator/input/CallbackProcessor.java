package pl.jbujak.simulator.input;

import pl.jbujak.simulator.environment.Player;
import pl.jbujak.simulator.gui.RenderEngine;
import static org.lwjgl.glfw.GLFW.*;

public class CallbackProcessor {
	
	private static CursorProcessor cursorProcessor;
	private static WindowSizeProcessor windowSizeProcessor;

	public CallbackProcessor(RenderEngine renderEngine, long windowHandle, Player controlledPlayer) {
		
		cursorProcessor = new CursorProcessor(windowHandle, controlledPlayer);
		glfwSetCursorPosCallback(windowHandle, cursorProcessor);

		windowSizeProcessor = new WindowSizeProcessor(renderEngine);
		glfwSetWindowSizeCallback(windowHandle, windowSizeProcessor);

	}
}
