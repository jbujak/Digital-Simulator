package pl.jbujak.simulator.world;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import pl.jbujak.simulator.blocks.Block;

public class SaveManager {
	public static void save(World world, String filename) throws FileNotFoundException, IOException {
		ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(filename));
		Block[][][] blocks = world.getBlocks();

		WorldRecord worldRecord = new WorldRecord(world.getXSize(), world.getYSize(), world.getZSize());
		worldRecord.playerPosition = world.getPlayer().getPosition();

		for (int x = 0; x < world.getXSize(); x++) {
			for (int y = 0; y < world.getYSize(); y++) {
				for (int z = 0; z < world.getZSize(); z++) {
					Block block = blocks[x][y][z];
					if (block == null)
						continue;

					BlockRecord blockRecord = new BlockRecord(block.getPosition(), block.getBlockType(),
							block.getOrientation());
					worldRecord.savedBlocks.add(blockRecord);
				}
			}
		}

		os.writeObject(worldRecord);

		os.close();
	}
}
