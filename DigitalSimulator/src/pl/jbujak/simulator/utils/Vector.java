package pl.jbujak.simulator.utils;

public class Vector {
	public double x;
	public double y;
	public double z;
	public double W;
	
	public Vector(double x, double y, double z, double W) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.W = W;
	}
	
	public Vector(double[] array) {
		x = array[0];
		y = array[1];
		z = array[2];
		W = array[3];
	}
	
	public double[] getArray() {
		double[] array = new double[4];
		array[0] = x;
		array[1] = y;
		array[2] = z;
		array[3] = W;
		return array;
	}
	
	public double[] getCartesianArray() {
		double[] array = new double[3];
		array[0] = x/W;
		array[1] = y/W;
		array[2] = z/W;
		return array;
	}
	
	public boolean equals(Object object) {
		if(object == null) return false;
		if(object.getClass() != this.getClass()) return false;
		Vector vector = (Vector)object;
		
		return vector.x == x && vector.y == y &&
				vector.z == z && vector.W == W;
	}
	
	public String toString() {
		return x + " " + y + " " + z + " " + W;
	}
}
