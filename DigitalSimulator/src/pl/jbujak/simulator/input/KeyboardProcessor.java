package pl.jbujak.simulator.input;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.glfw.GLFWKeyCallback;

import pl.jbujak.simulator.Simulation;
import pl.jbujak.simulator.player.IPlayer;
import pl.jbujak.simulator.world.Direction;

public class KeyboardProcessor extends GLFWKeyCallback {
	private final double doubleClickTimeInterval = 0.2;

	private long windowHandle;
	private double lastTimeClickedSpace;
	private double lastTimeClickedW;
	private IPlayer controlledPlayer;

	public KeyboardProcessor(long windowHandle, IPlayer controlledPlayer) {
		this.windowHandle = windowHandle;
		this.controlledPlayer = controlledPlayer;
		lastTimeClickedSpace = glfwGetTime();
	}
	
	public void process()
	{
		if(glfwGetKey(windowHandle, GLFW_KEY_ESCAPE) == 1) {
			glfwSetWindowShouldClose(windowHandle, GL_TRUE);
		}

		if(Simulation.isPaused()) {return;}
		//Events below will not be processed if simulation is paused.
		
		if(glfwGetKey(windowHandle, GLFW_KEY_W) == 1) {
			controlledPlayer.move(Direction.FRONT);
		}
		if(glfwGetKey(windowHandle, GLFW_KEY_S) == 1) {
			controlledPlayer.move(Direction.BACK);
		}
		if(glfwGetKey(windowHandle, GLFW_KEY_A) == 1) {
			controlledPlayer.move(Direction.LEFT);
		}
		if(glfwGetKey(windowHandle, GLFW_KEY_D) == 1) {
			controlledPlayer.move(Direction.RIGHT);
		}
		if(glfwGetKey(windowHandle, GLFW_KEY_SPACE) == 1) {
						
			if(controlledPlayer.isFlying()) {
				controlledPlayer.move(Direction.UP);
			}
			else {
				controlledPlayer.jump();
			}
		}
		if(glfwGetKey(windowHandle, GLFW_KEY_LEFT_SHIFT) == 1) {
			if(controlledPlayer.isFlying()) {
				controlledPlayer.move(Direction.DOWN);
			}
		}
	}	

	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		
		if(key == GLFW_KEY_E && action == GLFW_PRESS) {
			if(Simulation.isInventoryOpen()) {
				Simulation.closeInventory();
			}
			else {
				Simulation.openInventory();
			}
		}

		if(key == GLFW_KEY_W && action == GLFW_RELEASE) {
			controlledPlayer.stopRunning();
		}
		
		if(Simulation.isPaused()) {return;}
		//Events below will not be processed if simulation is paused.

		if(key == GLFW_KEY_SPACE && action == GLFW_PRESS) {
			double currentTime = glfwGetTime();
			if(currentTime - lastTimeClickedSpace < doubleClickTimeInterval) {
				controlledPlayer.toggleIsFlying();
			}
			lastTimeClickedSpace = currentTime;
		}
		
		if(key == GLFW_KEY_W && action == GLFW_PRESS) {
			double currentTime = glfwGetTime();
			if(currentTime - lastTimeClickedW < doubleClickTimeInterval) {
				controlledPlayer.startRunning();
			}
			lastTimeClickedW = currentTime;
		}


	}
}