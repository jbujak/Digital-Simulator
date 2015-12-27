package pl.jbujak.simulator.blocks;

import pl.jbujak.simulator.utils.Position;
import pl.jbujak.simulator.world.Direction;

public enum BlockType {
	BEDROCK, GRASS,
	WHITE_WOOL, ORANGE_WOOL, MAGENTA_WOOL, LIGHT_BLUE_WOOL, 
	YELLOW_WOOL, LIGHT_GREEN_WOOL, PINK_WOOL, GRAY_WOOL,
	LIGHT_GRAY_WOOL, CYAN_WOOL, PURPLE_WOOL, BLUE_WOOL,
	BROWN_WOOL, DARK_GREEN_WOOL, RED_WOOL, BLACK_WOOL,
	REDSTONE_LINE, REDSTONE_CROSS, REDSTONE_TORCH, REDSTONE_REPEATER,
	NOT_GATE, AND_GATE, OR_GATE, XOR_GATE;
	
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
		return getNewBlock(new Position());
	}
	
	public Block getNewBlock(Position position) {
		
		switch(this) {
		case BEDROCK:
			return new BedrockBlock(position);
		case GRASS:
			return new GrassBlock(position);
		case WHITE_WOOL:
			return new WoolBlock(position, Color.WHITE);
		case ORANGE_WOOL:
			return new WoolBlock(position, Color.ORANGE);
		case MAGENTA_WOOL:
			return new WoolBlock(position, Color.MAGENTA);
		case LIGHT_BLUE_WOOL:
			return new WoolBlock(position, Color.LIGHT_BLUE);
		case YELLOW_WOOL:
			return new WoolBlock(position, Color.YELLOW);
		case BLACK_WOOL:
			return new WoolBlock(position, Color.BLACK);
		case BLUE_WOOL:
			return new WoolBlock(position, Color.BLUE);
		case BROWN_WOOL:
			return new WoolBlock(position, Color.BROWN);
		case CYAN_WOOL:
			return new WoolBlock(position, Color.CYAN);
		case DARK_GREEN_WOOL:
			return new WoolBlock(position, Color.DARK_GREEN);
		case GRAY_WOOL:
			return new WoolBlock(position, Color.GRAY);
		case LIGHT_GRAY_WOOL:
			return new WoolBlock(position, Color.LIGHT_GRAY);
		case LIGHT_GREEN_WOOL:
			return new WoolBlock(position, Color.LIGHT_GREEN);
		case PINK_WOOL:
			return new WoolBlock(position, Color.PINK);
		case PURPLE_WOOL:
			return new WoolBlock(position, Color.PURPLE);
		case RED_WOOL:
			return new WoolBlock(position, Color.RED);
		case REDSTONE_LINE:
			return new RedstoneLine(position);
		case REDSTONE_CROSS:
			return new RedstoneCross(position);
		case REDSTONE_TORCH:
			return new RedstoneTorch(position);
		case REDSTONE_REPEATER:
			return new RedstoneRepeater(position);
		case NOT_GATE:
			return new NotGate(position);
		case AND_GATE:
			return new AndGate(position);
		case OR_GATE:
			return new OrGate(position);
		case XOR_GATE:
			return new XorGate(position);
		default:
			return null;
		}
	}
}
