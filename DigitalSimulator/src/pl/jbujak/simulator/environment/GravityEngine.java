package pl.jbujak.simulator.environment;

public class GravityEngine {
	
	private final double gravityRate = 0.01;
	private final double minimalYVelocity = -0.2;
	
	private boolean isFlying = false;;
	private double yVelocity = 0;

	private IPlayer player;
	private MovementEngine movementEngine;
	
	public GravityEngine(IPlayer player, MovementEngine movementEngine) {
		this.player = player;
		this.movementEngine = movementEngine;
	}
	
	public void process() {
		if(isFlying) {
			if(player.isStandingOnSolid()) {
				toggleIsFlying();
				player.jump();
			}
			return;
		}

		movementEngine.moveBy(yVelocity, Direction.UP);

		if(player.isStandingOnSolid()) {
			yVelocity = 0;
		}
		else
		{
			if(yVelocity > minimalYVelocity) {
				yVelocity -= gravityRate;
			}
		}
	}
	
	public void jump(double jumpVelocity) {
		yVelocity = jumpVelocity;
	}

	public void toggleIsFlying() {
		isFlying = !isFlying;
	}
	
	public boolean isFlying() {
		return isFlying;
	}
}
