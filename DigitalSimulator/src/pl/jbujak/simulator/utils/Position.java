package pl.jbujak.simulator.utils;

public class Position {
	
	public double x;
	public double y;
	public double z;

	
	public Position() {
		x = 0;
		y = 0;
		z = 0;
	}
	
	public Position(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}

	public static double distanceBetween(Position a, Position b) {
		double dx = a.x - b.x;
		double dy = a.y - b.y;
		double dz = a.z - b.z;
		
		return Math.sqrt(dx*dx + dy*dy + dz*dz);
	}
}
