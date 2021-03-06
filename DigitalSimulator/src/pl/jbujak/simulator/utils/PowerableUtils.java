package pl.jbujak.simulator.utils;

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import pl.jbujak.simulator.blocks.Powerable;
import pl.jbujak.simulator.world.Direction;
import pl.jbujak.simulator.world.World;

public class PowerableUtils {
	private final static int updatesPerFrame = 1000;
	private static Queue<Powerable> blocksToUpdate;
	
	public static boolean isPowerable(Position position) {
		World world = World.instance;
		if(world == null) {return false;}
		
		int x = (int)position.x;
		int y = (int)position.y;
		int z = (int)position.z;
		
		if(x < 0 || y < 0 || z < 0) {return false;}
		if(x >= world.getXSize() || y >= world.getYSize() || z >= world.getZSize()) {return false;}
		if(world.getBlocks()[x][y][z] == null) {return false;}
		
		return world.getBlocks()[x][y][z] instanceof Powerable;
	}
	
	public static void updateNearBlocks(Position position) {
		World world = World.instance;
		
		for(Direction height: new Direction[] {
				null, Direction.UP, Direction.DOWN
		}) {
			for(Direction direction: new Direction[] {
					null, Direction.RIGHT, Direction.LEFT, Direction.FRONT, Direction.BACK
					}) {

				if(isPowerable(position.next(direction).next(height))) {
					Powerable neighbour = (Powerable)world.getBlock(position.next(direction).next(height));
					addToUpdate(neighbour);
				}
			}
		}
	}
	
	public static void addToUpdate(Powerable block) {
		if(blocksToUpdate == null)
			blocksToUpdate = new LinkedBlockingQueue<Powerable>();

		blocksToUpdate.add(block);
	}
		
	public static void updateState() {
		World world = World.instance;

		Set<Position> updatedChunks = new HashSet<>();

		if(blocksToUpdate == null)
			blocksToUpdate = new LinkedBlockingQueue<Powerable>();

		int updatedBlocks = 0;
		
		while(!blocksToUpdate.isEmpty() && updatedBlocks < updatesPerFrame) {
			Powerable current = blocksToUpdate.remove();
			current.update();
			updatedChunks.add(world.getChunk(current.getPosition()));

			updatedBlocks++;
		}
		
		for(Position chunk: updatedChunks) {
			World.instance.addChangedChunk(chunk);
		}
	}	
	
	public static boolean suppliesPower(Position position, Direction direction) {
		if(!isPowerable(position))
			return false;
		
		World world = World.instance;
		Powerable powerable = (Powerable)world.getBlock(position);
		
		if(powerable.carriesEnergy()) 
			return true;
		if(!powerable.getConnectedSourcesAskedFrom(direction).isEmpty())
			return true;
		
		return false;
	}
	
	
}
