package pl.jbujak.simulator.world;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import pl.jbujak.simulator.blocks.Block;
import pl.jbujak.simulator.utils.Position;

public class LoadManager {

	public static void load(World world) {
		
		try {
			ObjectInputStream is = new ObjectInputStream(new FileInputStream("/home/jbujak/file.sav"));
			
			WorldRecord worldRecord = (WorldRecord) is.readObject();
			
			WorldGenerator.clear(world);
			
			for(BlockRecord blockRecord: worldRecord.savedBlocks) {
				Position position = blockRecord.position;
				Block newBlock = blockRecord.blockType.getNewBlock(position);
				world.changeBlock(position, newBlock);
			}
			is.close();

		} catch(IOException e) {
			e.printStackTrace();
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
