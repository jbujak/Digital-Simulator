package pl.jbujak.simulator.gui;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {

	private int initialHeight;
	private int initialWidth;

	public Window(int initialHeight, int initialwidth) {
		this.initialHeight = initialHeight;
		this.initialWidth = initialwidth;
		init();
	}

	// The window handle
	private static long windowHandle;

	public void init() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		glfwSetErrorCallback(errorCallbackPrint(System.err));

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if (glfwInit() != GL11.GL_TRUE)
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure our window
		glfwDefaultWindowHints(); // optional, the current window hints are
									// already the default
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden
												// after creation
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // the window will be resizable

		// Create the window
		windowHandle = glfwCreateWindow(initialWidth, initialHeight, "Hello World!", NULL,
				NULL);
		if (windowHandle == NULL)
			throw new RuntimeException("Failed to create the GLFW window");

		// Get the resolution of the primary monitor
		ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

		// Center our window
		glfwSetWindowPos(windowHandle,
				(GLFWvidmode.width(vidmode) - initialWidth) / 2,
				(GLFWvidmode.height(vidmode) - initialHeight) / 2);
		
		//Instead of centering, move to left up corner
		glfwSetWindowPos(windowHandle,
				0, 0);
				

		// Make the OpenGL context current
		glfwMakeContextCurrent(windowHandle);
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(windowHandle);

		glfwSetInputMode(windowHandle, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
		
		GLContext.createFromCurrent();		
		
		glEnable(GL_TEXTURE_2D);

		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
	}

	public long getWindowHandle() {
		return windowHandle;
	}
	
	public void close() {
		glfwSetWindowShouldClose(windowHandle, GL_TRUE);
	}
	
	public static int getHeight() {
		IntBuffer widthBuffer = BufferUtils.createIntBuffer(4);
		IntBuffer heightBuffer = BufferUtils.createIntBuffer(4);
		glfwGetWindowSize(windowHandle, widthBuffer, heightBuffer);

		int windowHeight= heightBuffer.get(0);
		return windowHeight;
	}

	public static int getWidth() {
		IntBuffer widthBuffer = BufferUtils.createIntBuffer(4);
		IntBuffer heightBuffer = BufferUtils.createIntBuffer(4);
		glfwGetWindowSize(windowHandle, widthBuffer, heightBuffer);

		int windowWidth= widthBuffer.get(0);
		return windowWidth;
	}
}
