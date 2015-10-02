package pl.jbujak.simulator.environment;

import static org.junit.Assert.*;

import org.junit.Test;

import pl.jbujak.simulator.blocks.BlockType;
import pl.jbujak.simulator.player.Hotbar;

public class InventoryTest {

	@Test
	public void testNextItem() {
		Hotbar hotbar = new Hotbar();
		hotbar.nextItem();
		assertEquals(BlockType.BEDROCK, hotbar.getCurrentItem().getBlockType());
	}

	@Test
	public void testPrevItem() {
		Hotbar hotbar = new Hotbar();
		hotbar.nextItem();
		hotbar.prevItem();
		assertEquals(BlockType.GRASS, hotbar.getCurrentItem().getBlockType());
	}

}
