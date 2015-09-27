package pl.jbujak.simulator.environment;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import pl.jbujak.simulator.blocks.BedrockBlock;
import pl.jbujak.simulator.fake.FakeCameraEngine;
import pl.jbujak.simulator.fake.FakeWorld;
import pl.jbujak.simulator.gui.Window;
import pl.jbujak.simulator.utils.Position;

public class PlayerTest {
	FakeCameraEngine cameraEngine;
	FakeWorld world;
	IPlayer player;

	@Before
	public void setUp() throws Exception {
		cameraEngine = new FakeCameraEngine();
		world = new FakeWorld();
		world.isPositionOutOfWorld = true;
		
		player = new Player(new Position(0, 0, 2), world, cameraEngine);
}

	@Test
	public void testToggleIsFlying() {
		assertFalse(player.isFlying());
		player.toggleIsFlying();
		assertTrue(player.isFlying());
		player.toggleIsFlying();
		assertFalse(player.isFlying());

	}

	@Test
	public void testRotateBy() {
		double phi = cameraEngine.getPhi();
		double theta = cameraEngine.getTheta();
		
		player.rotateBy(1, 2);
		phi += 1;
		theta += 2;
		assertEquals(phi, cameraEngine.getPhi(), 0.0001);
		assertEquals(theta, cameraEngine.getTheta(), 0.0001);
		
		player.rotateBy(360, 360);
		assertEquals(phi, cameraEngine.getPhi(), 0.0001);
		assertEquals(theta, cameraEngine.getTheta(), 0.0001);
		
		player.rotateBy(-720, -720);
		assertEquals(phi, cameraEngine.getPhi(), 0.0001);
		assertEquals(theta, cameraEngine.getTheta(), 0.0001);
	}

	@Test
	public void testMove() {
		world.isBlockSolid = false;
		world.isPositionOutOfWorld = false;
		for(Direction direction: Direction.values()) {
			IPlayer movedPlayer = new Player(new Position(12,0,12), world, cameraEngine);
			movedPlayer.move(direction);
			assertNotEquals(new Position(12,0,12), movedPlayer.getPosition());
		}
		
		world.isBlockSolid = true;
		world.isPositionOutOfWorld = false;
		for(Direction direction: Direction.values()) {
			IPlayer movedPlayer = new Player(new Position(12,0,12), world, cameraEngine);
			movedPlayer.move(direction);
			assertEquals(new Position(12,0,12), movedPlayer.getPosition());
		}
		
		world.isBlockSolid = false;
		world.isPositionOutOfWorld = true;
		for(Direction direction: Direction.values()) {
			IPlayer movedPlayer = new Player(new Position(12,0,12), world, cameraEngine);
			movedPlayer.move(direction);
			assertEquals(new Position(12,0,12), movedPlayer.getPosition());
		}
	}

	@Test
	public void testMoveBy() {
		@SuppressWarnings("unused")
		Window winndow = new Window(1, 1);
		World world = new World(25, 5, 25, new WorldGenerator());
		Player player = new Player(new Position(0,0,0), world, cameraEngine);
		player.moveBy(1, Direction.UP);
		assertEquals(new Position(0,1,0), player.getPosition());
	}

	@Test
	public void testJumpOnSolid() {
		@SuppressWarnings("unused")
		Window winndow = new Window(1, 1);
		World world = new World(25, 5, 25, new WorldGenerator());
		Player player = new Player(new Position(0,1,0), world, cameraEngine);

		Position startPosition = player.getPosition().copy();
		player.jump();
		player.processGravity();;
		Position endPosition = player.getPosition();
		assertTrue(startPosition.y < endPosition.y);
	}
	

	@Test
	public void testIsStandingOnSolid() {
		@SuppressWarnings("unused")
		Window winndow = new Window(1, 1);
		World world = new World(25, 5, 25, new WorldGenerator());
		Player player = new Player(new Position(0,1,0), world, cameraEngine);

		assertTrue(player.isStandingOnSolid());
		
		world.changeBlock(new Position(0, 0, 0), null);
		assertFalse(player.isStandingOnSolid());
		
		world.changeBlock(new Position(0, 0, 0), new BedrockBlock());
		player.moveBy(0.9, Direction.UP);
		assertFalse(player.isStandingOnSolid());
	}
}
