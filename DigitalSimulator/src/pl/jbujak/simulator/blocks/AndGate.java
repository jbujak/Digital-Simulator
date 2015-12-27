package pl.jbujak.simulator.blocks;

import pl.jbujak.simulator.utils.Position;
import pl.jbujak.simulator.world.Direction;

public class AndGate extends LogicalGate{
	
	public AndGate(Position position) {
		super(position);
		this.blockType = BlockType.AND_GATE;
		this.previewId = 22;
		setTextureIds();
	}
	
	@Override
	protected boolean calculateOutput() {
		return leftInputState && rightInputState;
	}

	private void setTextureIds() {
			for(Direction face: Direction.values()) {
				switch(face) {
				case DOWN: 
					textureId.put(face, 22); 
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
