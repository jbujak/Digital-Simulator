package pl.jbujak.simulator.elements;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConnectorTest {
	
	@Test
	public void testConnectTo() {
		Connector source = new Connector(null);
		Connector destination = new Connector(null);
		
		source.connectTo(destination);
		assertEquals(destination, source.getDestination());
	}
	
	@Test
	public void testConenctToWhileOn() {
		Connector source = new Connector(null);
		Connector destination = new Connector(null);
		
		source.setStateFromOutside(true);
		source.connectTo(destination);
		
		assertTrue(destination.getState());
	}
	
	@Test
	public void testConnectToNull() {
		Connector source = new Connector(null);
		source.connectTo(null);
	}

	@Test
	public void testSetStateFromInside() {
		Connector source = new Connector(null);
		Connector destination = new Connector(null);
		source.connectTo(destination);
		
		assertFalse(destination.getState());
		source.setStateFromInside(true);
		assertTrue(destination.getState());
	}

	@Test
	public void testSetStateFromInsideNullDestination() {
		Connector nullDestinationConnector = new Connector(null);
		nullDestinationConnector.setStateFromInside(true);
	}
	
	@Test
	public void testSetStateFromOutsideNullParent() {
		Connector nullParentConnector = new Connector(null);
		nullParentConnector.setStateFromOutside(true);
	}

}
