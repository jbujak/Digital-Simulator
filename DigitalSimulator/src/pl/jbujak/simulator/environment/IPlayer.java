package pl.jbujak.simulator.environment;

import pl.jbujak.simulator.utils.Position;

public interface IPlayer {

	public abstract void toggleIsFlying();

	public abstract boolean isFlying();

	public abstract void rotateBy(double phi, double theta);

	public abstract void move(Direction direction);

	public abstract void moveBy(double stepLength, Direction direction);

	public abstract void jump();

	public abstract boolean isStandingOnSolid();

	public abstract Position getPosition();
	
	public abstract GravityEngine getGravityEngine();

	void putBlock();

	void destroyBlock();

}