package pl.jbujak.simulator.world;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import pl.jbujak.simulator.utils.Position;

public class WorldRecord implements Serializable{
	private static final long serialVersionUID = 3705584740635085339L;
	public Set<BlockRecord> savedBlocks;
	public int xSize;
	public int ySize;
	public int zSize;
	
	public Position playerPosition;
	
	public WorldRecord(int xSize, int ySize, int zSize) {
		this.xSize = xSize;
		this.ySize = ySize;
		this.zSize = zSize;
		savedBlocks = new HashSet<>();
	}
}
