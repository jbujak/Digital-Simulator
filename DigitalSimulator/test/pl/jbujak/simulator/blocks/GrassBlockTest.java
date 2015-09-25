package pl.jbujak.simulator.blocks;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import pl.jbujak.simulator.environment.Direction;

public class GrassBlockTest {
	GrassBlock block;

	@Before
	public void setUp() throws Exception {
		block = new GrassBlock();
	}

	@Test
	public void testGetTextureName() {
		for (Direction face : Direction.values()) {
			if (face == Direction.UP) {
				assertEquals("grass_top.png", GrassBlock.getTextureName(face));
			}
			else if(face == Direction.DOWN) {
				assertEquals("grass_bottom.png", GrassBlock.getTextureName(face));
			}
			else {
				assertEquals("grass_side.png", GrassBlock.getTextureName(face));
			}
		}
	}

	@Test
	public void testGetBlockType() {
		assertEquals(BlockType.GRASS, block.getBlockType());
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
