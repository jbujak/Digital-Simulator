package pl.jbujak.simulator.utils;

import static java.lang.Math.*;

import pl.jbujak.simulator.world.Direction;

public class Position {
	
	public double x;
	public double y;
	public double z;

	public Position() {
		x = -1;
		y = -1;
		z = -1;
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
	
	public Position copy() {
		return new Position(x,y,z);
	}
	
	public Position toInt() {
		return new Position((int)floor(x), (int)floor(y), (int)floor(z));
	}	
	
	@Override
	public int hashCode() {
		int x = new Double(this.x).hashCode();
		int y = new Double(this.y).hashCode();
		int z = new Double(this.z).hashCode();
		return (1<<16)*x + (1<<8)*y + z;
	}

	@Override
	public boolean equals(Object object) {
		if(object == null) return false;
		if(!(object instanceof Position)) return false;

		if(((Position)object).x != this.x) return false;
		if(((Position)object).y != this.y) return false;
		if(((Position)object).z != this.z) return false;

		return true;
		
	}
	
	public Position next(Direction direction) {
		switch(direction) {
		case FRONT:
			return new Position(x, y, z-1);
		case BACK:
			return new Position(x, y, z+1);
		case LEFT:
			return new Position(x-1, y, z);
		case RIGHT:
			return new Position(x+1, y, z);
		case DOWN:
			return new Position(x, y-1, z);
		case UP:
			return new Position(x, y+1, z);
		default:
			return null;
			
		}
	}
}
