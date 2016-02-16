package pl.jbujak.simulator.input;

import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWCursorPosCallback;

import static org.lwjgl.glfw.GLFW.*;
import pl.jbujak.simulator.Simulation;
import pl.jbujak.simulator.player.Player;
import pl.jbujak.simulator.utils.Position;

public class CursorProcessor extends GLFWCursorPosCallback {
	private static double previousXPos;
	private static double previousYPos;
	private double mouseSensitivity = 0.15;
	private Player controlledPlayer;

	public CursorProcessor(long windowHandle, Player controlledPlayer) {

		this.controlledPlayer = controlledPlayer;
		
		DoubleBuffer xBuffer = BufferUtils.createDoubleBuffer(8);
		DoubleBuffer yBuffer = BufferUtils.createDoubleBuffer(8);
		glfwGetCursorPos(windowHandle, xBuffer, yBuffer);
		
		previousXPos = xBuffer.get(0);
		previousYPos = yBuffer.get(0);
	}

	@Override
	public void invoke(long window, double xPos, double yPos) {

		double xOffset = xPos - previousXPos;
		double yOffset = yPos - previousYPos;

		previousXPos = xPos;
		previousYPos = yPos;

		if(Simulation.isPaused()) {return;}
		//Events below will not be processed if simulation is paused.
		
		double phi = xOffset * mouseSensitivity;
		double theta = yOffset * mouseSensitivity;
		
		controlledPlayer.rotateBy(phi, theta);

	}
	
	public static Position getCursorPosition() {
		return new Position(previousXPos, previousYPos, 0);
	}

}
