package pl.jbujak.simulator.player;

import pl.jbujak.simulator.gui.ICameraEngine;
import pl.jbujak.simulator.utils.Position;
import pl.jbujak.simulator.world.Direction;
import pl.jbujak.simulator.world.World;

public class MovementEngine {
	private final double defaultStepLenghtWalking = 0.07;
	private final double playerHeight = 1.5;
	private final double maxHeightOverGround = 0.2;
	
	private double defaultStepLength = defaultStepLenghtWalking;
	
	private ICameraEngine cameraEngine;
	private LineOfSight lineOfSight;
	private World world;
	
	Position position;
	private double phi;
	private double theta;

	public MovementEngine(ICameraEngine cameraEngine, Position position, World world) {
		this.cameraEngine = cameraEngine;
		this.position = position;
		this.world = world;
		this.lineOfSight = new LineOfSight(world);
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

	public boolean isStandingOnSolid() {
		Position positionOfBlockBelow = new Position(position.x,
				position.y - 1, position.z);
		return world.isBlockSolid(positionOfBlockBelow) && isStandingOnBlock();
	}
	
	public Position getPosition() {
		return position;
	}
	
	public void setPosition(Position position) {
		this.position = position;
	}
	
	public Direction getDirection() {
		if(phi > 325 || phi <= 45) return Direction.FRONT;
		if(phi >  45 && phi <=135) return Direction.RIGHT;
		if(phi >135 && phi <= 225) return Direction.BACK;
		return Direction.LEFT;
	}
	
	public void startRunning() {
		defaultStepLength = defaultStepLenghtWalking * 2;
	}
	
	public void stopRunning() {
		defaultStepLength = defaultStepLenghtWalking;
	}
	
	public void restart() {
		position = new Position(world.getXSize() / 2, world.getYSize() - playerHeight, world.getZSize() / 2);
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

	private void sendCurrentPosToCamera() {
		Position positionOfEyes = new Position(position.x, position.y
				+ playerHeight, position.z);
		cameraEngine.translateTo(positionOfEyes);
		lineOfSight.translateTo(positionOfEyes);
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
		if(world.isBlockSolid(new Position(position.x, position.y+1, position.z))) {return false;}
		return true;
	}

	private Boolean isStandingOnBlock() {
		return position.y - Math.floor(position.y) < maxHeightOverGround;
	}
}
