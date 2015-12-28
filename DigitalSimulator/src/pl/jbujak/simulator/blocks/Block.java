package pl.jbujak.simulator.blocks;

import java.util.HashMap;
import java.util.Map;

import pl.jbujak.simulator.utils.Position;
import pl.jbujak.simulator.world.Direction;

public abstract class Block {

	protected BlockType blockType;
	protected boolean isSolid;
	protected boolean isTransparent;
	protected boolean availableInInventory = true;
	protected Position position;
	
	protected Map<Direction, Integer> textureId;
	protected int previewId;
	protected Direction orientation = Direction.FRONT;
	
	public Block(Position position) {
		textureId = new HashMap<Direction, Integer>();
		previewId = 0;
		
		this.position = position;
	}
	
	public Position getPosition() {
		return position;
	}
	
	//Corner closest to point (0, 0, 0)
	public Position getActiveAreaCorner0() {
		return new Position(0, 0, 0);
	}
	
	//Corner opposite to corner0
	public Position getActiveAreaCorner1() {
		return new Position(1, 1, 1);
	}
	
	public boolean isInActiveArea(Position position) {
		Position corner0 = this.getActiveAreaCorner0();
		Position corner1 = this.getActiveAreaCorner1();
		
		double x0 = corner0.x;
		double y0 = corner0.y;
		double z0 = corner0.z;
		
		double x1 = corner1.x;
		double y1 = corner1.y;
		double z1 = corner1.z;
		
		double x = position.x;
		double y = position.y;
		double z = position.z;
		
		if(x < x0 || x > x1) {return false;}
		if(y < y0 || y > y1) {return false;}
		if(z < z0 || z > z1) {return false;}
		
		return true;
	}
	
	public boolean isAvailableInInventory() {
		return availableInInventory;
	}
	
	public void setOrientation(Direction orientation) {
		this.orientation = orientation;
	}
	
	public Direction getOrientation() {
		return orientation;
	}
	
	public float[] getColor(Direction face) {
		return new float[] {
				1,1,1, 1,1,1, 1,1,1, 1,1,1
		};
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
