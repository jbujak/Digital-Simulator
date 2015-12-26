package pl.jbujak.simulator.blocks;

import java.util.HashSet;
import java.util.Set;

import pl.jbujak.simulator.utils.Position;
import pl.jbujak.simulator.world.Direction;

public class RedstoneTorch extends Block implements IPowerable {
	private boolean isOn;

	public RedstoneTorch(Position position) {
		super(position);
		this.blockType = BlockType.REDSTONE_TORCH;
		this.isSolid = false;
		this.isTransparent = true;
		this.isOn = true;
		this.previewId = 18;
		
		setTextureIds();
	}

	@Override
	public boolean carriesEnergy() {
		return isOn;
	}

	@Override
	public void update() {}
	
	@Override
	public Set<Position> getConnectedSourcesAskedFrom(Direction direction) {
		Set<Position> result = new HashSet<>();
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
	
	@Override
	public Direction[] getFaces() {
		return new Direction[] {Direction.LEFT, Direction.RIGHT,
				Direction.FRONT, Direction.BACK};
	}
	
	@Override
	public float getTextureOffset(Direction face) {
		return 0.5f;
	}
	
	@Override
	public Position getActiveAreaCorner0() {
		return new Position(0.35, 0, 0.35);
	}
	
	@Override
	public Position getActiveAreaCorner1() {
		return new Position(0.65, 0.8, 0.65);
	}

	private void setTextureIds() {
		for(Direction face: Direction.values()) {
			switch(face) {
			case LEFT:
			case RIGHT:
			case FRONT:
			case BACK:
				textureId.put(face, 18);
				break;
			default:
				textureId.put(face, 15);
			}
		}
	}
	
}
