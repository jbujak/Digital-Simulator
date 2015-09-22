package pl.jbujak.simulator.blocks;

import pl.jbujak.simulator.environment.Direction;

public class GrassBlock extends Block {
	public GrassBlock() {
		this.blockType = BlockType.GRASS;
		isSolid = true;
		isTransparent = false;
	}
	
	public static String getTextureName(Direction face){
		switch(face) {
		case UP: return "grass_top.png";
		case DOWN: return "grass_bottom.png";
		default: return "grass_side.png";
		}
	}
}
