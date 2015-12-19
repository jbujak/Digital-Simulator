package pl.jbujak.simulator.gui;

import static org.lwjgl.opengl.GL11.*;

import pl.jbujak.simulator.utils.Position;
import pl.jbujak.simulator.world.World;

public class BlockBorder implements IDrawable {
	World world;
	
	public BlockBorder(World world) {
		this.world = world;
	}
	

	@Override
	public void draw(int windowWidth, int windowHeight) {
		
		Position position = world.getSelectedBlock();

		if(position==null) {return;}

		int x = (int)position.x;
		int y = (int)position.y;
		int z = (int)position.z;
		
		if(world.getBlocks()[x][y][z] == null) {return;}
		
		double offset = 0.001;
		
		Position corner0 = world.getBlocks()[x][y][z].getActiveAreaCorner0();
		double x0 = corner0.x;
		double y0 = corner0.y;
		double z0 = corner0.z;
		
		Position corner1 = world.getBlocks()[x][y][z].getActiveAreaCorner1();
		double x1 = corner1.x;
		double y1 = corner1.y;
		double z1 = corner1.z;
		
		glLineWidth(5);
		glColor3d(0, 0, 0);
		glBegin(GL_LINES);
		
		glVertex3d(x+x0-offset, y+y0-offset, z+z0-offset);
		glVertex3d(x+x1+offset, y+y0-offset, z+z0-offset);

		glVertex3d(x+x0-offset, y+y0-offset, z+z0-offset);
		glVertex3d(x+x0-offset, y+y1+offset, z+z0-offset);

		glVertex3d(x+x0-offset, y+y0-offset, z+z0-offset);
		glVertex3d(x+x0-offset, y+y0-offset, z+z1+offset);
		
		glVertex3d(x+x0-offset, y+y1+offset, z+z1+offset);
		glVertex3d(x+x1+offset, y+y1+offset, z+z1+offset);
		
		glVertex3d(x+x0-offset, y+y1+offset, z+z1+offset);
		glVertex3d(x+x0-offset, y+y0-offset, z+z1+offset);
		
		glVertex3d(x+x0-offset, y+y1+offset, z+z1+offset);
		glVertex3d(x+x0-offset, y+y1+offset, z+z0-offset);
		
		glVertex3d(x+x1+offset, y+y1+offset, z+z0-offset);
		glVertex3d(x+x0-offset, y+y1+offset, z+z0-offset);
		
		glVertex3d(x+x1+offset, y+y1+offset, z+z0-offset);
		glVertex3d(x+x1+offset, y+y0-offset, z+z0-offset);
		
		glVertex3d(x+x1+offset, y+y1+offset, z+z0-offset);
		glVertex3d(x+x1+offset, y+y1+offset, z+z1+offset);
		
		glVertex3d(x+x1+offset, y+y0-offset, z+z1+offset);
		glVertex3d(x+x0-offset, y+y0-offset, z+z1+offset);
		
		glVertex3d(x+x1+offset, y+y0-offset, z+z1+offset);
		glVertex3d(x+x1+offset, y+y1+offset, z+z1+offset);
		
		glVertex3d(x+x1+offset, y+y0-offset, z+z1+offset);
		glVertex3d(x+x1+offset, y+y0-offset, z+z0-offset);

		glEnd();
		glColor3d(1, 1, 1);

	}

}
