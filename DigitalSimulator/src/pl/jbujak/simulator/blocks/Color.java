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
	
	public float[] getRGBArray() {
		float r;
		float g;
		float b;
		switch(this) {
		case WHITE:
			r = 230/255F;
			g = 240/255F;
			b = 240/255F;
			break;
		case ORANGE:
			r = 217/255F;
			g = 117/255F;
			b = 51/255F;
			break;
		case MAGENTA:
			r = 172/255F;
			g = 66/255F;
			b = 182/255F;
			break;
		case LIGHT_BLUE:
			r = 88/255F;
			g = 123/255F;
			b = 194/255F;
			break;
		case YELLOW:
			r = 194/255F;
			g = 182/255F;
			b = 46/255F;
			break;
		case LIGHT_GREEN:
			r = 65/255F;
			g = 176/255F;
			b = 56/255F;
			break;
		case PINK:
			r = 209/255F;
			g = 133/255F;
			b = 153/255F;
			break;
		case GRAY:
			r = 70/255F;
			g = 70/255F;
			b = 70/255F;
			break;
		case LIGHT_GRAY:
			r = 156/255F;
			g = 162/255F;
			b = 162/255F;
			break;
		case CYAN:
			r = 50/255F;
			g = 119/255F;
			b = 148/255F;
			break;
		case PURPLE:
			r = 127/255F;
			g = 60/255F;
			b = 184/255F;
			break;
		case BLUE:
			r = 46/255F;
			g = 55/255F;
			b = 142/255F;
			break;
		case BROWN:
			r = 79/255F;
			g = 50/255F;
			b = 31/255F;
			break;
		case DARK_GREEN:
			r = 53/255F;
			g = 71/255F;
			b = 27/255F;
			break;
		case RED:
			r = 168/255F;
			g = 59/255F;
			b = 54/255F;
			break;
		case BLACK:
			r = 28/255F;
			g = 22/255F;
			b = 22/255F;
			break;
		default:
			r = 255/255F;
			g = 255/255F;
			b = 255/255F;
		}
		return new float[] {
				r,g,b,1, r,g,b,1, r,g,b,1, r,g,b,1
		};
	}
}
