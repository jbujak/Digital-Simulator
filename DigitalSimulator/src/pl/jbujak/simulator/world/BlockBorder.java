package pl.jbujak.simulator.world;

import static org.lwjgl.opengl.GL11.*;
import pl.jbujak.simulator.gui.IDrawable;
import pl.jbujak.simulator.utils.Position;

public class BlockBorder implements IDrawable {
	World world;
	
	public BlockBorder(World world) {
		this.world = world;
	}
	

	@Override
	public void draw() {
		
		Position position = world.getSelectedBlock();

		if(position==null) {return;}

		double x = position.x;
		double y = position.y;
		double z = position.z;
		
		double offset = 0.001;
		
		glLineWidth(5);
		glColor3d(0, 0, 0);
		glBegin(GL_LINES);
		
		glVertex3d(x+0-offset, y+0-offset, z+0-offset);
		glVertex3d(x+1+offset, y+0-offset, z+0-offset);

		glVertex3d(x+0-offset, y+0-offset, z+0-offset);
		glVertex3d(x+0-offset, y+1+offset, z+0-offset);

		glVertex3d(x+0-offset, y+0-offset, z+0-offset);
		glVertex3d(x+0-offset, y+0-offset, z+1+offset);
		
		glVertex3d(x+0-offset, y+1+offset, z+1+offset);
		glVertex3d(x+1+offset, y+1+offset, z+1+offset);
		
		glVertex3d(x+0-offset, y+1+offset, z+1+offset);
		glVertex3d(x+0-offset, y+0-offset, z+1+offset);
		
		glVertex3d(x+0-offset, y+1+offset, z+1+offset);
		glVertex3d(x+0-offset, y+1+offset, z+0-offset);
		
		glVertex3d(x+1+offset, y+1+offset, z+0-offset);
		glVertex3d(x+0-offset, y+1+offset, z+0-offset);
		
		glVertex3d(x+1+offset, y+1+offset, z+0-offset);
		glVertex3d(x+1+offset, y+0-offset, z+0-offset);
		
		glVertex3d(x+1+offset, y+1+offset, z+0-offset);
		glVertex3d(x+1+offset, y+1+offset, z+1+offset);
		
		glVertex3d(x+1+offset, y+0-offset, z+1+offset);
		glVertex3d(x+0-offset, y+0-offset, z+1+offset);
		
		glVertex3d(x+1+offset, y+0-offset, z+1+offset);
		glVertex3d(x+1+offset, y+1+offset, z+1+offset);
		
		glVertex3d(x+1+offset, y+0-offset, z+1+offset);
		glVertex3d(x+1+offset, y+0-offset, z+0-offset);

		glEnd();
		glColor3d(1, 1, 1);

	}

}
