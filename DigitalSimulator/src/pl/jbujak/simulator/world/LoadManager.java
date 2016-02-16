package pl.jbujak.simulator.world;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import pl.jbujak.simulator.Simulation;
import pl.jbujak.simulator.blocks.Block;
import pl.jbujak.simulator.utils.Position;

public class LoadManager {

	public static void load(World world, String filename)
			throws FileNotFoundException, IOException, ClassNotFoundException {

		ObjectInputStream is = new ObjectInputStream(new FileInputStream(filename));
		WorldRecord worldRecord = (WorldRecord) is.readObject();
		is.close();

		world.changeSize(worldRecord.xSize, worldRecord.ySize, worldRecord.zSize);

		WorldGenerator.clear(world);
		Simulation.unpause();

		for (BlockRecord blockRecord : worldRecord.savedBlocks) {
			Position position = blockRecord.position;
			Block newBlock = blockRecord.blockType.getNewBlock(position);
			newBlock.setOrientation(blockRecord.orientation);
			world.changeBlock(position, newBlock);
		}
		world.getPlayer().setPosition(worldRecord.playerPosition);

	}
}
