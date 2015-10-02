package pl.jbujak.simulator.player;

import pl.jbujak.simulator.gui.DrawEngine;
import pl.jbujak.simulator.gui.ICameraEngine;
import pl.jbujak.simulator.utils.Position;
import pl.jbujak.simulator.world.Direction;
import pl.jbujak.simulator.world.IWorld;

public class Player implements IPlayer {
	private final double jumpVelocity = 0.15;

	private MovementEngine movementEngine;
	private GravityEngine gravityEngine;
	private IWorld world;
	private Hotbar hotbar;
	private Inventory inventory;

	public Player(Position position, IWorld world, ICameraEngine cameraEngine) {
		this.world = world;
		this.movementEngine = new MovementEngine(cameraEngine, position, world);
		this.gravityEngine = new GravityEngine(this, movementEngine);

		this.hotbar = new Hotbar();
		this.inventory = new Inventory();

		DrawEngine.addShape(hotbar);
		DrawEngine.addShape(inventory);
		DrawEngine.addShape(new Aim());
		

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
		gravityEngine.toggleIsFlying();
	}

	public boolean isFlying() {
		return gravityEngine.isFlying();
	}
	
	public void processGravity() {
		gravityEngine.process();
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
			world.changeBlock(positionOfBlock, hotbar.getCurrentItem());
		}
		
	}
	
	public void destroyBlock() {
		world.changeBlock(world.getSelectedBlock(), null);
	}
	
	public void startRunning() {
		movementEngine.startRunning();
	}
	
	public void stopRunning() {
		movementEngine.stopRunning();
	}
	
	public Hotbar getInventory() {
		return hotbar;
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