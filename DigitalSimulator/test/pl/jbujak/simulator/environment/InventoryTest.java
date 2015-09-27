package pl.jbujak.simulator.environment;

import static org.junit.Assert.*;

import org.junit.Test;

import pl.jbujak.simulator.blocks.BlockType;

public class InventoryTest {

	@Test
	public void testNextItem() {
		Inventory inventory = new Inventory();
		inventory.nextItem();
		assertEquals(BlockType.BEDROCK, inventory.getCurrentItem().getBlockType());
	}

	@Test
	public void testPrevItem() {
		Inventory inventory = new Inventory();
		inventory.nextItem();
		inventory.prevItem();
		assertEquals(BlockType.GRASS, inventory.getCurrentItem().getBlockType());
	}

}
