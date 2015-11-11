package pl.jbujak.simulator.gui;

import pl.jbujak.simulator.Simulation;
import pl.jbujak.simulator.blocks.BlockTextureManager;
import pl.jbujak.simulator.blocks.BlockType;
import pl.jbujak.simulator.player.Hotbar;
import pl.jbujak.simulator.player.Inventory;
import pl.jbujak.simulator.utils.Position;
import pl.jbujak.simulator.world.Direction;

import static org.lwjgl.opengl.GL11.*;

public class HotbarDrawer implements IDrawable {
	private int cellTextureId[];
	private Hotbar hotbar;
	private Position leftUpCornerPosition;
	
	public HotbarDrawer(Hotbar hotbar) {
		cellTextureId = new int[10];
		this.hotbar = hotbar;
	}
	
	@Override
	public void draw(int windowWidth, int windowHeight) {
		double cellSize = Inventory.getCellSize();
		double sizeX = cellSize * Inventory.hotbarWidth;
		glPushMatrix();
		glLoadIdentity();
		
		
		glTranslated(windowWidth/2, windowHeight*0.9, 0);
		glTranslated(-sizeX/2 - cellSize/2, 0, 0);
		
		leftUpCornerPosition = new Position(
				windowWidth/2 - sizeX/2,
				windowHeight*0.9 - cellSize/2, 0);
		
		for(int i=0; i<Inventory.hotbarWidth; i++)
		{

			glTranslated(cellSize, 0, 0);
			
			glEnable(GL_TEXTURE_2D);
			glBindTexture(GL_TEXTURE_2D, cellTextureId[i]);
			
			if(cellTextureId[i] == 0) {
				glColor3d(0.8, 0.8, 0.8);
			}
			else {
				glColor3d(1, 1, 1);
			}
			
			glPushMatrix();
			glTranslated(0, 0, -2);

			glBegin(GL_QUADS);

			glTexCoord2d(1, 0);
			glVertex2d(-cellSize/2, -cellSize/2);

			glTexCoord2d(0, 0);
			glVertex2d(-cellSize/2, cellSize/2);

			glTexCoord2d(0, 1);
			glVertex2d(cellSize/2, cellSize/2);
		
			glTexCoord2d(1, 1);
			glVertex2d(cellSize/2, -cellSize/2);

			glEnd();
			glPopMatrix();
			
			if(i == hotbar.getCurrentPosition() && !Simulation.isPaused()) {
				glLineWidth(5);
				glColor3d(0, 0, 0);

				glPushMatrix();
			}
			else {
				glLineWidth(3);
				glColor3d(0.4, 0.4, 0.4);

				glPushMatrix();
				glTranslated(0, 0, -1);
			}
			

			glDisable(GL_TEXTURE_2D);
			glBegin(GL_LINES);
			
			glVertex2d(cellSize/2, -cellSize/2);
			glVertex2d(cellSize/2, cellSize/2);

			glVertex2d(cellSize/2, cellSize/2);
			glVertex2d(-cellSize/2, cellSize/2);

			glVertex2d(-cellSize/2, cellSize/2);
			glVertex2d(-cellSize/2, -cellSize/2);

			glVertex2d(-cellSize/2, -cellSize/2);
			glVertex2d(cellSize/2, -cellSize/2);

			glEnd();
			glPopMatrix();

			glColor3d(1, 1, 1);
		}

		glColor3d(1, 1, 1);

		glPopMatrix();


	}
	
	public void setItem(int position, BlockType blockType) {
		if(BlockTextureManager.isRegistered(blockType)) {
			int newTextureId = TextureLoader.loadTexture(BlockTextureManager.getTextureId(blockType, Direction.UP));
			cellTextureId[position] = newTextureId;
		}
	}
	
	public Position getLeftUpCornerPosition() {
		return leftUpCornerPosition;
	}

}
