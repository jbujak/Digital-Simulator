package pl.jbujak.simulator.blocks;

import pl.jbujak.simulator.world.Direction;

public abstract class Block {

	protected BlockType blockType;
	protected Boolean isSolid;
	protected Boolean isTransparent;

	public BlockType getBlockType() {return blockType;}
	public Boolean isSolid() {return isSolid;}
	public Boolean isTransparent() {return isTransparent;}
	public abstract String getTextureName(Direction face);
}
