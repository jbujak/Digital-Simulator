package pl.jbujak.simulator.blocks;

import java.util.HashSet;
import java.util.Set;

import pl.jbujak.simulator.utils.Position;
import pl.jbujak.simulator.utils.PowerableUtils;
import pl.jbujak.simulator.world.Direction;
import pl.jbujak.simulator.world.World;

public class PixelBlock extends Block implements Powerable {
	private boolean isOn = false;

	public PixelBlock(Position position) {
		super(position);
		this.blockType = BlockType.PIXEL;
		this.isSolid = true;
		this.isTransparent = false;
		
		setTextureIds();
	}

	
	private void setTextureIds() {
		for(Direction face: Direction.values()) {
			textureId.put(face, 5);
		}
	}

	@Override
	public float[] getColor(Direction face) {
		if(isOn) {
			return new float[] {
					1,1,1,1, 1,1,1,1, 1,1,1,1, 1,1,1,1
			};
		}
		else {
			float color = 48/255f;
			return new float[] {
					color,color,color,1,
					color,color,color,1,
					color,color,color,1,
					color,color,color,1
			};
		}
	}

	@Override
	public boolean carriesEnergy() {
		return false;
	}


	@Override
	public void update() {
		boolean oldIsOn = isOn;
		isOn = false;
		for(Direction height: new Direction[] {Direction.DOWN, null, Direction.UP }) {
			for(Direction direction: Direction.values()) {
				if(PowerableUtils.suppliesPower(position.next(direction).next(height), direction.opposite()))
					isOn = true;
			}
		}
		if(isOn != oldIsOn) {
			World.instance.changeBlock(position, this);
		}
	}


	@Override
	public Set<Position> getConnectedSourcesAskedFrom(Direction direction) {
		return new HashSet<>();
	}


	@Override
	public Set<Position> getSourcesConnectedFrom(Direction direction) {
		return new HashSet<>();
	}


	@Override
	public boolean isSource() {
		return false;
	}

}
