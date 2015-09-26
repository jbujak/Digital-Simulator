package pl.jbujak.simulator.environment;

import pl.jbujak.simulator.gui.ICameraEngine;
import pl.jbujak.simulator.utils.Position;

public class Player implements IPlayer {
	private final double jumpVelocity = 0.15;

	private Position position;
	private double yVelocity = 0;
	
	private Boolean isFlying = false;
	private MovementEngine movementEngine;
	private GravityEngine gravityEngine;

	public Player(Position position, IWorld world, ICameraEngine cameraEngine) {
		this.position = position;
		this.movementEngine = new MovementEngine(cameraEngine, position, world);
		this.gravityEngine = new GravityEngine(this, movementEngine);

		moveBy(0, Direction.FRONT);
		rotateBy(0, 0);
	}

	public void rotateBy(double phi, double theta) {
		movementEngine.rotateBy(phi, theta);
	}

	public void move(Direction direction) {
		movementEngine.move(direction);
	}

	public void moveBy(double stepLength, Direction direction) {
		movementEngine.moveBy(stepLength, direction);
	}

	public void jump() {
		if (isStandingOnSolid()) {
			gravityEngine.jump(jumpVelocity);
		}
	}

	public boolean isStandingOnSolid() {
		return movementEngine.isStandingOnSolid();
	}

	public double getYVelocity() {
		return yVelocity;
	}

	public void decreaseYVelocityBy(double yV) {
		yVelocity -= yV;
	}

	public void zeroYVelocity() {
		yVelocity = 0;
	}
	
	public Position getPosition() {
		return position;
	}

	public void toggleIsFlying() {
		isFlying = !isFlying;
	}

	public boolean isFlying() {
		return isFlying;
	}
	
	public GravityEngine getGravityEngine() {
		return gravityEngine;
	}
}