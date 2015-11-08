package pl.jbujak.simulator.input;

import org.lwjgl.glfw.GLFWMouseButtonCallback;

import pl.jbujak.simulator.Simulation;
import pl.jbujak.simulator.player.IPlayer;
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
				if(!Simulation.isPaused()) {
					controlledPlayer.destroyBlock();
				}
				else if(Simulation.isInventoryOpen()) {
					controlledPlayer.getInventory().grabCell();
				}
			}
			else if(button == GLFW_MOUSE_BUTTON_2) {
				if(!Simulation.isPaused()) {
					controlledPlayer.putBlock();
				}
			}
		}
		else if(action == GLFW_RELEASE) {
			if(button == GLFW_MOUSE_BUTTON_1) {
				if(Simulation.isInventoryOpen()) {
					controlledPlayer.getInventory().dropCell();
				}
			}
		}
	}
}
