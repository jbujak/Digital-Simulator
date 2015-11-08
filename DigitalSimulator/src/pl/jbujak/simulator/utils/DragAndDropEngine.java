package pl.jbujak.simulator.utils;

import pl.jbujak.simulator.blocks.BlockType;
import pl.jbujak.simulator.gui.DraggedCell;
import pl.jbujak.simulator.gui.DrawEngine;
import pl.jbujak.simulator.player.Inventory;

public class DragAndDropEngine {
	private BlockType[][] inventoryTable;
	private DraggedCell draggedCell;

	public DragAndDropEngine(Inventory inventory) {
		inventoryTable = inventory.getInventoryTable();
		draggedCell = new DraggedCell();
		DrawEngine.addShape2D(draggedCell);

	}
	
	public void grabCell(int sourceX, int sourceY) {
		if(inventoryTable[sourceY][sourceX] == null) {return;}
		draggedCell.setIsDragged(true);
		draggedCell.setBlockType(inventoryTable[sourceY][sourceX]);
	}
	
	public void dropCell() { 
		draggedCell.setIsDragged(false);
	}
	
	public BlockType getDraggedCellBlockType() {
		return draggedCell.getBlockType();
	}
}
