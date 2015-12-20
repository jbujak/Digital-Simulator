package pl.jbujak.simulator.gui;

import pl.jbujak.simulator.Simulation;
import pl.jbujak.simulator.blocks.BlockTextureManager;
import pl.jbujak.simulator.blocks.BlockType;
import pl.jbujak.simulator.player.Hotbar;
import pl.jbujak.simulator.player.Inventory;
import pl.jbujak.simulator.utils.Position;
import pl.jbujak.simulator.world.Direction;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class HotbarDrawer implements IDrawable {
	private final int componentsPerColor = 3;
	private int cellTextureId[];
	private FloatBuffer cellColor[];

	private Hotbar hotbar;
	private Position leftUpCornerPosition;
	
	private Aim aim;
	
	public HotbarDrawer(Hotbar hotbar) {
		this.hotbar = hotbar;

		cellTextureId = new int[Inventory.hotbarWidth];
		cellColor = new FloatBuffer[Inventory.hotbarWidth];
	}
	
	@Override
	public void draw(int windowWidth, int windowHeight) {

		aim = new Aim();

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
			/*
			if(i==Inventory.hotbarWidth-1) {
			glPushMatrix();
			glLoadIdentity();
			glTranslated(windowWidth/2, windowHeight/2, 0);
			glColor3d(0, 0, 0);
			glBegin(GL_QUADS);
			glVertex2d(-sizeOfAim, -sizeOfAim);
			glVertex2d(-sizeOfAim, sizeOfAim);
			glVertex2d(sizeOfAim, sizeOfAim);
			glVertex2d(sizeOfAim, -sizeOfAim);
			glEnd();
			glPopMatrix();
			}
			*/

			glTranslated(cellSize, 0, 0);
			
			glColor3d(0.8, 0.8, 0.8);

			glPushMatrix();
			glTranslated(0, 0, -3);

			glBegin(GL_QUADS);

			glVertex2d(-cellSize/2, -cellSize/2);

			glVertex2d(-cellSize/2, cellSize/2);

			glVertex2d(cellSize/2, cellSize/2);
		
			glVertex2d(cellSize/2, -cellSize/2);

			glEnd();
			glPopMatrix();

			if(cellTextureId[i] != 0) {
				glEnable(GL_TEXTURE_2D);
				glBindTexture(GL_TEXTURE_2D, cellTextureId[i]);
				glColor3fv(cellColor[i]);;

				glPushMatrix();
				glTranslated(0, 0, -2);

				glBegin(GL_QUADS);

				glTexCoord2d(0, 0);
				glVertex2d(-cellSize/2, -cellSize/2);

				glTexCoord2d(0, 1);
				glVertex2d(-cellSize/2, cellSize/2);

				glTexCoord2d(1, 1);
				glVertex2d(cellSize/2, cellSize/2);
			
				glTexCoord2d(1, 0);
				glVertex2d(cellSize/2, -cellSize/2);

				glEnd();
				glPopMatrix();

			}
			
						
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

		aim.draw(windowWidth, windowHeight);
	}
	
	public void setItem(int position, BlockType blockType) {
		if(BlockTextureManager.isRegistered(blockType)) {
			int newTextureId = TextureLoader.loadTexture(BlockTextureManager.getPreviewId(blockType));
			cellTextureId[position] = newTextureId;
			
			float[] color = blockType.getNewBlock().getColor(Direction.UP);
			cellColor[position] = BufferUtils.createFloatBuffer(componentsPerColor);
			cellColor[position].put(color, 0, componentsPerColor);
			cellColor[position].flip();
		}
	}
	
	public Position getLeftUpCornerPosition() {
		return leftUpCornerPosition;
	}

}
