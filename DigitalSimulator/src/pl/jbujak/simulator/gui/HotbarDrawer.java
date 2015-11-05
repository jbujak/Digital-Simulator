package pl.jbujak.simulator.gui;

import pl.jbujak.simulator.blocks.BlockType;
import pl.jbujak.simulator.player.Hotbar;
import pl.jbujak.simulator.world.Direction;

import static org.lwjgl.opengl.GL11.*;

public class HotbarDrawer implements IDrawable {
	private int cellTextureId[];
	private Hotbar hotbar;
	
	public HotbarDrawer(Hotbar hotbar) {
		cellTextureId = new int[10];
		this.hotbar = hotbar;
	}
	
	@Override
	public void draw(int windowWidth, int windowHeight) {
		double hotbarWidth = windowWidth * 0.5;
		double cellSize = hotbarWidth * 0.1;
		glPushMatrix();
		glLoadIdentity();
		
		glEnable(GL_TEXTURE_2D);
		
		glTranslated(windowWidth/2, windowHeight*0.9, 0);
		glTranslated(-hotbarWidth/2, 0, 0);
		for(int i=0; i<10; i++)
		{

			glTranslated(cellSize, 0, 0);
			glBindTexture(GL_TEXTURE_2D, cellTextureId[i]);
			glBegin(GL_QUADS);

			glTexCoord2d(1, 0);
			glVertex3d(-cellSize/2, -cellSize/2, -0.1);

			glTexCoord2d(0, 0);
			glVertex3d(-cellSize/2, cellSize/2, -0.1);

			glTexCoord2d(0, 1);
			glVertex3d(cellSize/2, cellSize/2, -0.1);
		
			glTexCoord2d(1, 1);
			glVertex3d(cellSize/2, -cellSize/2, -0.1);

			glEnd();
			
			if(i == hotbar.getCurrentPosition()) {

				glColor3d(0, 0, 0);
				glPushMatrix();
				glTranslated(0, 0, 0.0001);
				glBegin(GL_LINES);
				
				glVertex3d(cellSize/2, -cellSize/2, -0.1);
				glVertex3d(cellSize/2, cellSize/2, -0.1);

				glVertex3d(cellSize/2, cellSize/2, -0.1);
				glVertex3d(-cellSize/2, cellSize/2, -0.1);

				glVertex3d(-cellSize/2, cellSize/2, -0.1);
				glVertex3d(-cellSize/2, -cellSize/2, -0.1);

				glVertex3d(-cellSize/2, -cellSize/2, -0.1);
				glVertex3d(cellSize/2, -cellSize/2, -0.1);

				glEnd();
				glPopMatrix();
				glColor3d(1, 1, 1);
			}
		}

		glColor3d(1, 1, 1);

		glPopMatrix();


	}
	
	public void setItem(int position, BlockType blockType) {
		String newTextureName = BlockType.getTextureName(blockType, Direction.UP);
		int newTextureId = TextureLoader.loadTexture(newTextureName);
		cellTextureId[position] = newTextureId;
	}

}
