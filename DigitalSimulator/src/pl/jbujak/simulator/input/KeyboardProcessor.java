package pl.jbujak.simulator.input;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWKeyCallback;

import pl.jbujak.simulator.Simulation;
import pl.jbujak.simulator.cmd.CommandLine;
import pl.jbujak.simulator.gui.Menu;
import pl.jbujak.simulator.player.IPlayer;
import pl.jbujak.simulator.player.Inventory;
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

	public void process() {

		if (Simulation.isPaused()) {
			return;
		}
		// Events below will not be processed if simulation is paused.

		if (glfwGetKey(windowHandle, GLFW_KEY_W) == 1) {
			controlledPlayer.move(Direction.FRONT);
		}
		if (glfwGetKey(windowHandle, GLFW_KEY_S) == 1) {
			controlledPlayer.move(Direction.BACK);
		}
		if (glfwGetKey(windowHandle, GLFW_KEY_A) == 1) {
			controlledPlayer.move(Direction.LEFT);
		}
		if (glfwGetKey(windowHandle, GLFW_KEY_D) == 1) {
			controlledPlayer.move(Direction.RIGHT);
		}
		if (glfwGetKey(windowHandle, GLFW_KEY_SPACE) == 1) {

			if (controlledPlayer.isFlying()) {
				controlledPlayer.move(Direction.UP);
			} else {
				controlledPlayer.jump();
			}
		}
		if (glfwGetKey(windowHandle, GLFW_KEY_LEFT_SHIFT) == 1) {
			if (controlledPlayer.isFlying()) {
				controlledPlayer.move(Direction.DOWN);
			}
		}
	}

	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		if (glfwGetKey(windowHandle, GLFW_KEY_ESCAPE) == GLFW_PRESS) {
			if (Inventory.isOpen())
				Inventory.close();
			else if (Menu.isOpen())
				Menu.close();
			else if (CommandLine.isOpen())
				CommandLine.close();
			else if (!Simulation.isPaused())
				Menu.open();

			return;
		}

		if (CommandLine.isOpen() && action == GLFW_PRESS) {
			char currentChar = calculateChar(key);
			if((int) currentChar != 0)
				CommandLine.sendChar(currentChar);
		}

		if (key == GLFW_KEY_E && action == GLFW_PRESS) {
			if (Inventory.isOpen())
				Inventory.close();
			else if (!Simulation.isPaused())
				Inventory.open();
		}

		if (key == GLFW_KEY_ENTER && action == GLFW_PRESS) {
			if (!CommandLine.isOpen())
				CommandLine.open();
		}

		if (key == GLFW_KEY_W && action == GLFW_RELEASE) {
			controlledPlayer.stopRunning();
		}

		if (Simulation.isPaused()) {
			return;
		}
		// Events below will not be processed if simulation is paused.

		if (key == GLFW_KEY_SPACE && action == GLFW_PRESS) {
			double currentTime = glfwGetTime();
			if (currentTime - lastTimeClickedSpace < doubleClickTimeInterval) {
				controlledPlayer.toggleIsFlying();
			}
			lastTimeClickedSpace = currentTime;
		}

		if (key == GLFW_KEY_W && action == GLFW_PRESS) {
			double currentTime = glfwGetTime();
			if (currentTime - lastTimeClickedW < doubleClickTimeInterval) {
				controlledPlayer.startRunning();
			}
			lastTimeClickedW = currentTime;
		}

	}

	private char calculateChar(int key) {
		if(key == GLFW_KEY_ENTER) return '\n';
		if(key == GLFW_KEY_SLASH) return '/';
		if(key == GLFW_KEY_SPACE) return ' ';
		if(key == GLFW_KEY_BACKSPACE) return '\b';
		if(key == GLFW_KEY_PERIOD) return '.';

		if((glfwGetKey(windowHandle, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS)
				|| glfwGetKey(windowHandle, GLFW_KEY_RIGHT_SHIFT) == GLFW_PRESS) {
			if(key >= GLFW_KEY_A && key <= GLFW_KEY_Z) return (char) key;
		}
		else {
			if(key >= GLFW_KEY_A && key <= GLFW_KEY_Z) return (char) (key + 32);
			if(key >= GLFW_KEY_0 && key <= GLFW_KEY_9) return (char) key;
		}
		return (char) 0;
	}
}