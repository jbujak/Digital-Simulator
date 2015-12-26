package pl.jbujak.simulator.world;

public enum Direction {
	FRONT(0), BACK(1), LEFT(2), RIGHT(3), UP(4), DOWN(5);
	
	public final int value;
	
	private Direction(int value) {
		this.value = value;
	}
	
	public Direction opposite() {
		return this.directionOn(BACK);
	}
	
	public Direction directionOn(Direction direction) {
		if(direction == FRONT) {
			return this;
		}
		if(direction == BACK) {
			switch (this) {
			case FRONT:
				return BACK;
			case BACK:
				return FRONT;
			case LEFT:
				return RIGHT;
			case RIGHT:
				return LEFT;
			case UP:
				return DOWN;
			case DOWN:
				return UP;
			default:
				return null;
			}
		}
		if(direction == LEFT) {
			switch (this) {
			case FRONT:
				return LEFT;
			case BACK:
				return RIGHT;
			case LEFT:
				return BACK;
			case RIGHT:
				return FRONT;
			case UP:
				return UP;
			case DOWN:
				return DOWN;
			default:
				return null;
			}
		}
		if(direction == RIGHT) {
			switch (this) {
			case FRONT:
				return RIGHT;
			case BACK:
				return LEFT;
			case LEFT:
				return FRONT;
			case RIGHT:
				return BACK;
			case UP:
				return UP;
			case DOWN:
				return DOWN;
			default:
				return null;
			}
		}
		return this;
	}
}
