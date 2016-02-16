package pl.jbujak.simulator.cmd;

import java.io.FileNotFoundException;
import java.io.IOException;

import pl.jbujak.simulator.Simulation;
import pl.jbujak.simulator.world.LoadManager;
import pl.jbujak.simulator.world.SaveManager;
import pl.jbujak.simulator.world.World;

public class Commands {
	private static String[] commands = {
		"save", "load", "help", "new", "clear", "exit"
	};

	public static String save(String[] parameters) {
		if (parameters.length != 1) {
			return "Wrong number of parameters\nUsage: save filename";
		}

		try {
			SaveManager.save(World.instance, parameters[0]);
		} catch (IOException e) {
			return "Save error";
		}

		return "Save succesful";
	}

	public static String load(String[] parameters) {
		if (parameters.length != 1) {
			return "Wrong number of parameters\nUsage: load filename";
		}

		try {
			LoadManager.load(World.instance, parameters[0]);
		} catch (FileNotFoundException e) {
			return "File not found";
		} catch (ClassNotFoundException e) {
			return "File is not a valid save";
		} catch (IOException e) {
			return "Load error";
		}

		CommandLine.close();
		return "Load succesful";
	}

	public static String exit(String[] parameters) {
		Simulation.exit();
		return "";
	}

	public static String newWorld(String[] parameters) {
		if (parameters.length == 0) {
			Simulation.newWorld();
			CommandLine.close();
			return "";
		} else if (parameters.length == 3) {
			World world = World.instance;
			try {
				int xSize = Integer.parseInt(parameters[0]);
				int ySize = Integer.parseInt(parameters[1]);
				int zSize = Integer.parseInt(parameters[2]);
				if (xSize > world.maxXSize || ySize > world.maxYSize || zSize > world.maxZSize) {
					return "World size to big. Maximum size is " + world.maxXSize + " " + world.maxYSize + " "
							+ world.maxZSize;
				}
				Simulation.newWorld(xSize, ySize, zSize);
			} catch (NumberFormatException e) {
				return "World size must be a number";
			}
			return "";
		} else {
			return "Wrong number of parameters\nUsage: new [width height length]";
		}
	}
	
	public static String help(String[] parameters) {
		String result = "";
		if(parameters.length == 0) {
			result = "help [command] to show help for specific commmand\n";
			result += "Commands:\n";
			for(String command: commands) {
				result += command + ", ";
			}
		}
		else if(parameters.length == 1) {
			if(parameters[0].equals("save")) {
				result += "<save filename> saves current world to a given file";
			}
			else if(parameters[0].equals("load")) {
				result += "<load filename> loads world from a given file";
			}
			else if(parameters[0].equals("help")) {
				result += "<help> shows all possible commands";
			}
			else if(parameters[0].equals("new")) {
				result += "<new> creates new world with the same dimensions as the old one\n";
				result += "<new width height length> creates new world with given dimensions";
			}
			else if(parameters[0].equals("clear")) {
				result += "<clear> clears command line output";
			}
			else if(parameters[0].equals("exit")) {
				result += "<exit> closes application";
			}
		}
		return result;
	}
}
