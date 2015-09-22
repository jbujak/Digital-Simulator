package pl.jbujak.simulator.utils;

import static org.junit.Assert.*;

import java.nio.DoubleBuffer;

import org.junit.Before;
import org.junit.Test;
import org.lwjgl.BufferUtils;

public class ModelViewMatrixTest {
	
	public ModelViewMatrix matrix;

	@Before
	public void setUp() throws Exception {
		matrix = new ModelViewMatrix();
	}

	@Test
	public void testSetDoubleBuffer() {
		DoubleBuffer buffer = BufferUtils.createDoubleBuffer(16*4);
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				buffer.put(4*i + j);
			}
		}
		
		buffer.rewind();
		matrix.set(buffer);
		
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				assertEquals(4*i + j, matrix.get(i, j), 0.0001);
			}
		}
	}

	@Test
	public void testSetIntIntDouble() {
		matrix.set(0, 0, 42);
		assertEquals(42, matrix.get(0,0), 0.0001);
	}
	

	@Test
	public void testMultiplyByVector() {
		Vector vector = new Vector(0,1,2,3);
		
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				matrix.set(i, j, 4*i + j);
			}
		}
		
		Vector result = matrix.multiplyByVector(vector);
		assertEquals(new Vector(14,38,62,86), result);
	}
	
	@Test
	public void testToString() {
		
		String string = "";

		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				matrix.set(i, j, 4*i + j);
				string += matrix.get(i, j);
				string += " ";
			}
			string += "\n";
		}

		assertEquals(string, matrix.toString());
	}
}
