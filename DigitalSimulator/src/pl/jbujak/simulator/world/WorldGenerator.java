package pl.jbujak.simulator.world;

import pl.jbujak.simulator.blocks.GrassBlock;
import pl.jbujak.simulator.utils.Position;

public class WorldGenerator {
	public static void generate(World world) {
		clear(world);
		for(int x = 0; x < world.xSize; x++)
			for(int y = 0; y < 5; y++)
				for(int z = 0; z < world.zSize; z++) {
					world.changeBlock(new Position(x, y, z), new GrassBlock(new Position()));
				}
		}
	
	private static void clear(World world) {
		for(int x = 0; x < world.xSize; x++) {
			for(int y = 0; y < world.ySize; y++) {
				for(int z = 0; z < world.zSize; z++) {
					world.changeBlock(new Position(x, y, z), null);
				}
			}
		}
	}
}
