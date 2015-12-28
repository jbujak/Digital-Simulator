package pl.jbujak.simulator.blocks;

import pl.jbujak.simulator.utils.Position;
import pl.jbujak.simulator.world.Direction;

public class NotGate extends LogicalGate{
	
	private boolean isBroken = false;
	private int numberOfChanges;

	public NotGate(Position position) {
		super(position);
		this.blockType = BlockType.NOT_GATE;
		this.previewId = 21;
		setTextureIds();
	}
	
	public float[] getColor(Direction face) {
		if(!isBroken) {
			return super.getColor(face);
		}
		else {
			return new float[] {
					0.3f, 0.3f, 0.3f, 1,
					0.3f, 0.3f, 0.3f, 1,
					0.3f, 0.3f, 0.3f, 1,
					0.3f, 0.3f, 0.3f, 1
			};
		}
	}

	@Override
	protected boolean calculateOutput() {
		if(isBroken) 
			return false;
		
		numberOfChanges++;
		if(numberOfChanges > 50) {
			//Assumes user can't change state 500 times per second
			isBroken = true;
		}
		
		Runnable counter = new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					numberOfChanges--;
				}
				numberOfChanges = 0;
			}
		};
		
		new Thread(counter).start();;
		
		return !backInputState;
	}

	private void setTextureIds() {
			for(Direction face: Direction.values()) {
				switch(face) {
				case DOWN: 
					textureId.put(face, 21); 
					break;
				case UP: 
					textureId.put(face, 15); 
					break;
				default: 
					textureId.put(face, 19);
				}
		}
	}
}
