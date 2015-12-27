package pl.jbujak.simulator.blocks;

import pl.jbujak.simulator.utils.Position;
import pl.jbujak.simulator.world.Direction;

public class OrGate extends LogicalGate{
	
	public OrGate(Position position) {
		super(position);
		this.blockType = BlockType.OR_GATE;
		this.previewId = 23;
		setTextureIds();
	}
	
	@Override
	protected boolean calculateOutput() {
		return leftInputState || rightInputState;
	}

	private void setTextureIds() {
			for(Direction face: Direction.values()) {
				switch(face) {
				case DOWN: 
					textureId.put(face, 23); 
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
