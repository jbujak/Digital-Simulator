package pl.jbujak.simulator.blocks;

import pl.jbujak.simulator.world.Direction;

public class RedstoneBlock extends Block {
	
	public RedstoneBlock() {
		this.blockType = BlockType.REDSTONE;
		this.isSolid = true;
		this.isTransparent = false;
		
		setTextureIds();
	}
	
	private void setTextureIds() {
		for(Direction face: Direction.values()) {
			switch(face) {
			case UP:
				textureId.put(face, 80); break;
			default:
				textureId.put(face, 50);
			}
		}
	}

}
