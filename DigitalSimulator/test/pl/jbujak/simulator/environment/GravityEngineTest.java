package pl.jbujak.simulator.environment;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import pl.jbujak.simulator.fake.FakePlayer;

public class GravityEngineTest {
	FakePlayer player;
	GravityEngine gravityEngine;

	@Before
	public void setUp() {
		player = new FakePlayer();
		gravityEngine = new GravityEngine(player);
	}

	@Test
	public void testProcessWhileFlying() {
		player.yVelocity = 0;
		player.isFlying = true;
		player.isStandingOnSolid = true;
		gravityEngine.process();
		assertEquals(0, player.yVelocity, 0.001);
	}
	
	@Test
	public void testProcessWhileOnSolid() {
		player.isFlying = false;
		player.isStandingOnSolid = true;

		player.yVelocity = 5;
		gravityEngine.process();
		assertEquals(0, player.yVelocity, 0.001);
		
		gravityEngine.process();
		assertEquals(0, player.yVelocity, 0.001);
	}
	
	@Test
	public void testProcessWhileFalling() {
		player.isFlying = false;
		player.isStandingOnSolid = false;
		double yVelocity = player.getYVelocity();
		
		gravityEngine.process();
		assertTrue(player.getYVelocity() < yVelocity);
		
		for(int i=0; i<1000; i++) {
			gravityEngine.process();
		}
		
		yVelocity = player.getYVelocity();
		gravityEngine.process();
		assertEquals(yVelocity, player.getYVelocity(), 0.001);
	}

}
