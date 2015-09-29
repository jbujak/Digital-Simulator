package pl.jbujak.simulator.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class PositionTest {

	@Test
	public void testDistanceBetween() {
		Position a = new Position(1,1,1);
		Position b = new Position(2,2,2);
		
		assertEquals(Math.sqrt(3), Position.distanceBetween(a, b), 0.0001);
	}
	
	@Test
	public void testDefaultConstructor() {
		Position position = new Position();
		assertEquals(0, position.x, 0.0001);
		assertEquals(0, position.y, 0.0001);
		assertEquals(0, position.z, 0.0001);
	}
	
	@Test
	public void testToString() {
		Position position = new Position(1,2,3);
		assertEquals("(1.0, 2.0, 3.0)", position.toString());
	}
	
	@Test
	public void testEquals() {
		Position position = new Position(1,2,3);

		Position equalPosition = new Position(1,2,3);
		assertEquals(equalPosition, position);
		
		Position notEqualPosition = new Position(0,2,3);
		assertNotEquals(position, notEqualPosition);
		notEqualPosition = new Position(1,0,3);
		assertNotEquals(position, notEqualPosition);
		notEqualPosition = new Position(1,2,0);
		assertNotEquals(position, notEqualPosition);
		
		assertNotEquals(position, null);
		assertNotEquals(position, new Integer(1));
	}
	
	@Test
	public void testHashCode() {
		assertEquals(new Position(0,0,0).hashCode(), new Position(0,0,0).hashCode());
		assertNotEquals(new Position(0,0,0).hashCode(), new Position(0,0,1).hashCode());
	}

}
