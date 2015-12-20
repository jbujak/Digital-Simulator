package pl.jbujak.simulator.blocks;

import pl.jbujak.simulator.utils.Position;
import pl.jbujak.simulator.world.Direction;

public class GrassBlock extends Block {
	public GrassBlock(Position position) {
		super(position);
		this.blockType = BlockType.GRASS;
		this.isSolid = true;
		this.isTransparent = false;
		
		setTextureIds();
	}
	
	private void setTextureIds() {
		for(Direction face: Direction.values()) {
			switch(face) {
			case UP: textureId.put(face, 0); break;
			case DOWN: textureId.put(face, 2); break;
			default: textureId.put(face, 3); break;
			}
		}
	}
}
