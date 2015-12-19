package pl.jbujak.simulator.environment;

import static org.junit.Assert.*;

import org.junit.Test;

import pl.jbujak.simulator.blocks.BlockType;
import pl.jbujak.simulator.player.Hotbar;

public class InventoryTest {

	@Test
	public void testNextItem() {
		Hotbar hotbar = new Hotbar();
		hotbar.setItem(1, BlockType.BEDROCK);
		hotbar.nextItem();
		assertEquals(BlockType.BEDROCK, hotbar.getCurrentItem());
	}

	@Test
	public void testPrevItem() {
		Hotbar hotbar = new Hotbar();
		hotbar.setItem(0, BlockType.GRASS);
		hotbar.nextItem();
		hotbar.prevItem();
		assertEquals(BlockType.GRASS, hotbar.getCurrentItem());
	}

}
