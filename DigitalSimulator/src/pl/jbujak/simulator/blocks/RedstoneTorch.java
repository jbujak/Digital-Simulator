package pl.jbujak.simulator.blocks;

import pl.jbujak.simulator.world.Direction;

public class RedstoneTorch extends Block implements IPowerable {
	private boolean isOn;

	public RedstoneTorch() {
		this.blockType = BlockType.REDSTONE_TORCH;
		this.isSolid = false;
		this.isTransparent = true;
		this.isOn = true;
		this.previewId = 86;
		
		setTextureIds();
	}

	@Override
	public boolean isOn() {
		return isOn;
	}

	@Override
	public void update() {}
	
	@Override
	public Direction[] getFaces() {
		return new Direction[] {Direction.LEFT, Direction.RIGHT,
				Direction.FRONT, Direction.BACK};
	}
	
	@Override
	public float getTextureOffset(Direction face) {
		return 0.5f;
	}

	private void setTextureIds() {
		for(Direction face: Direction.values()) {
			switch(face) {
			case LEFT:
			case RIGHT:
			case FRONT:
			case BACK:
				if(isOn) {textureId.put(face, 87);}
				else {textureId.put(face, 86);}
				break;
			default:
				textureId.put(face, 50);
			}
		}
	}
	
}
