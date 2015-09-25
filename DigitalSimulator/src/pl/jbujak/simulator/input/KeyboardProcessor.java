package pl.jbujak.simulator.input;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.glfw.GLFWKeyCallback;

import pl.jbujak.simulator.environment.Direction;
import pl.jbujak.simulator.environment.IPlayer;

public class KeyboardProcessor extends GLFWKeyCallback {

	long windowHandle;
	private IPlayer controlledPlayer;

	public KeyboardProcessor(long windowHandle, IPlayer controlledPlayer) {
		this.windowHandle = windowHandle;
		this.controlledPlayer = controlledPlayer;
	}
	
	public void process()
	{
		if(glfwGetKey(windowHandle, GLFW_KEY_ESCAPE) == 1) {
			glfwSetWindowShouldClose(windowHandle, GL_TRUE);
		}
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


	}
}