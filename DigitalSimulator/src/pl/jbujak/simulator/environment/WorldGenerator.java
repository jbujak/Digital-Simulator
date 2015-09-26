package pl.jbujak.simulator.environment;

import pl.jbujak.simulator.blocks.BedrockBlock;
import pl.jbujak.simulator.blocks.GrassBlock;
import pl.jbujak.simulator.utils.Position;

public class WorldGenerator implements IWorldGenerator {
	public void generate(World world) {

		for(int x = 0; x < world.xSize; x++)
			for(int y = 0; y < 1; y++)
				for(int z = 0; z < world.zSize; z++) {
					world.changeBlock(new Position(x, y, z), new BedrockBlock());
				}
		
		for(int z = 0; z < world.zSize; z++) {
			if(z%2 == 0) {
				world.changeBlock(new Position(10, 1, z), new GrassBlock());
				world.changeBlock(new Position(10, 2, z), new GrassBlock());
				world.changeBlock(new Position(10, 3, z), new GrassBlock());
			}
			else {
				world.changeBlock(new Position(10, 1, z), new BedrockBlock());
				world.changeBlock(new Position(10, 2, z), new BedrockBlock());
				world.changeBlock(new Position(10, 3, z), new BedrockBlock());
			}
			world.changeBlock(new Position(z, 1, 5), new GrassBlock());
			world.changeBlock(new Position(z, 2, 5), new GrassBlock());
		}
	}
}
