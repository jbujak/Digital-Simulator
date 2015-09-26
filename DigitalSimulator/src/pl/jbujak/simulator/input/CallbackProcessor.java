package pl.jbujak.simulator.input;

import pl.jbujak.simulator.environment.IPlayer;
import pl.jbujak.simulator.gui.RenderEngine;
import static org.lwjgl.glfw.GLFW.*;

public class CallbackProcessor {
	
	private static CursorProcessor cursorProcessor;
	private static WindowSizeProcessor windowSizeProcessor;
	private static MouseButtonProcessor mouseButtonProcessor;

	public CallbackProcessor(RenderEngine renderEngine, long windowHandle, IPlayer controlledPlayer) {
		
		cursorProcessor = new CursorProcessor(windowHandle, controlledPlayer);
		glfwSetCursorPosCallback(windowHandle, cursorProcessor);

		windowSizeProcessor = new WindowSizeProcessor(renderEngine);
		glfwSetWindowSizeCallback(windowHandle, windowSizeProcessor);
		
		mouseButtonProcessor = new MouseButtonProcessor(controlledPlayer);
		glfwSetMouseButtonCallback(windowHandle, mouseButtonProcessor);
	}
}
