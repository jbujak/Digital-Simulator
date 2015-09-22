package pl.jbujak.simulator.input;

import org.lwjgl.glfw.GLFWWindowSizeCallback;
import pl.jbujak.simulator.gui.RenderEngine;

public class WindowSizeProcessor extends GLFWWindowSizeCallback {
	
	private RenderEngine renderEngine;
	
	public WindowSizeProcessor(RenderEngine renderEngine) {
		this.renderEngine = renderEngine;
	}
	
	@Override
	public void invoke(long windowHandle, int width, int height) {
		renderEngine.updateScene();
		renderEngine.render();
	}

}
