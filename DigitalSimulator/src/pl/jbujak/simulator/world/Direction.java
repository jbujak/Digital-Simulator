package pl.jbujak.simulator.world;

public enum Direction {
	FRONT(0), BACK(1), LEFT(2), RIGHT(3), UP(4), DOWN(5);
	
	public final int value;
	
	private Direction(int value) {
		this.value = value;
	}
	
}
