package pl.jbujak.simulator.blocks;

public abstract class Block {

	protected BlockType blockType;
	protected Boolean isSolid;
	protected Boolean isTransparent;

	public BlockType getBlockType() {
		return blockType;
	}
	
	public Boolean isSolid() {return isSolid;}
	public Boolean isTransparent() {return isTransparent;}
}
