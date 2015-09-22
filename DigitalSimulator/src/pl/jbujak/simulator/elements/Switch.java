package pl.jbujak.simulator.elements;

public class Switch extends Element {
	
	public Switch()
	{
		output = new Connector(this);
	}

	@Override
	public void refresh()
	{
		output.setStateFromInside(state);
	}
	
	public void toggle()
	{
		state = !state;
		refresh();
	}
	
	public Connector output;
	
	private boolean state;
}
