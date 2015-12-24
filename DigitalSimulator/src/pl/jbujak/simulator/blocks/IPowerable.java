package pl.jbujak.simulator.blocks;

import java.util.Set;

import pl.jbujak.simulator.utils.Position;
import pl.jbujak.simulator.world.Direction;

public interface IPowerable {
	public boolean isOn();
	
	public void update();
	
	public Set<Position> getConnectedSourcesWithout(Direction direction);
	
	public boolean isSource();
}
