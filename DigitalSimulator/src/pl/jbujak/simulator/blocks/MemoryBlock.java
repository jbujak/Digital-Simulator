package pl.jbujak.simulator.blocks;

import pl.jbujak.simulator.utils.Position;
import pl.jbujak.simulator.world.Direction;

public class MemoryBlock extends LogicalGate {
	private boolean isOn;

	public MemoryBlock(Position position) {
		super(position);
		this.blockType = BlockType.MEMORY;
		this.previewId = 25;
		setTextureIds();
	}

	private void setTextureIds() {
		for(Direction face: Direction.values()) {
			switch(face) {
			case DOWN: 
				textureId.put(face, 25); 
				break;
			case UP: 
				textureId.put(face, 15); 
				break;
			default: 
				textureId.put(face, 19);
			}
		}
	}

	@Override
	protected boolean calculateOutput() {
		if (leftInputState && !rightInputState)
			isOn = false;
		else if(!leftInputState && rightInputState) 
			isOn = true;
		return isOn;
	}
}
