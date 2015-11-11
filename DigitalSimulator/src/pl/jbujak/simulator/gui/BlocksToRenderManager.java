package pl.jbujak.simulator.gui;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import pl.jbujak.simulator.blocks.Block;
import pl.jbujak.simulator.blocks.BlockType;
import pl.jbujak.simulator.utils.Position;

public class BlocksToRenderManager {

	private Map<BlockType, HashSet<Position>> blocksToRender;
	private Block[][][] blocks;
	
	public BlocksToRenderManager(Block[][][] blocks) {
		this.blocks = blocks;

		blocksToRender = new HashMap<BlockType, HashSet<Position>>();
		for(BlockType blockType: BlockType.values()) {
			blocksToRender.put(blockType, new HashSet<Position>());
		}
	}

	public void changeBlock(Position position, Block newBlock) {
		if(position == null) {return;}
		int x = (int)position.x;
		int y = (int)position.y;
		int z = (int)position.z;
		
		if(blocks[x][y][z] != null) {
			BlockType previousBlockType = blocks[x][y][z].getBlockType();
			Position previousPosition = new Position(x, y, z);
			blocksToRender.get(previousBlockType).remove(previousPosition);
		}
		if(newBlock != null) {
			BlockType newBlockType = newBlock.getBlockType();
			Position newPosition = new Position(x, y, z);
			blocksToRender.get(newBlockType).add(newPosition);
		}
	}
	
	public Map<BlockType, HashSet<Position>> getBlocksToRender() {
		return blocksToRender;
	}
}
