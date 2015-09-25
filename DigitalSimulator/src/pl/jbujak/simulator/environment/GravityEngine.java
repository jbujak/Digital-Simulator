package pl.jbujak.simulator.environment;

public class GravityEngine {
	
	private final double gravityRate = 0.01;
	private final double minimalYVelocity = -0.2;
	private IPlayer player;
	
	public GravityEngine(IPlayer player) {
		this.player = player;
	}
	
	public void process() {
		if(player.isFlying()) {
			return;
		}

		player.moveBy(player.getYVelocity(), Direction.UP);

		if(player.isStandingOnSolid()) {
			player.zeroYVelocity();
		}
		else
		{
			if(player.getYVelocity() > minimalYVelocity) {
				player.decreaseYVelocityBy(gravityRate);
			}
		}
	}
}
