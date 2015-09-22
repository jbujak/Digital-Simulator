package pl.jbujak.simulator.blocks;

import pl.jbujak.simulator.environment.Direction;

public enum BlockType {
	BEDROCK(0), GRASS(1);
	
	public final int value;
	
	private BlockType(int value) {
		this.value = value;
	}
	
	public static String getTextureName(BlockType blockType, Direction face) {
		switch(blockType) {
		case BEDROCK:
			return BedrockBlock.getTextureName(face);
		case  GRASS:
			return GrassBlock.getTextureName(face);
		}
		throw new RuntimeException("Bad block type");
	}
}
