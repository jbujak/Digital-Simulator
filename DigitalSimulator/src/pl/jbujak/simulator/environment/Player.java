package pl.jbujak.simulator.environment;

import pl.jbujak.simulator.gui.CameraEngine;

public class Player {
	private final double defaultStepLength = 0.15;
	private final double maxHeightOverGround = 0.2;
	private final double jumpVelocity = 0.15;
	private final double playerHeight = 1.5;

	private double phi;
	private double theta;
	private double xPos;
	private double yPos;
	private double zPos;
	private double yVelocity = 0;
	private Boolean isFlying = false;
	private CameraEngine cameraEngine;
	private World world;

	public Player(double xPos, double yPos, double zPos, CameraEngine engine, World world) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.zPos = zPos;
		this.cameraEngine = engine;
		this.world = world;

		cameraEngine.translateTo(xPos, yPos, zPos);
		cameraEngine.rotateTo(0, 0);
	}
	
	public void toggleIsFlying() {
		isFlying = !isFlying;
	}
	
	public Boolean isFlying() {
		return isFlying;
	}
	
	public void rotateBy(double phi, double theta) {
		this.phi += phi;
		if (Math.abs(this.theta + theta) < 90)
			this.theta += theta;

		cameraEngine.rotateTo(this.phi, this.theta);
	}
	
	public void move(Direction direction) {
		moveBy(defaultStepLength, direction);
	}
	
	public void moveBy(double stepLength, Direction direction) {
		executeMovement(stepLength, direction);

		sendCurrentPosToCamera();
	}

		
	public void jump() {
		if(isStandingOnSolid()) {
			yVelocity = jumpVelocity;
		}
	}

	public Boolean isStandingOnSolid() {
		return world.isBlockSolid(xPos, yPos-1, zPos) && isStandingOnBlock();
	}
	
	public double getXPos() {return xPos;}
	public double getYPos() {return yPos;}
	public double getZPos() {return zPos;}
	public double getYVelocity() {return yVelocity;}
	public void decreaseYVelocityBy(double yV) {yVelocity -= yV;}
	public void zeroYVelocity() {yVelocity = 0;}
	
	private void sendCurrentPosToCamera() {
		cameraEngine.translateTo(xPos, yPos+playerHeight, zPos);
	}
	
	
	private Boolean isStandingOnBlock() {
		return yPos - Math.floor(yPos) < maxHeightOverGround;
	}
	
	private void executeMovement(double stepLength, Direction direction) {
		switch(direction) {
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
		xPos += stepLength;
		if(currentPositionInvalid()) {xPos -= stepLength;}
	}
	private void moveAlongYAxis(double stepLength) {
		yPos += stepLength;
		if(currentPositionInvalid()) {yPos -= stepLength;}
	}
	private void moveAlongZAxis(double stepLength) {
		zPos += stepLength;
		if(currentPositionInvalid()) {zPos -= stepLength;}
	}

	private Boolean currentPositionInvalid() {
		if(currentPositionOutOfWorld()) return true;
		if(world.isBlockSolid(xPos, yPos, zPos)) return true;
		return false;
	}
	
	private Boolean currentPositionOutOfWorld() {
		if(xPos < 0) {return true;}
		if(yPos < 0) {return true;}
		if(zPos < 0) {return true;}
		
		if(xPos >= world.getXSize()) {return true;}
		if(yPos >= world.getYSize()) {return true;}
		if(zPos >= world.getZSize()) {return true;}
		
		return false;
	}
}