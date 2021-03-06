package pl.jbujak.simulator.player;

import pl.jbujak.simulator.world.Direction;

public class GravityEngine {
	
	private final double gravityRate = 0.01;
	private final double minimalYVelocity = -0.2;
	private final double startYVelocity = -3;
	
	private boolean isFlying = false;;
	private double yVelocity = startYVelocity;

	private Player player;
	private MovementEngine movementEngine;
	
	public GravityEngine(Player player, MovementEngine movementEngine) {
		this.player = player;
		this.movementEngine = movementEngine;
	}
	
	public void process() {
		if(isFlying) {
			if(player.isStandingOnSolid()) {
				yVelocity = 0;
				toggleIsFlying();
				player.jump();
			}
			movementEngine.moveBy(0, Direction.UP);
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
	
	public void restart() {
		yVelocity = startYVelocity;
	}
}
