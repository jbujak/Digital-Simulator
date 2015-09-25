package pl.jbujak.simulator.fake;

import pl.jbujak.simulator.environment.Direction;
import pl.jbujak.simulator.environment.IPlayer;
import pl.jbujak.simulator.utils.Position;

public class FakePlayer implements IPlayer {
	
	public boolean isFlying;
	public boolean isStandingOnSolid;
	
	public double yVelocity;

	@Override
	public void toggleIsFlying() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isFlying() {
		return isFlying;
		
	}

	@Override
	public void rotateBy(double phi, double theta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move(Direction direction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveBy(double stepLength, Direction direction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void jump() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isStandingOnSolid() {
		return isStandingOnSolid;
	}

	@Override
	public double getYVelocity() {
		return yVelocity;
	}

	@Override
	public void decreaseYVelocityBy(double yV) {
		yVelocity -= yV;
		
	}

	@Override
	public void zeroYVelocity() {
		yVelocity = 0;
	}

	@Override
	public Position getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

}
