package pl.jbujak.simulator.gui;

import pl.jbujak.simulator.utils.Position;

public interface ICameraEngine {
	
	public void translateTo(Position position);
	public void rotateTo(double phi, double theta);

}
