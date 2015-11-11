package pl.jbujak.simulator.blocks;

import java.util.HashMap;
import java.util.Map;

import pl.jbujak.simulator.world.Direction;

public abstract class Block {

	protected BlockType blockType;
	protected Boolean isSolid;
	protected Boolean isTransparent;

	protected Map<Direction, Integer> textureId;

	public Block() {
		textureId = new HashMap<Direction, Integer>();
	}

	public BlockType getBlockType() {return blockType;}

	public Boolean isSolid() {return isSolid;}

	public Boolean isTransparent() {return isTransparent;}

	public static final void registerBlock(Block block) {
		for(Direction face: Direction.values()) {
			BlockTextureManager.registerBlock(block.blockType, face, block.textureId.get(face));
		}
	}
}
