package pl.jbujak.simulator.blocks;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import pl.jbujak.simulator.utils.Position;
import pl.jbujak.simulator.utils.PowerableUtils;
import pl.jbujak.simulator.world.Direction;
import pl.jbujak.simulator.world.World;

public abstract class RedstoneDust extends Block implements IPowerable { 
	protected boolean isOn;
	protected Map<Direction, Set<Position>> connectedSources;
	
	public RedstoneDust(Position position) {
		super(position);
		this.isSolid = false;
		this.isTransparent = true;
		this.isOn = false;

		connectedSources = new HashMap<>();
		for(Direction direction: Direction.values()) {
			connectedSources.put(direction, new HashSet<>());
		}

		if(!position.equals(new Position())) {
			update();
		}
	}
	
	@Override
	public boolean isOn() {
		return isOn;
	}
	
	@Override
	public void update() {
		updateDirection();
		updateIsOn();
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
	public Set<Position> getConnectedSourcesFrom(Direction direction) {
		return connectedSources.get(direction);
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
	
	private void updateDirection() {
		World world = World.instance;
		
		if(PowerableUtils.isPowerable(position.next(Direction.RIGHT)) || 
		   PowerableUtils.isPowerable(position.next(Direction.LEFT))) {
			
			if(PowerableUtils.isPowerable(position.next(Direction.FRONT)) ||
			   PowerableUtils.isPowerable(position.next(Direction.BACK))) {
				if(!(this instanceof RedstoneCross)) {
					world.changeBlock(position, new RedstoneCross(position));
				}
			}
			else {
				if(!(this instanceof RedstoneLine)) {
					world.changeBlock(position, new RedstoneLine(position));
				}
				if(getOrientation() != Direction.RIGHT) {
					setOrientation(Direction.RIGHT);
					PowerableUtils.updateNearBlocks(position);
				}
			}
		}
		else {
			if(!(this instanceof RedstoneLine)) {
				world.changeBlock(position, new RedstoneLine(position));
			}
			if(PowerableUtils.isPowerable(position.next(Direction.FRONT)) ||
			   PowerableUtils.isPowerable(position.next(Direction.BACK))) {
				if(getOrientation() != Direction.FRONT) {
					setOrientation(Direction.FRONT);
					PowerableUtils.updateNearBlocks(position);
				}
			}
		}
	}
	
	private void updateIsOn() {
		boolean changed = false;

		World world = World.instance;
		for(Direction direction: Direction.values()) {
			Set<Position> newSources = new HashSet<>();
			if(PowerableUtils.isPowerable(position.next(direction))) {
				IPowerable neighbour = (IPowerable)world.getBlock(position.next(direction));
				
				if(!neighbour.getConnectedSourcesFrom(direction.opposite()).isEmpty()) {
					//We don't want to keep cycle
					//It would mean infinite energy
					continue;
				}
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
			if(!(connectedSources.get(direction).equals(newSources))) {
				connectedSources.put(direction, newSources);
				changed = true;
			}
		}
		
		System.out.println(position + " " + connectedSources);
		
		boolean shouldBeOn = false;
		for(Direction direction: Direction.values()) {
			if(!(connectedSources.get(direction).isEmpty())) {
				shouldBeOn = true;
			}
		}
		isOn = shouldBeOn;
		
		if(changed) {
			PowerableUtils.updateNearBlocks(position);
		}	
	}
}
