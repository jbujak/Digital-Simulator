package pl.jbujak.simulator.blocks;

import java.util.HashSet;
import java.util.Set;

import pl.jbujak.simulator.utils.Position;
import pl.jbujak.simulator.utils.PowerableUtils;
import pl.jbujak.simulator.world.Direction;

public abstract class LogicalGate extends Block implements IPowerable {
	private final float height = 1/16f;
	private boolean isOn = false;

	public LogicalGate(Position position) {
		super(position);
		this.isSolid = false;
		this.isTransparent = true;
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
		boolean shouldBeOn = calculateOutput();
		
		if(isOn != shouldBeOn) {
			isOn = shouldBeOn;
			PowerableUtils.updateNearBlocks(position);
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

	protected abstract boolean calculateOutput();
}
