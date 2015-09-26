package pl.jbujak.simulator.environment;

import pl.jbujak.simulator.blocks.GrassBlock;
import pl.jbujak.simulator.gui.ICameraEngine;
import pl.jbujak.simulator.utils.Position;

public class Player implements IPlayer {
	private final double jumpVelocity = 0.15;

	private Boolean isFlying = false;
	private MovementEngine movementEngine;
	private GravityEngine gravityEngine;
	private IWorld world;

	public Player(Position position, IWorld world, ICameraEngine cameraEngine) {
		this.world = world;
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

	public Position getPosition() {
		return movementEngine.getPosition();
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
	
	public void putBlock() {
		if(world.getSelectedBlock() == null) {return;}
		Position positionOfBlock = world.getSelectedBlock().copy();
		Direction selectedFace = world.getSelectedFace();
		switch(selectedFace) {
		case FRONT:
			positionOfBlock.z += 1;
			break;
		case BACK:
			positionOfBlock.z -= 1;
			break;
		case UP:
			positionOfBlock.y += 1;
			break;
		case DOWN:
			positionOfBlock.y -= 1;
			break;
		case RIGHT:
			positionOfBlock.x += 1;
			break;
		case LEFT:
			positionOfBlock.x -= 1;
		}
		
		if(isBlockPositionValid(positionOfBlock)) {
			world.changeBlock(positionOfBlock, new GrassBlock());
		}
		
	}
	
	public void destroyBlock() {
		world.changeBlock(world.getSelectedBlock(), null);
	}
	
	private boolean isBlockPositionValid(Position blockPosition) {
		Position testPosition = blockPosition.copy();
		if(testPosition.equals(getPosition().toInt()) && isStandingOnSolid()) {
			return false;
		}
		testPosition.y -= 1;
		if(testPosition.equals(getPosition().toInt())) {
			return false;
		}
		return true;

	}
}