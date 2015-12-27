package pl.jbujak.simulator.blocks;

import pl.jbujak.simulator.utils.Position;
import pl.jbujak.simulator.utils.PowerableUtils;
import pl.jbujak.simulator.world.Direction;

public class RedstoneRepeater extends LogicalGate{

	public RedstoneRepeater(Position position) {
		super(position);
		this.blockType = BlockType.REDSTONE_REPEATER;
		this.previewId = 20;
		setTextureIds();
	}

	@Override
	protected boolean calculateOutput() {
		Position inputPosition = position.next(orientation.opposite());

		return PowerableUtils.suppliesPower(inputPosition, orientation);
	}

	private void setTextureIds() {
			for(Direction face: Direction.values()) {
				switch(face) {
				case DOWN: 
					textureId.put(face, 20); 
					break;
				case UP: 
					textureId.put(face, 15); 
					break;
				default: 
					textureId.put(face, 19);
				}
		}
	}
	

}
