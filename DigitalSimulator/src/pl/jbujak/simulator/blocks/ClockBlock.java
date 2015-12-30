package pl.jbujak.simulator.blocks;

import pl.jbujak.simulator.Simulation;
import pl.jbujak.simulator.utils.Position;
import pl.jbujak.simulator.world.Direction;

public class ClockBlock extends LogicalGate {
	private boolean isOn = false;
	private int frequency = 1000;

	public ClockBlock(Position position) {
		super(position);

		this.blockType = BlockType.CLOCK;
		this.isSolid = false;
		this.isTransparent = true;
		this.previewId = 26;
		
		setTextureIds();
		
		if(!position.equals(new Position())) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					while(!Simulation.isTerminated()) {
						try {
							Thread.sleep(frequency);
						} catch (InterruptedException e) {}
						
						isOn = !isOn;
						update();
					}
				}
			}).start();
		}
	}

	private void setTextureIds() {
		for(Direction face: Direction.values()) {
			switch(face) {
			case DOWN: 
				textureId.put(face, 26); 
				break;
			case UP: 
				textureId.put(face, 15); 
				break;
			default: 
				textureId.put(face, 19);
			}
		}
	}

	@Override
	protected boolean calculateOutput() {
		return isOn;
	}
}
