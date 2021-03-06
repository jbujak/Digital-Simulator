package pl.jbujak.simulator.world;

import java.io.Serializable;

import pl.jbujak.simulator.blocks.BlockType;
import pl.jbujak.simulator.utils.Position;

public class BlockRecord implements Serializable{
	private static final long serialVersionUID = -3696472500335045807L;
	public Position position;
	public BlockType blockType;
	public Direction orientation;
	
	public BlockRecord(Position position, BlockType blockType, Direction orientation) {
		this.position = position;
		this.blockType = blockType;
		this.orientation = orientation;
	}
}
