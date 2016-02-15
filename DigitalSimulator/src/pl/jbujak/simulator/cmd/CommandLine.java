package pl.jbujak.simulator.cmd;

import pl.jbujak.simulator.Simulation;
import pl.jbujak.simulator.gui.CommandLineDrawer;
import pl.jbujak.simulator.gui.DrawEngine;

public class CommandLine {
	
	private static boolean isOpen;
	private static String command;
	private static CommandLineDrawer drawer;
	
	public CommandLine() {
		drawer = new CommandLineDrawer();
		DrawEngine.addShape2D(drawer);
		command = "";
	}

	public static void open() {
		isOpen = true;
		Simulation.pause();
	}
	
	public static void close() {
		isOpen = false;
		Simulation.unpause();
		command = "";
	}
	
	public static boolean isOpen() {
		return isOpen;
	}
	
	public static void sendChar(char c) {
		if(c == '\n') {
			enter();
		}
		else if(c == '\b') {
			if(!command.isEmpty()) {
				command = command.substring(0, command.length()-1);
			}
		}
		else {
			command += c;
		}
	}
	
	private static void enter() {
		command = "";
	}
	
	public static String getCommand() {
		return command;
	}
		
}
