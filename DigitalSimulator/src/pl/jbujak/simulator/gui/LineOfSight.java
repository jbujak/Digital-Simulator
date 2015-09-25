package pl.jbujak.simulator.gui;

import pl.jbujak.simulator.blocks.Block;
import pl.jbujak.simulator.environment.IWorld;
import pl.jbujak.simulator.utils.Position;

public class LineOfSight {

	private final double sightRadius = 10;

	Position position;
	private double phi;
	private double theta;

	private final IWorld world;
	private Block[][][] blocks;
	private double dx = 0.01;

	private int dxSign = 1;

	// y = ay*x + by
	// z = az*x + bz
	private double ay;
	private double by;
	private double az;
	private double bz;

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
		if(phi == 0) {phi = 0.1;}
		if(theta == 90) {theta = 89.9;}
		if(theta == -90) {theta = -89.9;}

		this.phi = phi;
		this.theta = theta;
		updateLineOfSight();
	}

	private void updateLineOfSight() {
		blocks = world.getBlocks();
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

		dx = Math.min(Math.abs(sightRadius / ay), Math.abs(sightRadius / az)) / 100;
		dx = Math.min(dx, 0.1);
	}

	public Position getAimedBlock() throws NoBlockException {
		Position testedPosition = new Position(position.x, position.y,
				position.z);

		for (double x = position.x; Position.distanceBetween(position,
				testedPosition) < sightRadius; x += dx * dxSign) {
			testedPosition.x = Math.floor(x);
			testedPosition.y = Math.floor(getY(x));
			testedPosition.z = Math.floor(getZ(x));

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
