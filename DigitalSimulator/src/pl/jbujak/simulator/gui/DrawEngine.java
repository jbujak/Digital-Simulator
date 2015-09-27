package pl.jbujak.simulator.gui;
import static org.lwjgl.opengl.GL11.*;
import pl.jbujak.simulator.utils.Position;

public class DrawEngine {
	private static final double sizeOfAim = -0.0006;
	
	public static void drawAim() {
		glColor3d(0, 0, 0);
		glBegin(GL_QUADS);
		glVertex3d(sizeOfAim, -sizeOfAim, -0.2);
		glVertex3d(sizeOfAim, sizeOfAim, -0.2);
		glVertex3d(-sizeOfAim, sizeOfAim, -0.2);
		glVertex3d(-sizeOfAim, -sizeOfAim, -0.2);
		glEnd();
		glColor3d(1, 1, 1);

	}
	
	public static void drawSelectedBlockBorder(Position position) {
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
