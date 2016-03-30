package pl.jbujak.simulator.blocks;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import pl.jbujak.simulator.utils.Position;
import pl.jbujak.simulator.utils.PowerableUtils;
import pl.jbujak.simulator.world.Direction;

public class ButtonBlock extends Block implements Powerable, Clickable {
	private boolean isOn = false;
	
	private Map<Direction, Float> textureOffset;

	public ButtonBlock(Position position) {
		super(position);
		this.blockType = BlockType.BUTTON;
		this.isSolid = false;
		this.isTransparent = false;
		
		textureOffset = new HashMap<>();
		
		setTextureOffsets();
		setTextureIds();
	}
	
	@Override
	public void click() {
		isOn = true;
		PowerableUtils.updateNearBlocks(position);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {}
				
				isOn = false;
				PowerableUtils.updateNearBlocks(position);
			}
		}).start();;
		
	}

	@Override
	public boolean carriesEnergy() {
		return false;
	}

	@Override
	public void update() {

	}

	@Override
	public Set<Position> getConnectedSourcesAskedFrom(Direction direction) {
		Set<Position> result = new HashSet<>();
		result.add(position);
		return result;
	}

	@Override
	public Set<Position> getSourcesConnectedFrom(Direction direction) {
		return new HashSet<>();
	}

	@Override
	public boolean isSource() {
		return isOn;
	}
	
	@Override
	public float getTextureOffset(Direction face) {
		return textureOffset.get(face);
	}
	
	@Override
	public Position getActiveAreaCorner0() {
		return new Position(5f/16, 0, 6f/16);
	}
	
	@Override
	public Position getActiveAreaCorner1() {
		return new Position(11f/16, 2f/16, 10f/16);
	}
	
	private void setTextureOffsets() {
		textureOffset.put(Direction.RIGHT, 5f/16);
		textureOffset.put(Direction.LEFT, 5f/16);
		textureOffset.put(Direction.FRONT, 6f/16);
		textureOffset.put(Direction.BACK, 6f/16);
		textureOffset.put(Direction.UP, 14f/16);
		textureOffset.put(Direction.DOWN, 0f/16);
	}
	
	private void setTextureIds() {
		textureId.put(Direction.FRONT, 7);
		textureId.put(Direction.BACK, 7);
		textureId.put(Direction.LEFT, 8);
		textureId.put(Direction.RIGHT, 8);
		textureId.put(Direction.UP, 6);
		textureId.put(Direction.DOWN, 6);
	}
}
