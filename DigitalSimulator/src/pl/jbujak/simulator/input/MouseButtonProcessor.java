package pl.jbujak.simulator.input;

import org.lwjgl.glfw.GLFWMouseButtonCallback;

import pl.jbujak.simulator.environment.IPlayer;
import static org.lwjgl.glfw.GLFW.*;

public class MouseButtonProcessor extends GLFWMouseButtonCallback{
	private IPlayer controlledPlayer;
	
	public MouseButtonProcessor(IPlayer controlledPlayer) {
		this.controlledPlayer = controlledPlayer;
	}

	@Override
	public void invoke(long windowHandle, int button, int action, int mods) {
		
		if(action == GLFW_PRESS) {
			if(button == GLFW_MOUSE_BUTTON_1) {
				controlledPlayer.destroyBlock();
			}
			else if(button == GLFW_MOUSE_BUTTON_2) {
				controlledPlayer.putBlock();
			}
		}
	}
}
