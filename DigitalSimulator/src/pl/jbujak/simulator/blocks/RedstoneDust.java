package pl.jbujak.simulator.blocks;

import pl.jbujak.simulator.utils.Position;
import pl.jbujak.simulator.utils.PowerableUtils;
import pl.jbujak.simulator.world.Direction;

public class RedstoneDust extends Block implements IPowerable { 
	private boolean isOn;
	
	public RedstoneDust(Position position) {
		super(position);
		this.blockType = BlockType.REDSTONE_LINE;
		this.isSolid = false;
		this.isTransparent = true;
		this.isOn = false;
		this.previewId = 80;

		if(!position.equals(new Position())) {
			update();
		}
		
		setTextureIds();
	}
	
	@Override
	public boolean isOn() {
		return isOn;
	}
	
	@Override
	public void update() {
		PowerableUtils.updateRedstoneDirection(this, position);

	}
	
	@Override
	public Direction[] getFaces() {
		Direction[] faces = new Direction[1];
		faces[0] = Direction.DOWN;
		return faces;
	}
	
	@Override
	public float[] getColor(Direction face) {
		if(isOn) {
			return new float[] {
					1,0,0, 1,0,0, 1,0,0, 1,0,0
			};
		}
		else {
			float r = 127F/255F;
			return new float[] {
					r,0,0, r,0,0, r,0,0, r,0,0
			};
		}
	}
	
	@Override
	public Position getActiveAreaCorner0() {
		return new Position(0, 0, 0);
	}
	
	@Override
	public Position getActiveAreaCorner1() {
		return new Position(1, 0.1, 1);
	}
	
	private void setTextureIds() {
		for(Direction face: Direction.values()) {
			switch(face) {
			case DOWN:
				textureId.put(face, 80);
				break;
			default:
				textureId.put(face, 50);
			}
		}
	}
}
