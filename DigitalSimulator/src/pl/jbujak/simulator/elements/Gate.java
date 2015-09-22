package pl.jbujak.simulator.elements;

public abstract class Gate extends Element {
	
	public Gate()
	{
		input1 = new Connector(this);
		input2 = new Connector(this);
		output = new Connector(this);
	}
	
	public Connector input1, input2;
	public Connector output;

}
