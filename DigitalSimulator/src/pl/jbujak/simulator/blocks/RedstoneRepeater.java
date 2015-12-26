package pl.jbujak.simulator.blocks;

import java.util.HashSet;
import java.util.Set;

import pl.jbujak.simulator.utils.Position;
import pl.jbujak.simulator.utils.PowerableUtils;
import pl.jbujak.simulator.world.Direction;

public class RedstoneRepeater extends Block implements IPowerable {
	private final float height = 1/16f;
	private boolean isOn = false;

	public RedstoneRepeater(Position position) {
		super(position);
		this.blockType = BlockType.REDSTONE_REPEATER;
		this.isSolid = false;
		this.isTransparent = true;
		this.previewId = 19;

		setTextureIds();
	}
	
	@Override
	public float getTextureOffset(Direction face) {
		switch(face) {
		case DOWN:
			return height;
		default:
			return 0f;
		}
	}

	@Override
	public Position getActiveAreaCorner0() {
		return new Position(0, 0, 0);
	}
	
	@Override
	public Position getActiveAreaCorner1() {
		return new Position(1, height, 1);
	}

	@Override
	public boolean carriesEnergy() {
		return false;
	}

	@Override
	public void update() {
		Position inputPosition = position.next(orientation.opposite());
		if(PowerableUtils.suppliesPower(inputPosition, orientation)) {
			if(!isOn) {
				isOn = true;
				PowerableUtils.updateNearBlocks(position);
			}
		}
		else { 
			if(isOn) {
				isOn = false;
				PowerableUtils.updateNearBlocks(position);
			}
		}
	}


	@Override
	public Set<Position> getConnectedSourcesAskedFrom(Direction direction) {
		if(!isOn) {
			return new HashSet<>();
		}
		
		Set<Position> result = new HashSet<>();
		if(direction == this.orientation)
			result.add(position);

		return result;
	}

	@Override
	public Set<Position> getSourcesConnectedFrom(Direction direction) {
		return new HashSet<>();
	}

	@Override
	public boolean isSource() {
		return true;
	}

	private void setTextureIds() {
			for(Direction face: Direction.values()) {
				switch(face) {
				case DOWN: 
					textureId.put(face, 19); 
					break;
				case UP: 
					textureId.put(face, 15); 
					break;
				default: 
					textureId.put(face, 20);
				}
		}
	}
}
