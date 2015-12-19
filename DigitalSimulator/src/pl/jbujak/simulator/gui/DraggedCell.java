package pl.jbujak.simulator.gui;

import pl.jbujak.simulator.blocks.BlockTextureManager;
import pl.jbujak.simulator.blocks.BlockType;
import pl.jbujak.simulator.input.CursorProcessor;
import pl.jbujak.simulator.player.Inventory;
import pl.jbujak.simulator.utils.Position;
import pl.jbujak.simulator.world.Direction;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class DraggedCell implements IDrawable{
	private int componentsPerColor = 3;
	private int textureId;
	private FloatBuffer cellColor;
	private boolean isDragged = false;
	private BlockType blockType;
	
	public void setIsDragged(boolean value) {
		isDragged = value;
	}
	
	public void setBlockType(BlockType blockType) {
		this.blockType = blockType;
		textureId = TextureLoader.loadTexture(BlockTextureManager.getPreviewId(blockType));

		float[] color = blockType.getNewBlock().getColor(Direction.UP);
		cellColor = BufferUtils.createFloatBuffer(componentsPerColor);
		cellColor.put(color, 0, componentsPerColor);
		cellColor.flip();
	}
	
	public BlockType getBlockType() {
		return blockType;
	}
	
	public void draw(int windowWidth, int windowHeight) {
		if(!isDragged) {return;}
		
		double cellSize = Inventory.getCellSize();
		
		Position cursorPosition = CursorProcessor.getCursorPosition();
		
		glPushMatrix();
		glLoadIdentity();
		
		glTranslated(cursorPosition.x, cursorPosition.y, 0);
		glDisable(GL_TEXTURE_2D);
		glColor3d(0.8, 0.8, 0.8);
		glBegin(GL_QUADS);
		glVertex2d(-cellSize/2, -cellSize/2);

		glVertex2d(-cellSize/2, cellSize/2);

		glVertex2d(cellSize/2, cellSize/2);
	
		glVertex2d(cellSize/2, -cellSize/2);

		glEnd();
		glPopMatrix();
			
		glPushMatrix();
		glTranslated(cursorPosition.x, cursorPosition.y, 1);

		glColor3fv(cellColor);
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, textureId);
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
}
