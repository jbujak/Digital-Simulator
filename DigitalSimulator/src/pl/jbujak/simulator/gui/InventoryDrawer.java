package pl.jbujak.simulator.gui;

import pl.jbujak.simulator.Simulation;
import pl.jbujak.simulator.blocks.BlockTextureManager;
import pl.jbujak.simulator.blocks.BlockType;
import pl.jbujak.simulator.player.Inventory;
import pl.jbujak.simulator.utils.Position;

import static org.lwjgl.opengl.GL11.*;

public class InventoryDrawer implements IDrawable {
	private final int inventoryWidth;
	private final int inventoryHeight;
	private BlockType[][] inventoryTable;
	private int[][] cellTextureId;
	
	private Position leftUpCornerPosition;
	
	public InventoryDrawer(Inventory inventory) {
		this.inventoryWidth = Inventory.inventoryWidth;
		this.inventoryHeight = Inventory.inventoryHeight;
		
		inventoryTable = inventory.getInventoryTable();
		cellTextureId = new int[inventoryHeight][inventoryWidth];
		
		for(int i=0; i<inventoryHeight; i++) {
			for(int j=0; j<inventoryWidth; j++) {
				if(inventoryTable[i][j] != null) {
				
					BlockType blockType = inventoryTable[i][j];
					
					if(BlockTextureManager.isRegistered(blockType)) {
						int currentTextureId = TextureLoader.loadTexture(
								BlockTextureManager.getPreviewId(blockType));
						cellTextureId[i][j] = currentTextureId;
					}
				}
				
			}
		}

		
		
	}

	@Override
	public void draw(int windowWidth, int windowHeight) {
		if(!Simulation.isInventoryOpen()) {
			return;
		}
		
		double cellSize = Inventory.getCellSize();

		double sizeX = cellSize * inventoryWidth;
		double sizeY = cellSize * inventoryHeight;
		
		
		glPushMatrix();
		glLoadIdentity();
		glTranslated(windowWidth/2 - sizeX/2, windowHeight/2-sizeY/2, 0);
		leftUpCornerPosition = new Position(windowWidth/2 - sizeX/2, windowHeight/2 - sizeY/2 - cellSize, 0);
		
		glColor3d(1, 1, 1);
		
		for(int i=0; i<inventoryHeight; i++) {
			for(int j=0; j<inventoryWidth; j++) {
				glTranslated(cellSize, 0, 0);
				
				glEnable(GL_TEXTURE_2D);
				glBindTexture(GL_TEXTURE_2D, cellTextureId[i][j]);
				if(cellTextureId[i][j] == 0) {glColor3d(0.8, 0.8, 0.8);}
				else {glColor3d(1, 1, 1);}
				
				glPushMatrix();
				glTranslated(0, 0, -1);
				
				glBegin(GL_QUADS);

				glTexCoord2d(1, 0);
				glVertex2d(-cellSize, -cellSize);

				glTexCoord2d(1, 1);
				glVertex2d(-cellSize, 0);

				glTexCoord2d(0, 1);
				glVertex2d(0, 0);
			
				glTexCoord2d(0, 0);
				glVertex2d(0, -cellSize);

				glEnd();
				glPopMatrix();
				
				glColor3d(0.4, 0.4, 0.4);

				glDisable(GL_TEXTURE_2D);
				glLineWidth(3);
				glBegin(GL_LINES);
				
				glVertex2d(0, -cellSize);
				glVertex2d(0, 0);

				glVertex2d(0, 0);
				glVertex2d(-cellSize, 0);

				glVertex2d(-cellSize, 0);
				glVertex2d(-cellSize, -cellSize);

				glVertex2d(-cellSize, -cellSize);
				glVertex2d(0, -cellSize);

				glEnd();

			}
			glTranslated(-sizeX, cellSize, 0);
		}

		glPopMatrix();
		glColor3d(1, 1, 1);
	}
	
	public Position getLeftUpCornerPosition() {
		return leftUpCornerPosition;
	}

}
