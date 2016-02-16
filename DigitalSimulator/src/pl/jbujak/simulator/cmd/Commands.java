package pl.jbujak.simulator.cmd;

import java.io.FileNotFoundException;
import java.io.IOException;

import pl.jbujak.simulator.Simulation;
import pl.jbujak.simulator.world.LoadManager;
import pl.jbujak.simulator.world.SaveManager;
import pl.jbujak.simulator.world.World;

public class Commands {
	public static String save(String[] parameters) {
		if(parameters.length != 1) {
			return "Wrong number of parameters\nUsage:save filename\n";
		}
		
		try {
			SaveManager.save(World.instance, parameters[0]);
		} catch (IOException e) {
			return "Save error\n";
		}

		return "Save succesful\n";
	}
	
	public static String load(String[] parameters) {
		if(parameters.length != 1) {
			return "Wrong number of parameters\nUsage: load filename\n";
		}
		
		try {
			LoadManager.load(World.instance, parameters[0]);
		} catch (FileNotFoundException e) {
			return "File not found\n";
		} catch (ClassNotFoundException e) {
			return "File is not a valid save\n";
		} catch (IOException e) {
			return "Load error\n";
		}
		
		CommandLine.close();
		return "Load succesful\n";
	}
	
	public static String exit(String[] parameters) {
		Simulation.exit();
		return "";
	}
}
