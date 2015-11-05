package pl.jbujak.simulator.gui;

import java.util.HashSet;


public class DrawEngine {
	private static HashSet<IDrawable> shapesToDraw3D = new HashSet<IDrawable>();
	private static HashSet<IDrawable> shapesToDraw2D = new HashSet<IDrawable>();
	
	public static void addShape3D(IDrawable shape) {
		shapesToDraw3D.add(shape);
	}

	public static void addShape2D(IDrawable shape) {
		shapesToDraw2D.add(shape);
	}

	public static void draw3D(int windowWidth, int windowHeight) {
		for(IDrawable shape: shapesToDraw3D) {
			shape.draw(windowWidth, windowHeight);
		}
	}
	
	public static void draw2D(int windowWidth, int windowHeight) {
		for(IDrawable shape: shapesToDraw2D) {
			shape.draw(windowWidth, windowHeight);
		}
	}
}
