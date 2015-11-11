package pl.jbujak.simulator.blocks;

import java.util.HashSet;
import java.util.Set;

import pl.jbujak.simulator.utils.BlockTypeFaceValue;
import pl.jbujak.simulator.world.Direction;

public class BlockTextureManager {
	
	private static BlockTypeFaceValue textureId = new BlockTypeFaceValue();
	private static Set<BlockType> registeredBlocks = new HashSet<BlockType>();
	

	public static void registerBlock(BlockType blockType, Direction face, int textureId) {
		BlockTextureManager.textureId.setValue(blockType, face, textureId);
		registeredBlocks.add(blockType);
	}
	
	public static boolean isRegistered(BlockType blockType) {
		if(registeredBlocks == null) {return false;}
		return registeredBlocks.contains(blockType);
	}
	
	public static int getTextureId(BlockType blockType, Direction face) {
		return textureId.getValue(blockType, face);
	}

}
