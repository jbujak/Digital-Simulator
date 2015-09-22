package pl.jbujak.simulator.input;

import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWCursorPosCallback;

import static org.lwjgl.glfw.GLFW.*;
import pl.jbujak.simulator.environment.Player;

public class CursorProcessor extends GLFWCursorPosCallback {

	private double previousXPos;
	private double previousYPos;
	private double mouseSensitivity = 0.2;
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
		
		controlledPlayer.rotateBy(xOffset*mouseSensitivity, yOffset*mouseSensitivity);

	}

}
