package pl.jbujak.simulator.cmd;

import java.util.Arrays;

import pl.jbujak.simulator.Simulation;
import pl.jbujak.simulator.gui.CommandLineDrawer;
import pl.jbujak.simulator.gui.DrawEngine;

public class CommandLine {
	
	private static boolean isOpen;
	private static String command;
	private static String history;
	private static CommandLineDrawer drawer;
	
	public CommandLine() {
		drawer = new CommandLineDrawer();
		DrawEngine.addShape2D(drawer);
		command = "";
		history = "";
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
		String result = "";
		history += ">" + command + "\n";
		
		String[] commandWords = command.split(" ");
		if(commandWords.length != 0) {
			String cmd = commandWords[0];
			String[] parameters = Arrays.copyOfRange(commandWords, 1, commandWords.length);
			
			if(cmd.isEmpty()) {
				result = "";
			}
			else if(cmd.equals("clear")) {
				history = "";
			}
			else if(cmd.equals("save")) {
				result = Commands.save(parameters);
			}
			else if(cmd.equals("load")) {
				result = Commands.load(parameters);
			}
			else if(cmd.equals("exit")) {
				result = Commands.exit(parameters);
			}
			else if(cmd.equals("new")) {
				result = Commands.newWorld(parameters);
			}
			else if(cmd.equals("help")) {
				result = Commands.help(parameters);
			}
			else {
				result = "Unknown command";
			}
		}
		
		history += result + "\n";
		command = "";
	}
	
	public static String getCommand() {
		return command;
	}
	
	public static String getHistory() {
		return history;
	}
	
		
}
