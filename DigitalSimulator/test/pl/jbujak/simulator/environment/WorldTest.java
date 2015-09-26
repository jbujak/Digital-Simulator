package pl.jbujak.simulator.environment;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import pl.jbujak.simulator.blocks.BedrockBlock;
import pl.jbujak.simulator.blocks.Block;
import pl.jbujak.simulator.blocks.BlockType;
import pl.jbujak.simulator.blocks.GrassBlock;
import pl.jbujak.simulator.fake.NullWorldGenerator;
import pl.jbujak.simulator.gui.*;
import pl.jbujak.simulator.utils.Position;

public class WorldTest {
	Window window;
	IWorld world;
	
	@Before
	public void setUp() {
		window = new Window(1, 1);
		world = new World(2, 2, 2, new NullWorldGenerator());
	}

	@Test
	public void testChangeBlock() {
		world.changeBlock(new Position(0, 0, 0), new BedrockBlock());
		Block[][][] blocks = world.getBlocks();
		assertEquals(blocks[0][0][0].getBlockType(), BlockType.BEDROCK);
		
		world.changeBlock(new Position(0, 0, 0), new GrassBlock());
		blocks = world.getBlocks();
		assertEquals(blocks[0][0][0].getBlockType(), BlockType.GRASS);
	}

	@Test
	public void testIsBlockSolid() {
		assertFalse(world.isBlockSolid(new Position(0,0,0)));
		world.changeBlock(new Position(0, 0, 0), new BedrockBlock());
		assertTrue(world.isBlockSolid(new Position(0,0,0)));
		
		assertTrue(world.isBlockSolid(new Position(-0.001,0,0)));
		assertTrue(world.isBlockSolid(new Position(0,-0.001,0)));
		assertTrue(world.isBlockSolid(new Position(0,0,-0.001)));
		
		
		assertTrue(world.isBlockSolid(new Position(2, 0, 0)));
		assertTrue(world.isBlockSolid(new Position(0, 2, 0)));
		assertTrue(world.isBlockSolid(new Position(0, 0, 2)));
	}

	@Test
	public void testIsPositionOutOfWorld() {
		assertFalse(world.isPositionOutOfWorld(new Position(0,0,0)));
		assertFalse(world.isPositionOutOfWorld(new Position(1.999,1.999,1.999)));
		
		assertTrue(world.isPositionOutOfWorld(new Position(-0.001,0,0)));
		assertTrue(world.isPositionOutOfWorld(new Position(0,-0.001,0)));
		assertTrue(world.isPositionOutOfWorld(new Position(0,0,-0.001)));
		
		assertTrue(world.isPositionOutOfWorld(new Position(2, 0, 0)));
		assertTrue(world.isPositionOutOfWorld(new Position(0, 2, 0)));
		assertTrue(world.isPositionOutOfWorld(new Position(0, 0, 2)));
	}
	
	@Test
	public void testGetPlayer() {
		IPlayer player = world.getPlayer();
		assertNotNull(player);
		assertEquals(Player.class, player.getClass());
	}
	
	@Test
	public void testGetBlocksToRender() {
		ArrayList<HashSet<RenderBlock>> blocksToRender = world.getBlocksToRender();
		assertEquals(BlockType.values().length, blocksToRender.size());
		for(int i=0; i<blocksToRender.size(); i++) {
			assertEquals(0, blocksToRender.get(i).size());
		}
		
		world.changeBlock(new Position(0, 0, 0), new BedrockBlock());
		blocksToRender = world.getBlocksToRender();
		assertEquals(1, blocksToRender.get(BlockType.BEDROCK.value).size());
		for(RenderBlock renderBlock: blocksToRender.get(BlockType.BEDROCK.value)) {
			assertEquals(new RenderBlock(0, 0, 0), renderBlock);
		}
	}
	
	@Test
	public void testGetNumbersOfBlockTypes() {
		assertEquals(BlockType.values().length, world.getNumberOfBlockTypes());
	}
	
	@Test
	public void testGetSelectedBlock() {
		world.setSelectedBlock(new Position(1,2,3));
		assertEquals(new Position(1, 2, 3), world.getSelectedBlock());;
	}

}
