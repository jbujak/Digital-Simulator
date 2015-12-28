package pl.jbujak.simulator.blocks;

import pl.jbujak.simulator.utils.Position;
import pl.jbujak.simulator.world.Direction;

public class XorGate extends LogicalGate{
	
	private boolean isBroken = false;
	private int numberOfChanges = 0;

	public XorGate(Position position) {
		super(position);
		this.blockType = BlockType.XOR_GATE;
		this.previewId = 24;
		setTextureIds();
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
		

		return leftInputState ^ rightInputState;
	}

	public float[] getColor(Direction face) {
		if(!isBroken) {
			return super.getColor(face);
		}
		else {
			return new float[] {
					0.3f, 0.3f, 0.3f,
					0.3f, 0.3f, 0.3f,
					0.3f, 0.3f, 0.3f,
					0.3f, 0.3f, 0.3f,
			};
		}
	}


	private void setTextureIds() {
			for(Direction face: Direction.values()) {
				switch(face) {
				case DOWN: 
					textureId.put(face, 24); 
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
