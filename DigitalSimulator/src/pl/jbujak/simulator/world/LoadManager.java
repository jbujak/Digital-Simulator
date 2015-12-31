package pl.jbujak.simulator.world;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import pl.jbujak.simulator.Simulation;
import pl.jbujak.simulator.blocks.Block;
import pl.jbujak.simulator.utils.Position;

public class LoadManager {

	public static void load(World world) {
		
		try {
			ObjectInputStream is = new ObjectInputStream(new FileInputStream("world.sav"));
			WorldRecord worldRecord = (WorldRecord) is.readObject();
			is.close();

			world.changeSize(worldRecord.xSize, worldRecord.ySize, worldRecord.zSize);
			
			WorldGenerator.clear(world);
			Simulation.unpause();
			
			for(BlockRecord blockRecord: worldRecord.savedBlocks) {
				Position position = blockRecord.position;
				Block newBlock = blockRecord.blockType.getNewBlock(position);
				world.changeBlock(position, newBlock);
			}
			world.getPlayer().setPosition(worldRecord.playerPosition);

		} catch(IOException e) {
			e.printStackTrace();
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
