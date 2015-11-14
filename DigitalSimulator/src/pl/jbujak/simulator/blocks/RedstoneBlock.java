package pl.jbujak.simulator.blocks;

import pl.jbujak.simulator.world.Direction;

public class RedstoneBlock extends Block implements IPowerable { 
	private boolean isOn;
	
	public RedstoneBlock() {
		this.blockType = BlockType.REDSTONE;
		this.isSolid = false;
		this.isTransparent = true;
		this.isOn = false;
		this.previewId = 80;
		
		setTextureIds();
	}
	
	@Override
	public boolean isOn() {
		return isOn;
	}
	
	@Override
	public void update() {
		
	}
	
	@Override
	public Direction[] getFaces() {
		Direction[] faces = new Direction[1];
		faces[0] = Direction.DOWN;
		return faces;
	}
	
	private void setTextureIds() {
		for(Direction face: Direction.values()) {
			switch(face) {
			case DOWN:
				if(isOn) {textureId.put(face, 82);}
				else {textureId.put(face, 80);}
				break;
			default:
				textureId.put(face, 50);
			}
		}
	}
}
