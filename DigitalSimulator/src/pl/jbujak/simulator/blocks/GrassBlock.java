package pl.jbujak.simulator.blocks;

import pl.jbujak.simulator.world.Direction;

public class GrassBlock extends Block {
	public GrassBlock() {
		this.blockType = BlockType.GRASS;
		this.isSolid = true;
		this.isTransparent = false;
	}
	
	public String getTextureName(Direction face){
		switch(face) {
		case UP: return "grass_top.png";
		case DOWN: return "grass_bottom.png";
		default: return "grass_side.png";
		}
	}
}
