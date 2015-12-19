package pl.jbujak.simulator.blocks;

import pl.jbujak.simulator.world.Direction;

public enum BlockType {
	BEDROCK, GRASS,
	WHITE_WOOL, ORANGE_WOOL, MAGENTA_WOOL, LIGHT_BLUE_WOOL, 
	YELLOW_WOOL, LIGHT_GREEN_WOOL, PINK_WOOL, GRAY_WOOL,
	LIGHT_GRAY_WOOL, CYAN_WOOL, PURPLE_WOOL, BLUE_WOOL,
	BROWN_WOOL, DARK_GREEN_WOOL, RED_WOOL, BLACK_WOOL,
	REDSTONE, REDSTONE_TORCH;
	
	public Direction[] getFaces() {
		return this.getNewBlock().getFaces();
	}
	
	public boolean isSolid() {
		return this.getNewBlock().isSolid();
	}
	
	public boolean isTransparent() {
		return this.getNewBlock().isTransparent();
	}
	
	public float getTextureOffset(Direction face) {
		return this.getNewBlock().getTextureOffset(face);
	}
	
	public Block getNewBlock() {
		
		switch(this) {
		case BEDROCK:
			return new BedrockBlock();
		case GRASS:
			return new GrassBlock();
		case WHITE_WOOL:
			return new WoolBlock(Color.WHITE);
		case ORANGE_WOOL:
			return new WoolBlock(Color.ORANGE);
		case MAGENTA_WOOL:
			return new WoolBlock(Color.MAGENTA);
		case LIGHT_BLUE_WOOL:
			return new WoolBlock(Color.LIGHT_BLUE);
		case YELLOW_WOOL:
			return new WoolBlock(Color.YELLOW);
		case BLACK_WOOL:
			return new WoolBlock(Color.BLACK);
		case BLUE_WOOL:
			return new WoolBlock(Color.BLUE);
		case BROWN_WOOL:
			return new WoolBlock(Color.BROWN);
		case CYAN_WOOL:
			return new WoolBlock(Color.CYAN);
		case DARK_GREEN_WOOL:
			return new WoolBlock(Color.DARK_GREEN);
		case GRAY_WOOL:
			return new WoolBlock(Color.GRAY);
		case LIGHT_GRAY_WOOL:
			return new WoolBlock(Color.LIGHT_GRAY);
		case LIGHT_GREEN_WOOL:
			return new WoolBlock(Color.LIGHT_GREEN);
		case PINK_WOOL:
			return new WoolBlock(Color.PINK);
		case PURPLE_WOOL:
			return new WoolBlock(Color.PURPLE);
		case RED_WOOL:
			return new WoolBlock(Color.RED);
		case REDSTONE:
			return new RedstoneDust();
		case REDSTONE_TORCH:
			return new RedstoneTorch();
		default:
			return null;
		}
	}
}
