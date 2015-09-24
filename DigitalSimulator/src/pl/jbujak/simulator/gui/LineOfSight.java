package pl.jbujak.simulator.gui;

import pl.jbujak.simulator.blocks.Block;
import pl.jbujak.simulator.environment.World;
import pl.jbujak.simulator.utils.Position;

public class LineOfSight {

	private final double sightRadius = 10;

	Position position;
	private double phi;
	private double theta;

	private final World world;
	private final Block[][][] blocks;
	private double dx = 0.01;

	private int dxSign = 1;

	// y = ay*x + by
	// z = az*x + bz
	private double ay;
	private double by;
	private double az;
	private double bz;

	public LineOfSight(World world) {
		this.world = world;
		this.blocks = world.getBlocks();
	}

	public void translateTo(Position position) {
		this.position = position;
		updateLineOfSight();
	}

	public void rotateTo(double phi, double theta) {
		this.phi = phi;
		this.theta = theta;
		updateLineOfSight();
	}

	private void updateLineOfSight() {
		if (phi < 180) {
			dxSign = 1;
		} else {
			dxSign = -1;
		}

		// Calculating z(x)
		double x0 = position.x
				- (position.z / Math.tan(Math.toRadians(phi - 90)));
		az = position.z / (position.x - x0);
		bz = -az * x0;

		// Calculating y(x)
		ay = Math.tan(Math.toRadians(-theta)) / Math.sin(Math.toRadians(phi));
		by = position.y - (position.x * Math.tan(Math.toRadians(-theta)))
				/ Math.sin(Math.toRadians(phi));
		
		dx = Math.min(Math.abs(sightRadius/ay), Math.abs(sightRadius/az)) / 100;
		dx = Math.min(dx, 0.1);

		try { 
			Position positionOfAimedBlock = getAimedBlock();
			System.out.println(positionOfAimedBlock); 
		} catch (NoBlockException e) { 
			System.out.println("No blocks in sight"); 
		}
	}

	public Position getAimedBlock() throws NoBlockException {
		Position testedPosition = new Position(position.x, position.y,
				position.z);

		for (double x = position.x; Position.distanceBetween(position,
				testedPosition) < sightRadius; x += dx * dxSign) {
			testedPosition.x = (int) Math.floor(x);
			testedPosition.y = (int) Math.floor(getY(x));
			testedPosition.z = (int) Math.floor(getZ(x));
			
			//System.out.println(testedPosition);

			if (world.isPositionOutOfWorld(testedPosition)) {
				throw new NoBlockException();
			}

			if (blocks[(int) testedPosition.x][(int) testedPosition.y][(int) testedPosition.z] != null) {
				return testedPosition;
			}
		}
		throw new NoBlockException();
	}

	private double getY(double x) {
		return ay * x + by;
	}

	private double getZ(double x) {
		return az * x + bz;
	}

}

class NoBlockException extends Exception {

	private static final long serialVersionUID = -4475878501514052554L;

}
