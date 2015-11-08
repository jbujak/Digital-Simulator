package pl.jbujak.simulator.blocks;

import pl.jbujak.simulator.world.Direction;

public enum BlockType {
	BEDROCK(0), GRASS(1);
	
	public final int value;
	
	private BlockType(int value) {
		this.value = value;
	}
	
	public String getTextureName(Direction face) {
		return this.getNewBlock().getTextureName(face);
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
