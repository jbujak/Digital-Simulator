package pl.jbujak.simulator.elements;

public class Connector {
	
	public Connector(Element parent) {
		this.parent = parent;
		destination = null;
		state = false;
	}
	
	public void connectTo(Connector destination) {
		this.destination = destination;
		
		if(destination != null) {
			destination.setStateFromOutside(state);
		}
	}
	
	public Connector getDestination() {
		return destination;
	}
	
	public void setStateFromInside(boolean newState) {
		boolean previousState = state;
		state = newState;
		if(destination != null && state != previousState) {
			destination.setStateFromOutside(newState);
		}
	}
	
	public void setStateFromOutside(boolean newState)
	{
		state = newState;
		if(parent != null) {
			parent.refresh();
		}
	}

	public boolean getState() {
		return state;
	}
	
	private boolean state;
	private Element parent;
	private Connector destination;
}
