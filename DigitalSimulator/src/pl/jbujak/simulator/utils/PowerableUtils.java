package pl.jbujak.simulator.utils;

import pl.jbujak.simulator.blocks.Block;
import pl.jbujak.simulator.blocks.IPowerable;
import pl.jbujak.simulator.blocks.RedstoneCross;
import pl.jbujak.simulator.blocks.RedstoneDust;
import pl.jbujak.simulator.world.Direction;
import pl.jbujak.simulator.world.World;

public class PowerableUtils {
	public static boolean isPowerable(Position position) {
		World world = World.instance;
		if(world == null) {return false;}
		
		int x = (int)position.x;
		int y = (int)position.y;
		int z = (int)position.z;
		
		if(x < 0 || y < 0 || z < 0) {return false;}
		if(x > world.xSize || y > world.ySize || z > world.zSize) {return false;}
		if(world.getBlocks()[x][y][z] == null) {return false;}
		
		return world.getBlocks()[x][y][z] instanceof IPowerable;
	}
	
	public static void updateNearBlocks(Position position) {
		World world = World.instance;

		if(isPowerable(position.next(Direction.RIGHT))) {
			IPowerable rightBlock = (IPowerable)world.getBlock(position.next(Direction.RIGHT));
			rightBlock.update();
		}

		if(isPowerable(position.next(Direction.LEFT))) {
			IPowerable leftBlock = (IPowerable)world.getBlock(position.next(Direction.LEFT));
			leftBlock.update();
		}
		
		if(isPowerable(position.next(Direction.FRONT))) {
			IPowerable frontBlock = (IPowerable)world.getBlock(position.next(Direction.FRONT));
			frontBlock.update();
		}
		
		if(isPowerable(position.next(Direction.BACK))) {
			IPowerable backBlock = (IPowerable)world.getBlock(position.next(Direction.BACK));
			backBlock.update();
		}
	}	
	
	public static void updateRedstoneDirection(Block block, Position position) {
		World world = World.instance;
		
		if(PowerableUtils.isPowerable(position.next(Direction.RIGHT)) || 
		   PowerableUtils.isPowerable(position.next(Direction.LEFT))) {
			
			if(PowerableUtils.isPowerable(position.next(Direction.FRONT)) ||
			   PowerableUtils.isPowerable(position.next(Direction.BACK))) {
				if(!(block instanceof RedstoneCross)) {
					world.changeBlock(position, new RedstoneCross(position));
				}
			}
			else {
				if(!(block instanceof RedstoneDust)) {
					world.changeBlock(position, new RedstoneDust(position));
				}
				if(block.getOrientation() != Direction.RIGHT) {
					block.setOrientation(Direction.RIGHT);
					PowerableUtils.updateNearBlocks(position);
				}
			}
		}
		else {
			if(!(block instanceof RedstoneDust)) {
				world.changeBlock(position, new RedstoneDust(position));
			}
			if(PowerableUtils.isPowerable(position.next(Direction.FRONT)) ||
			   PowerableUtils.isPowerable(position.next(Direction.BACK))) {
				if(block.getOrientation() != Direction.FRONT) {
					block.setOrientation(Direction.FRONT);
					PowerableUtils.updateNearBlocks(position);
				}
			}
		}
	}
	
}
