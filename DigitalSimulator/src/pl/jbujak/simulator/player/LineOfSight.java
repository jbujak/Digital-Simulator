package pl.jbujak.simulator.player;

import pl.jbujak.simulator.blocks.Block;
import pl.jbujak.simulator.utils.Position;
import pl.jbujak.simulator.world.Direction;
import pl.jbujak.simulator.world.IWorld;
import static java.lang.Math.*;

public class LineOfSight {

	private final double sightRadius = 7;

	Position position;
	private double phi;
	private double theta;

	private final IWorld world;
	private Block[][][] blocks;
	private double dx = 0.001;

	private int dxSign = 1;

	// y = ay*x + by
	// z = az*x + bz
	private double ay;
	private double by;
	private double az;
	private double bz;
	
	private Position selectedBlock;
	private Direction selectedFace;

	public LineOfSight(IWorld world) {
		this.world = world;
		position = new Position();
	}

	public void translateTo(Position position) {
		if(position.x == 0) {position.x = 0.1;}
		if(position.y == 0) {position.y = 0.1;}
		if(position.z == 0) {position.z = 0.1;}
		this.position = position;
		updateLineOfSight();
	}

	public void rotateTo(double phi, double theta) {
		theta = min(theta, 88.5);
		theta = max(theta, -88.5);
		
		if(phi < 2) {phi = 2;}
		if(phi > 358) {phi = 358;}
		if(abs(phi-180) < 2) {phi += 2*signum(phi-180);}
		
		this.phi = phi;
		this.theta = theta;
		updateLineOfSight();
	}
	
	public Position getSelectedBlock() {
		return selectedBlock;
	}

	private void updateLineOfSight() {
		blocks = world.getBlocks();
		if (phi < 180) {
			dxSign = 1;
		} else {
			dxSign = -1;
		}

		calculateZOfX();
		calculateYOfX();
		
		calculateSelectedBlock();
		calculateSelectedFace();
		
		world.setSelectedBlock(selectedBlock);
		world.setSelectedFace(selectedFace);
	}

	private void calculateYOfX() {
		ay = tan(toRadians(-theta)) / sin(toRadians(phi));
		by = position.y - (position.x * tan(toRadians(-theta))) / sin(toRadians(phi));
	}

	private void calculateZOfX() {
		double x0 = position.x - (position.z / tan(toRadians(phi - 90)));
		az = position.z / (position.x - x0);
		bz = -az * x0;
	}

	private void calculateSelectedBlock() {
		Position testedPosition = new Position(position.x, position.y,
				position.z);

		for (double x = position.x; Position.distanceBetween(position,
				testedPosition) < sightRadius; x += dx * dxSign) {
			testedPosition.x = Math.floor(x);
			testedPosition.y = Math.floor(getY(x));
			testedPosition.z = Math.floor(getZ(x));

			if (world.isPositionOutOfWorld(testedPosition)) {
				selectedBlock = null;
				return;
			}

			Block testedBlock = blocks[(int)testedPosition.x][(int)testedPosition.y][(int)testedPosition.z];
			Position relativePosition = new Position();
			relativePosition.x = x - testedPosition.x;
			relativePosition.y = getY(x) - testedPosition.y;
			relativePosition.z = getZ(x) - testedPosition.z;
			
			if (testedBlock != null && testedBlock.isInActiveArea(relativePosition)) {
				selectedBlock = testedPosition;
				return;
			}
		}
		selectedBlock = null;
	}
	
	private void calculateSelectedFace() {
		if(selectedBlock == null) {
			selectedFace = null;
			return;
		}
		
		int selectedX = (int)selectedBlock.x;
		int selectedY = (int)selectedBlock.y;
		int selectedZ = (int)selectedBlock.z;
		
		Position corner0 = blocks[selectedX][selectedY][selectedZ].getActiveAreaCorner0();
		Position corner1 = blocks[selectedX][selectedY][selectedZ].getActiveAreaCorner1();
		
		double x0 = corner0.x;
		double y0 = corner0.y;
		double z0 = corner0.z;

		double x1 = corner1.x;
		double y1 = corner1.y;
		double z1 = corner1.z;

		if(position.z > selectedBlock.z + z1) {
			double x = (selectedBlock.z + z1 - bz) / az;
			if(x > selectedBlock.x + x0 && x < selectedBlock.x+x1) {
				double y = getY(x);
				if(y > selectedBlock.y + y0 && y < selectedBlock.y+y1) {
					selectedFace = Direction.FRONT;
				}
			}
		}
		if(position.z < selectedBlock.z + z0) {
			double x = (selectedBlock.z + z0 - bz) / az;
			if(x > selectedBlock.x + x0 && x < selectedBlock.x + x1) {
				double y = getY(x);
				if(y > selectedBlock.y + y0 && y < selectedBlock.y + y1) {
					selectedFace = Direction.BACK;
				}
			}
		}

		if(position.y > selectedBlock.y + y1) {
			double x = (selectedBlock.y + y1 - by) / ay;
			if(x > selectedBlock.x + x0 && x < selectedBlock.x + x1) {
				double z = getZ(x);
				if(z > selectedBlock.z + z0 && z < selectedBlock.z + z1) {
					selectedFace = Direction.UP;
				}
			}
		}
		if(position.y < selectedBlock.y + y0) {
			double x = (selectedBlock.y + y0 - by) / ay;
			if(x > selectedBlock.x + x0 && x < selectedBlock.x + x1) {
				double z = getZ(x);
				if(z > selectedBlock.z + z0 && z < selectedBlock.z + z1) {
					selectedFace = Direction.DOWN;
				}
			}
		}
		
		if(position.x > selectedBlock.x + z1) {
			double x = selectedBlock.x + z1;
			double y = getY(x);
			if(y > selectedBlock.y + y0&& y < selectedBlock.y + y1) {
				double z = getZ(x);
				if(z > selectedBlock.z + z0 && z < selectedBlock.z + z1) {
					selectedFace = Direction.RIGHT;
				}
			}
		}
		if(position.x < selectedBlock.x + x0) {
			double x = selectedBlock.x + x0;
			double y = getY(x);
			if(y > selectedBlock.y + x0 && y < selectedBlock.y + y1) {
				double z = getZ(x);
				if(z > selectedBlock.z + z0 && z < selectedBlock.z + z1) {
					selectedFace = Direction.LEFT;
				}
			}
		}
	}

	private double getY(double x) {
		return ay * x + by;
	}

	private double getZ(double x) {
		return az * x + bz;
	}

}
