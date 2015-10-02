package pl.jbujak.simulator.player;

import pl.jbujak.simulator.utils.Position;
import pl.jbujak.simulator.world.Direction;

public interface IPlayer {

	public abstract void toggleIsFlying();

	public abstract boolean isFlying();

	public abstract void rotateBy(double phi, double theta);

	public abstract void move(Direction direction);

	public abstract void moveBy(double stepLength, Direction direction);

	public abstract void jump();

	public abstract boolean isStandingOnSolid();

	public abstract Position getPosition();
	
	public abstract void putBlock();

	public abstract void destroyBlock();

	public abstract void processGravity();
	
	public abstract void startRunning();

	public abstract void stopRunning();

	public abstract Hotbar getInventory();
}