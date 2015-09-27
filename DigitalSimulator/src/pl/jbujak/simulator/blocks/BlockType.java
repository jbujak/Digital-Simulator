package pl.jbujak.simulator.blocks;

import pl.jbujak.simulator.environment.Direction;

public enum BlockType {
	BEDROCK(0), GRASS(1);
	
	public final int value;
	
	private BlockType(int value) {
		this.value = value;
	}
	
	public static String getTextureName(BlockType blockType, Direction face) {
		return blockType.getNewBlock().getTextureName(face);
	}
	
	public Block getNewBlock() {
		switch(this) {
		case BEDROCK:
			return new BedrockBlock();
		case  GRASS:
			return new GrassBlock();
		default:
			return null;
		}
	}
}
