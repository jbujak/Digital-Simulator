package pl.jbujak.simulator.world;

import pl.jbujak.simulator.blocks.GrassBlock;
import pl.jbujak.simulator.blocks.RedstoneLine;
import pl.jbujak.simulator.utils.Position;

public class WorldGenerator {
	public void generate(World world) {

		for(int x = 0; x < world.xSize; x++)
			for(int y = 0; y < 1; y++)
				for(int z = 0; z < world.zSize; z++) {
					world.changeBlock(new Position(x, y, z), new GrassBlock(new Position()));
				}
		
		for(int x = 0; x < world.xSize-1; x += 2) {
			for(int z = 0; z < world.zSize; z++) 
				world.changeBlock(new Position(x, 1, z), new RedstoneLine(new Position(x, 1, z)));
			
			if(x%4 == 0 )
				world.changeBlock(new Position(x+1, 1, world.zSize-1), new RedstoneLine(new Position(x+1, 1, world.zSize-1)));
			else
				world.changeBlock(new Position(x+1, 1, 0), new RedstoneLine(new Position(x+1, 1, 0)));
		}
	}
}
