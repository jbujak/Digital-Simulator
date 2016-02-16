package pl.jbujak.simulator.input;

import org.lwjgl.glfw.GLFWScrollCallback;

import pl.jbujak.simulator.player.Player;

public class ScrollWheelProcessor extends GLFWScrollCallback {
	private Player player;
	
	public ScrollWheelProcessor(Player player) {
		this.player = player;
	}

	@Override
	public void invoke(long windowHandle, double xOffset, double yOffset) {
		if(yOffset == -1) {
			player.getInventory().nextItem();
		}
		else if(yOffset == 1) {
			player.getInventory().prevItem();
		}

	}

}
