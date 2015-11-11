package pl.jbujak.simulator.blocks;

public enum Color {
	WHITE(0), ORANGE(1), MAGENTA(2), LIGHT_BLUE(3),
	YELLOW(4), LIGHT_GREEN(5), PINK(6), GRAY(7),
	LIGHT_GRAY(8), CYAN(9), PURPLE(10), BLUE(11),
	BROWN(12), DARK_GREEN(13), RED(14), BLACK(15);
	
	public final int offset;
	
	private Color(int offset) {
		this.offset = offset;
	}
}
