package pl.jbujak.simulator.elements;

public class AndGate extends Gate {
	
	@Override
	public void refresh() {
		if(input1.getState() && input2.getState())
			output.setStateFromInside(true);
		else
			output.setStateFromInside(false);
	}
}
