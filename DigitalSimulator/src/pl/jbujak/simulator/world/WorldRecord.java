package pl.jbujak.simulator.world;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class WorldRecord implements Serializable{
	private static final long serialVersionUID = 3705584740635085339L;
	public Set<BlockRecord> savedBlocks;
	
	public WorldRecord() {
		savedBlocks = new HashSet<>();
	}
}
