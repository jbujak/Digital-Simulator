package pl.jbujak.simulator.utils;

import java.util.HashMap;
import java.util.Map;

import pl.jbujak.simulator.blocks.BlockType;
import pl.jbujak.simulator.world.Direction;

public class BlockTypeFaceValue {

	private Map<BlockTypeFace, Integer> value;
	
	public BlockTypeFaceValue() {
		value = new HashMap<BlockTypeFace, Integer>();
	}
	
	public int getValue(BlockType blockType, Direction face) {
		return value.get(new BlockTypeFace(blockType, face));
	}
	
	public void setValue(BlockType blockType, Direction face, int value) {
		this.value.put(new BlockTypeFace(blockType, face), value);
	}

}

class BlockTypeFace {
	public BlockType blockType;
	public Direction face;
	
	public BlockTypeFace(BlockType blockType, Direction face) {
		this.blockType = blockType;
		this.face = face;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((blockType == null) ? 0 : blockType.hashCode());
		result = prime * result + ((face == null) ? 0 : face.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlockTypeFace other = (BlockTypeFace) obj;
		if (blockType != other.blockType)
			return false;
		if (face != other.face)
			return false;
		return true;
	}
}
