package pl.jbujak.simulator.elements;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import pl.jbujak.simulator.elements.AndGate;


public class AndGateTest {

	@Before
	public void setUp() throws Exception {
		
	}
	
	@Test
	public void testRefresh() {
		AndGate and = new AndGate();
		and.refresh();
		assertFalse(and.output.getState());
		and.input1.setStateFromInside(true);
		and.refresh();
		assertFalse(and.output.getState());
		and.input2.setStateFromInside(true);
		and.refresh();
		assertTrue(and.output.getState());
	}
	
	@Test
	public void testConnectingAnds() {
		AndGate and1 = new AndGate();
		AndGate and2 = new AndGate();
		
		and1.output.connectTo(and2.input1);
		
		and2.input2.setStateFromOutside(true);
		
		and1.input1.setStateFromOutside(true);
		and1.input2.setStateFromOutside(true);
		
		assertTrue(and1.output.getState());
		assertTrue(and2.output.getState());
	}
	
}
