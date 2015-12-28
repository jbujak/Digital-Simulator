package pl.jbujak.simulator.blocks;

import pl.jbujak.simulator.utils.Position;
import pl.jbujak.simulator.world.Direction;

public class RedstoneLine extends RedstoneDust {

	public RedstoneLine(Position position) {
		super(position);
		this.previewId = 16;
		this.blockType = BlockType.REDSTONE_LINE;
	
		setTextureIds();
	}
	
	private void setTextureIds() {
		for(Direction face: Direction.values()) {
			switch(face) {
			case DOWN:
				textureId.put(face, 16);
				break;
			case RIGHT:
			case LEFT:
			case FRONT:
			case BACK:
				textureId.put(face, 16);
				break;
			default:
				textureId.put(face, 15);
			}
		}
	}
}
