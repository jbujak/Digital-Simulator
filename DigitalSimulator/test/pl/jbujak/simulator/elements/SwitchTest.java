package pl.jbujak.simulator.elements;

import static org.junit.Assert.*;

import org.junit.Test;

public class SwitchTest {


	@Test
	public void testToggle() {
		Switch s = new Switch();

		assertFalse(s.output.getState());
		s.toggle();
		assertTrue(s.output.getState());
		s.toggle();
		assertFalse(s.output.getState());

	}

}
