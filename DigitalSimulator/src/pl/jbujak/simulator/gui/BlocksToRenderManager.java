package pl.jbujak.simulator.gui;

import java.util.ArrayList;
import java.util.HashSet;

import pl.jbujak.simulator.blocks.Block;
import pl.jbujak.simulator.blocks.BlockType;
import pl.jbujak.simulator.utils.Position;

public class BlocksToRenderManager {
	private final int numberOfBlockTypes;

	private ArrayList<HashSet<Position>> blocksToRender;
	private Block[][][] blocks;
	
	public BlocksToRenderManager(Block[][][] blocks) {
		numberOfBlockTypes = BlockType.values().length;
		this.blocks = blocks;

		blocksToRender = new ArrayList<HashSet<Position>>();
		for(int i=0; i<numberOfBlockTypes; i++) {
			blocksToRender.add(new HashSet<Position>());
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
			blocksToRender.get(previousBlockType.value).remove(previousPosition);
		}
		if(newBlock != null) {
			BlockType newBlockType = newBlock.getBlockType();
			Position newPosition = new Position(x, y, z);
			blocksToRender.get(newBlockType.value).add(newPosition);
		}
	}
	
	public ArrayList<HashSet<Position>> getBlocksToRender() {
		return blocksToRender;
	}
}
