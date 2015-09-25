package pl.jbujak.simulator.environment;

import pl.jbujak.simulator.gui.ICameraEngine;
import pl.jbujak.simulator.gui.LineOfSight;
import pl.jbujak.simulator.utils.Position;

public class Player implements IPlayer {
	private final double defaultStepLength = 0.10;
	private final double maxHeightOverGround = 0.2;
	private final double jumpVelocity = 0.15;
	private final double playerHeight = 1.5;

	private double phi;
	private double theta;
	private Position position;
	private double yVelocity = 0;
	
	private LineOfSight lineOfSight;
	
	private Boolean isFlying = false;
	private ICameraEngine cameraEngine;
	private IWorld world;

	public Player(Position position, IWorld world, ICameraEngine cameraEngine) {
		this.position = position;
		this.world = world;
		this.cameraEngine = cameraEngine;
		this.lineOfSight = new LineOfSight(world);

		moveBy(0, Direction.FRONT);
		rotateBy(0, 0);
	}

	public void toggleIsFlying() {
		isFlying = !isFlying;
	}

	public boolean isFlying() {
		return isFlying;
	}

	public void rotateBy(double phi, double theta) {
		this.phi += phi;
		this.phi = this.phi % 360;
		if(this.phi == 0.0) {this.phi += 0.1;}
		if(this.phi < 0 ) {this.phi += 360;}
		
		
		if (Math.abs(this.theta + theta) < 90) {this.theta += theta;}
		if(this.theta == 0) {this.theta += 0.1;}

		cameraEngine.rotateTo(this.phi, this.theta);
		lineOfSight.rotateTo(this.phi, this.theta);
	}

	public void move(Direction direction) {
		moveBy(defaultStepLength, direction);
	}

	public void moveBy(double stepLength, Direction direction) {
		executeMovement(stepLength, direction);

		sendCurrentPosToCamera();
	}

	public void jump() {
		if (isStandingOnSolid()) {
			yVelocity = jumpVelocity;
		}
	}

	public boolean isStandingOnSolid() {
		Position positionOfBlockBelow = new Position(position.x,
				position.y - 1, position.z);
		return world.isBlockSolid(positionOfBlockBelow) && isStandingOnBlock();
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

	private void sendCurrentPosToCamera() {
		Position positionOfEyes = new Position(position.x, position.y
				+ playerHeight, position.z);
		cameraEngine.translateTo(positionOfEyes);
		lineOfSight.translateTo(positionOfEyes);
	}

	private Boolean isStandingOnBlock() {
		return position.y - Math.floor(position.y) < maxHeightOverGround;
	}

	private void executeMovement(double stepLength, Direction direction) {
		switch (direction) {
		case FRONT:
			moveForwardBy(stepLength);
			break;
		case BACK:
			moveBackwardBy(stepLength);
			break;
		case LEFT:
			moveLeftBy(stepLength);
			break;
		case RIGHT:
			moveRightBy(stepLength);
			break;
		case UP:
			moveUpBy(stepLength);
			break;
		case DOWN:
			moveDownBy(stepLength);
			break;
		}
	}

	private void moveForwardBy(double stepLength) {
		moveAlongZAxis(-stepLength * Math.cos(Math.toRadians(phi)));
		moveAlongXAxis(stepLength * Math.sin(Math.toRadians(phi)));

	}

	private void moveBackwardBy(double stepLength) {
		moveAlongZAxis(stepLength * Math.cos(Math.toRadians(phi)));
		moveAlongXAxis(-stepLength * Math.sin(Math.toRadians(phi)));
	}

	private void moveLeftBy(double stepLength) {
		moveAlongXAxis(-stepLength * Math.cos(Math.toRadians(phi)));
		moveAlongZAxis(-stepLength * Math.sin(Math.toRadians(phi)));
	};

	private void moveRightBy(double stepLength) {
		moveAlongXAxis(stepLength * Math.cos(Math.toRadians(phi)));
		moveAlongZAxis(stepLength * Math.sin(Math.toRadians(phi)));
	}

	private void moveDownBy(double stepLength) {
		moveAlongYAxis(-stepLength);
	}

	private void moveUpBy(double stepLength) {
		moveAlongYAxis(stepLength);
	}

	private void moveAlongXAxis(double stepLength) {
		Position newPosition = position.copy();
		newPosition.x += stepLength + Math.signum(stepLength)*3*defaultStepLength;
		if(isPositionValid(newPosition)) {
			position.x += stepLength;
		}
	}

	private void moveAlongYAxis(double stepLength) {
		Position newPosition = position.copy();
		newPosition.y += stepLength + maxHeightOverGround;
		if(isPositionValid(newPosition)) {
			position.y += stepLength;
		}
	}

	private void moveAlongZAxis(double stepLength) {
		Position newPosition = position.copy();
		newPosition.z += stepLength + Math.signum(stepLength)*3*defaultStepLength;
		if(isPositionValid(newPosition)) {
			position.z += stepLength;
		}
	}
	
	private boolean isPositionValid(Position position) {
		if(world.isPositionOutOfWorld(position)) {return false;}
		if(world.isBlockSolid(position)) {return false;}
		return true;
	}
}