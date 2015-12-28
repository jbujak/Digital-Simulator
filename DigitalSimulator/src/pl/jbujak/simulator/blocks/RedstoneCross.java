package pl.jbujak.simulator.blocks;

import pl.jbujak.simulator.utils.Position;
import pl.jbujak.simulator.world.Direction;

public class RedstoneCross extends RedstoneDust { 
	
	public RedstoneCross(Position position) {
		super(position);
		this.blockType = BlockType.REDSTONE_CROSS;
		this.availableInInventory = false;
		
		setTextureIds();
	}
	
	private void setTextureIds() {
		for(Direction face: Direction.values()) {
			switch(face) {
			case DOWN:
				textureId.put(face, 17);
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
