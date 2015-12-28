package pl.jbujak.simulator.gui;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import pl.jbujak.simulator.blocks.Block;
import pl.jbujak.simulator.blocks.BlockType;
import pl.jbujak.simulator.utils.Position;

public class BlocksToRenderManager {

	private Map<Position, Map<BlockType, HashSet<Position>>> blocksToRender;
	private Block[][][] blocks;
	
	public BlocksToRenderManager(Block[][][] blocks) {
		this.blocks = blocks;

		blocksToRender = new HashMap<>();
	}

	public void changeBlock(Position position, Block newBlock) {
		if(position == null) {return;}
		int x = (int)position.x;
		int y = (int)position.y;
		int z = (int)position.z;
		
		Position chunk = getChunk(position);
		
		if(!blocksToRender.containsKey(chunk)) {
			blocksToRender.put(chunk, new HashMap<>());
			for(BlockType blockType: BlockType.values()) {
				blocksToRender.get(chunk).put(blockType, new HashSet<Position>());
			}
		}
		
		if(blocks[x][y][z] != null) {
			BlockType previousBlockType = blocks[x][y][z].getBlockType();
			Position previousPosition = new Position(x, y, z);
			blocksToRender.get(chunk).get(previousBlockType).remove(previousPosition);
		}
		if(newBlock != null) {
			BlockType newBlockType = newBlock.getBlockType();
			Position newPosition = new Position(x, y, z);
			blocksToRender.get(chunk).get(newBlockType).add(newPosition);
		}
	}
	
	public Map<BlockType, HashSet<Position>> getBlocksToRender(Position chunk) {
		return blocksToRender.get(chunk);
	}
	
	private Position getChunk(Position position) {
		return new Position(Math.floor(position.x/16), 0, Math.floor(position.z/16));
	}
}
