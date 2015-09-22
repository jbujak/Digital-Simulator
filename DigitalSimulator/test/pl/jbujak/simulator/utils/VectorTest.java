package pl.jbujak.simulator.utils;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class VectorTest {

	Vector vector;

	@Before
	public void setUp() throws Exception {
		vector = new Vector(0,1,2,3);
	}

	@Test
	public void testGetArray() {
		double[] array = vector.getArray();
		assertEquals(4, array.length);
		for(int i=0; i<4; i++) {
			assertEquals(i, array[i], 0.0001);
		}
	}
	
	@Test
	public void testGetCartesianArray() {
		double[] array = vector.getCartesianArray();
		assertEquals(3, array.length);
		for(int i=0; i<3; i++) {
			assertEquals((double)i/3, array[(int)i], 0.0001);
		}
	}

	@Test
	public void testEqualsObject() {
		assertFalse(vector.equals(null));
		assertFalse(vector.equals(new Integer(1)));

		assertTrue(vector.equals(vector));
		assertTrue(vector.equals(new Vector(0,1,2,3)));

		assertFalse(vector.equals(new Vector(1,1,2,3)));
		assertFalse(vector.equals(new Vector(0,2,2,3)));
		assertFalse(vector.equals(new Vector(0,1,3,3)));
		assertFalse(vector.equals(new Vector(0,1,2,4)));
	}

	@Test
	public void testToString() {
		assertEquals("0.0 1.0 2.0 3.0", vector.toString());
	}

}
