package pl.jbujak.simulator.blocks;

import pl.jbujak.simulator.world.Direction;

public enum BlockType {
	BEDROCK(0), GRASS(1),
	WHITE_WOOL(2)/*, ORANGE_WOOL(3), MAGENTA_WOOL(4), LIGHT_BLUE_WOOL(5), 
	YELLOW_WOOL(6), LIGHT_GREEN_WOOL(7), PINK_WOOL(8), GRAY_WOOL(9),
	LIGHT_GRAY_WOOL(10), CYAN_WOOL(11), PURPLE_WOOL(12), BLUE_WOOL(13),
	BROWN_WOOL(14), DARK_GREEN_WOOL(15), RED_WOOL(16), BLACK_WOOL(17)*/; 

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
		case GRASS:
			return new GrassBlock();
		case WHITE_WOOL:
			return new WoolBlock();
		default:
			return null;
		}
	}
}
