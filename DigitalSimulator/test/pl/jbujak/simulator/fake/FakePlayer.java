package pl.jbujak.simulator.fake;

import pl.jbujak.simulator.player.IPlayer;
import pl.jbujak.simulator.player.Inventory;
import pl.jbujak.simulator.utils.Position;
import pl.jbujak.simulator.world.Direction;

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
	public Position getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void processGravity() {
		// TODO Auto-generated method stub
	}

	@Override
	public void putBlock() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroyBlock() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startRunning() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopRunning() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Inventory getInventory() {
		// TODO Auto-generated method stub
		return null;
	}

}
