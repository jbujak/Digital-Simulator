package pl.jbujak.simulator.fake;

import pl.jbujak.simulator.gui.ICameraEngine;
import pl.jbujak.simulator.utils.Position;

public class FakeCameraEngine implements ICameraEngine{
	
	private Position position;
	private double phi;
	private double theta;
	

	@Override
	public void translateTo(Position position) {
		this.position = position;
		
	}

	@Override
	public void rotateTo(double phi, double theta) {
		this.phi = phi;
		this.theta = theta;
		
	}
	
	public double getPhi() {return phi;}

	public double getTheta() {return theta;}
	
	public Position getPosition() {return position;}
}
