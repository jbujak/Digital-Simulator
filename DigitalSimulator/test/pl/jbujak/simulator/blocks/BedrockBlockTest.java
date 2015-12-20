package pl.jbujak.simulator.blocks;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import pl.jbujak.simulator.utils.Position;

public class BedrockBlockTest {
	
	BedrockBlock block;

	@Before
	public void setUp() throws Exception {
		block = new BedrockBlock(new Position());
	}

	@Test
	public void testGetBlockType() {
		assertEquals(BlockType.BEDROCK, block.getBlockType());
	}

	@Test
	public void testIsSolid() {
		assertTrue(block.isSolid);
	}

	@Test
	public void testIsTransparent() {
		assertFalse(block.isTransparent);
	}

}
