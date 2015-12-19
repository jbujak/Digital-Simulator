package pl.jbujak.simulator.player;

import pl.jbujak.simulator.blocks.BlockType;
import pl.jbujak.simulator.gui.IDrawable;
import pl.jbujak.simulator.gui.InventoryDrawer;
import pl.jbujak.simulator.input.CursorProcessor;
import pl.jbujak.simulator.utils.DragAndDropEngine;
import pl.jbujak.simulator.utils.Position;

public class Inventory implements IDrawable{
	public static final int inventoryWidth = 10;
	public static final int inventoryHeight = 6;
	public static final int hotbarWidth = 10;
		
	private static double cellSize;
	private BlockType[][] inventoryTable;
	
	private InventoryDrawer inventoryDrawer;
	private Hotbar hotbar;
	private DragAndDropEngine dragAndDropEngine;
	
	public Inventory() {
		inventoryTable = new BlockType[inventoryHeight][inventoryWidth];
		fillInventoryTable();

		inventoryDrawer = new InventoryDrawer(this);
		hotbar = new Hotbar();
		dragAndDropEngine = new DragAndDropEngine(this);

	}
	
	public BlockType[][] getInventoryTable() {
		return inventoryTable;
	}
	
	public void nextItem() {
		hotbar.nextItem();
	}
	
	public void prevItem() {
		hotbar.prevItem();
	}
	
	public BlockType getCurrentItem() {
		return hotbar.getCurrentItem();
	}
	
	public void grabCell() {
		Position clickPosition = CursorProcessor.getCursorPosition();
		Position leftUpCornerPosition = inventoryDrawer.getLeftUpCornerPosition();
		Position rightDownCornerPosition =
				new Position(leftUpCornerPosition.x + inventoryWidth*cellSize,
				             leftUpCornerPosition.y + inventoryHeight*cellSize, 0);
		
		if(isPositionInRectangle(clickPosition, leftUpCornerPosition, rightDownCornerPosition)) {
			int sourceCellX = (int)Math.floor((clickPosition.x - leftUpCornerPosition.x) / cellSize);
			int sourceCellY = (int)Math.floor((clickPosition.y - leftUpCornerPosition.y) / cellSize);

			dragAndDropEngine.grabCell(sourceCellX, sourceCellY);
		}
	}
	
	public void dropCell() {
		Position clickPosition = CursorProcessor.getCursorPosition();
		Position leftUpCornerPosition = hotbar.getLeftUpCornerPosition();
		Position rightDownCornerPosition =
				new Position(leftUpCornerPosition.x + hotbarWidth*cellSize,
				             leftUpCornerPosition.y + cellSize, 0);

		dragAndDropEngine.dropCell();
		
		if(isPositionInRectangle(clickPosition, leftUpCornerPosition, rightDownCornerPosition)) {
			int destinationCellX = (int)Math.floor((clickPosition.x - leftUpCornerPosition.x) / cellSize);
			hotbar.setItem(destinationCellX, dragAndDropEngine.getDraggedCellBlockType());
		}
	}
	
	public static double getCellSize() {
		return cellSize;
	}
	
	public void draw(int windowWidth, int windowHeight) {
		cellSize = 0.05 * windowWidth;

		inventoryDrawer.draw(windowWidth, windowHeight);
		hotbar.draw(windowWidth, windowHeight);
	}
	
	private void fillInventoryTable() {
		int i=0;
		int j=0;
		for(BlockType blockType: BlockType.values()) {
			inventoryTable[i][j] = blockType;

			j++;
			if(j==inventoryWidth) {
				j=0;
				i++;
			}
		}
	}
	
	private boolean isPositionInRectangle(Position position, 
			Position leftUpCornerPosition, Position rightDownCornerPosition) {
		if(position.x < leftUpCornerPosition.x) {return false;}
		if(position.x > rightDownCornerPosition.x) {return false;}
		if(position.y < leftUpCornerPosition.y) {return false;}
		if(position.y > rightDownCornerPosition.y) {return false;}
		return true;
	}
}

