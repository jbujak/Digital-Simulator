package pl.jbujak.simulator.blocks;

import java.util.HashMap;
import java.util.Map;

import pl.jbujak.simulator.world.Direction;

public abstract class Block {

	protected BlockType blockType;
	protected Boolean isSolid;
	protected Boolean isTransparent;

	protected Map<Direction, Integer> textureId;
	protected int previewId;

	public Block() {
		textureId = new HashMap<Direction, Integer>();
		previewId = 0;
	}

	public BlockType getBlockType() {return blockType;}

	public Boolean isSolid() {return isSolid;}

	public Boolean isTransparent() {return isTransparent;}

	public static final void registerBlock(Block block) {
		int previewId = block.previewId;
		if(previewId == 0) {
			previewId = block.textureId.get(Direction.UP);
		}
		for(Direction face: Direction.values()) {
			BlockTextureManager.registerBlock(block.blockType, face, block.textureId.get(face), previewId);
		}
	}
	
	public Direction[] getFaces() {
		return Direction.values();
	}
	
	public float getTextureOffset(Direction face) {
		return 0;
	}
}
