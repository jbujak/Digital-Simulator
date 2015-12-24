package pl.jbujak.simulator.blocks;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import pl.jbujak.simulator.utils.Position;
import pl.jbujak.simulator.utils.PowerableUtils;
import pl.jbujak.simulator.world.Direction;
import pl.jbujak.simulator.world.World;

public class RedstoneDust extends Block implements IPowerable { 
	private boolean isOn;
	private Map<Direction, Set<Position>> connectedSources;
	
	public RedstoneDust(Position position) {
		super(position);
		blockType = BlockType.REDSTONE_LINE;
		isSolid = false;
		isTransparent = true;
		isOn = false;
		previewId = 80;

		connectedSources = new HashMap<>();
		for(Direction direction: Direction.values()) {
			connectedSources.put(direction, new HashSet<>());
		}

		if(!position.equals(new Position())) {
			update();
		}
	
		setTextureIds();
	}
	
	@Override
	public boolean isOn() {
		return isOn;
	}
	
	@Override
	public void update() {
		PowerableUtils.updateRedstoneDirection(this, position);

		World world = World.instance;
		for(Direction direction: Direction.values()) {
			Set<Position> newSources = new HashSet<>();
			if(PowerableUtils.isPowerable(position.next(direction))) {
				IPowerable neighbour = (IPowerable)world.getBlock(position.next(direction));
				Set<Position> neighbourSources = neighbour.getConnectedSourcesWithout(direction.opposite());

				for(Position maybeSource: neighbourSources) {
					if(PowerableUtils.isPowerable(maybeSource)) {
						IPowerable maybeSourceBlock = (IPowerable)world.getBlock(maybeSource);
						if(maybeSourceBlock.isSource()) {
							newSources.add(maybeSource);
						}
					}
				}
			}
			connectedSources.put(direction, newSources);
		}
		
		System.out.println(position + " " + connectedSources);
		
		for(Direction direction: Direction.values()) {
			if(!(connectedSources.get(direction).isEmpty())) {
				if(!isOn) {
					isOn = true;
					PowerableUtils.updateNearBlocks(position);
				}
				return;
			}
		}
		if(isOn) {
			isOn = false;
			PowerableUtils.updateNearBlocks(position);
		}
	}
	
	@Override
	public Set<Position> getConnectedSourcesWithout(Direction wrongDirection) {
		Set<Position> result = new HashSet<>();
		for(Direction direction: Direction.values()) {
			if(direction != wrongDirection) {
				Set<Position> currentConnectedSources = connectedSources.get(direction);
				for(Position source: currentConnectedSources) {
					result.add(source);
				}
			}
		}
		return result;
	}
	
	@Override
	public boolean isSource() {
		return false;
	}

	@Override
	public Direction[] getFaces() {
		Direction[] faces = new Direction[1];
		faces[0] = Direction.DOWN;
		return faces;
	}
	
	@Override
	public float[] getColor(Direction face) {
		if(isOn) {
			return new float[] {
					1,0,0, 1,0,0, 1,0,0, 1,0,0
			};
		}
		else {
			float r = 127F/255F;
			return new float[] {
					r,0,0, r,0,0, r,0,0, r,0,0
			};
		}
	}
	
	@Override
	public Position getActiveAreaCorner0() {
		return new Position(0, 0, 0);
	}
	
	@Override
	public Position getActiveAreaCorner1() {
		return new Position(1, 0.1, 1);
	}
	
	private void setTextureIds() {
		for(Direction face: Direction.values()) {
			switch(face) {
			case DOWN:
				textureId.put(face, 80);
				break;
			default:
				textureId.put(face, 50);
			}
		}
	}
}
