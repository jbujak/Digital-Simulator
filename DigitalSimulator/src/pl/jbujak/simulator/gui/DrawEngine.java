package pl.jbujak.simulator.gui;

import java.util.HashSet;


public class DrawEngine {
	private static HashSet<IDrawable> shapesToDraw = new HashSet<IDrawable>();
	
	public static void addShape(IDrawable shape) {
		shapesToDraw.add(shape);
	}
	
	public static void drawAll() {
		for(IDrawable shape: shapesToDraw) {
			shape.draw();
		}
	}
}
