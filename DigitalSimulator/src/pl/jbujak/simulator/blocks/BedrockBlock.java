package pl.jbujak.simulator.blocks;

import pl.jbujak.simulator.utils.Position;
import pl.jbujak.simulator.world.Direction;

public class BedrockBlock extends Block {
	
	public BedrockBlock(Position position) {
		super(position);
		this.blockType = BlockType.BEDROCK;
		this.isSolid = true;
		this.isTransparent = false;
		
		setTextureIds();
	}
	
	private void setTextureIds() {
		for(Direction face: Direction.values()) {
			textureId.put(face, 3);
		}
	}
}
