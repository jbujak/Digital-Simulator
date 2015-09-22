package pl.jbujak.simulator.gui;

public class RenderBlock {
	
	public final int x;
	public final int y;
	public final int z;
	
	public RenderBlock(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public boolean equals(Object object) {
		if(object == null) return false;
		if(!(object instanceof RenderBlock)) return false;

		if(((RenderBlock)object).x != this.x) return false;
		if(((RenderBlock)object).y != this.y) return false;
		if(((RenderBlock)object).z != this.z) return false;

		return true;
		
	}

}
