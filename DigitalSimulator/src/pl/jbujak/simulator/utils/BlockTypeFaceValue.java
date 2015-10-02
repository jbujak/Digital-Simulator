package pl.jbujak.simulator.utils;

import pl.jbujak.simulator.blocks.BlockType;
import pl.jbujak.simulator.world.Direction;

public class BlockTypeFaceValue {

	private final int numberOfFaces = 6;

	private int value[][];
	
	public BlockTypeFaceValue(int numberOfBlockTypes) {
		value = new int[numberOfBlockTypes][numberOfFaces];
	}
	
	public int getValue(BlockType blockType, Direction face) {
		return value[blockType.value][face.value];
	}
	
	public void setValue(BlockType blockType, Direction face, int value) {
		this.value[blockType.value][face.value] = value;
	}

}
