package pl.jbujak.simulator.gui;

import static org.junit.Assert.*;

import org.junit.Test;

public class RenderBlockTest {

	@Test
	public void testEqualsObject() {
		RenderBlock renderBlock = new RenderBlock(1,2,3);

		RenderBlock equalRenderBlock = new RenderBlock(1,2,3);
		assertEquals(equalRenderBlock, renderBlock);
		
		RenderBlock notEqualRenderBlock = new RenderBlock(0,2,3);
		assertNotEquals(renderBlock, notEqualRenderBlock);
		notEqualRenderBlock = new RenderBlock(1,0,3);
		assertNotEquals(renderBlock, notEqualRenderBlock);
		notEqualRenderBlock = new RenderBlock(1,2,0);
		assertNotEquals(renderBlock, notEqualRenderBlock);
		
		assertNotEquals(renderBlock, null);
		assertNotEquals(renderBlock, new Integer(1));

	}

}
