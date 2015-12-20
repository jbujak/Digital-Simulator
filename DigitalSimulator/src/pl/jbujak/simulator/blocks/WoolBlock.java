package pl.jbujak.simulator.blocks;

import pl.jbujak.simulator.utils.Position;
import pl.jbujak.simulator.world.Direction;

public class WoolBlock extends Block {
	
	private Color color;
	
	public WoolBlock(Position position, Color color) {
		super(position);
		this.color = color;
		setBlockType();
		this.isSolid = true;
		this.isTransparent = false;
		
		setTextureIds();
	}

	private void setBlockType() {
		switch(color) {
		case WHITE:
			this.blockType = BlockType.WHITE_WOOL; break;
		case ORANGE:
			this.blockType = BlockType.ORANGE_WOOL; break;
		case MAGENTA:
			this.blockType = BlockType.MAGENTA_WOOL; break;
		case LIGHT_BLUE:
			this.blockType = BlockType.LIGHT_BLUE_WOOL; break;
		case YELLOW: 
			this.blockType = BlockType.YELLOW_WOOL; break;
		case LIGHT_GREEN:
			this.blockType = BlockType.LIGHT_GREEN_WOOL; break;
		case PINK:
			this.blockType = BlockType.PINK_WOOL; break;
		case GRAY:
			this.blockType = BlockType.GRAY_WOOL; break;
		case LIGHT_GRAY:
			this.blockType = BlockType.LIGHT_GRAY_WOOL; break;
		case CYAN:
			this.blockType = BlockType.CYAN_WOOL; break;
		case PURPLE:
			this.blockType = BlockType.PURPLE_WOOL; break;
		case BLUE:
			this.blockType = BlockType.BLUE_WOOL; break;
		case BROWN:
			this.blockType = BlockType.BROWN_WOOL; break;
		case DARK_GREEN:
			this.blockType = BlockType.DARK_GREEN_WOOL; break;
		case RED:
			this.blockType = BlockType.RED_WOOL; break;
		case BLACK:
			this.blockType = BlockType.BLACK_WOOL; break;
		}
	}
	
	private void setTextureIds() {
		for(Direction face: Direction.values()) {
			textureId.put(face, 64 + color.offset);
		}
	}
}
