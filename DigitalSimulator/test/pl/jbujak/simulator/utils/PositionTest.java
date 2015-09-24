package pl.jbujak.simulator.utils;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PositionTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testDistanceBetween() {
		Position a = new Position(1,1,1);
		Position b = new Position(2,2,2);
		
		assertEquals(Math.sqrt(3), Position.distanceBetween(a, b), 0.0001);
	}

}
