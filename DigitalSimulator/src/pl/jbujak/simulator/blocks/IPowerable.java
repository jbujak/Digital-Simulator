package pl.jbujak.simulator.blocks;

import java.util.Set;

import pl.jbujak.simulator.utils.Position;
import pl.jbujak.simulator.world.Direction;

public interface IPowerable {
	public boolean carriesEnergy();
	
	public void update();
	
	public Set<Position> getConnectedSourcesAskedFrom(Direction direction);

	public Set<Position> getSourcesConnectedFrom(Direction direction);
	
	public boolean isSource();
	
	public Position getPosition();
}
