package pl.jbujak.simulator.environment;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import pl.jbujak.simulator.blocks.BedrockBlock;
import pl.jbujak.simulator.environment.LineOfSight;
import pl.jbujak.simulator.environment.NoBlockException;
import pl.jbujak.simulator.environment.World;
import pl.jbujak.simulator.fake.NullWorldGenerator;
import pl.jbujak.simulator.gui.Window;
import pl.jbujak.simulator.utils.Position;

public class LineOfSightTest {
	NullWorldGenerator generator;
	World world;
	LineOfSight lineOfSight;
	
	@Before
	public void setUp() {
		@SuppressWarnings("unused") //Window needed for OpenGL Context
		Window window = new Window(1, 1);
		generator = new NullWorldGenerator();
		world = new World(25,5,25, generator);
		lineOfSight = new LineOfSight(world);
	}

	@Test
	public void testGetAimedBlockOutOfWorld() {
		lineOfSight.translateTo(new Position(0,0,0));
		lineOfSight.rotateTo(0.1, 0.1);
		assertEquals(null, lineOfSight.getSelectedBlock());
	}

	@Test
	public void testGetAimedBlockNoBlockInSight() {
		lineOfSight.translateTo(new Position(0,0,0));
		lineOfSight.rotateTo(180, 0.1);
		assertEquals(null, lineOfSight.getSelectedBlock());
	}
	
	@Test
	public void testGetAimedBlockVerticalDown() throws NoBlockException {
		world.changeBlock(new Position(0, 0, 0), new BedrockBlock());
		lineOfSight.translateTo(new Position(0,0,0));
		lineOfSight.rotateTo(0, 90);
		assertEquals(new Position(0,0,0), lineOfSight.getSelectedBlock());
	}

	@Test
	public void testGetAimedBlockVerticalUp() throws NoBlockException {
		world.changeBlock(new Position(0, 2, 0), new BedrockBlock());
		lineOfSight.translateTo(new Position(0,0,0));
		lineOfSight.rotateTo(0, -90);
		assertEquals(new Position(0,2,0), lineOfSight.getSelectedBlock());
	}
	
	
	@Test
	public void testGetAimedBlockHorizontal() throws NoBlockException {
		world.changeBlock(new Position(10, 2, 0), new BedrockBlock());
		lineOfSight.translateTo(new Position(18, 2.7, 0.4));
		lineOfSight.rotateTo(270, 0);
		assertEquals(new Position(10,2,0), lineOfSight.getSelectedBlock());
	}
	
	@Test
	public void testGetAimedBlockSkew() throws NoBlockException {
		world.changeBlock(new Position(0, 0, 0), new BedrockBlock());
		lineOfSight.translateTo(new Position(0.4, 2.7, 5));
		lineOfSight.rotateTo(0, 20);
		assertEquals(new Position(0,0,0), lineOfSight.getSelectedBlock());
	}
	

}