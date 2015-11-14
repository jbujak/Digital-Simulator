package pl.jbujak.simulator.player;

import pl.jbujak.simulator.blocks.Block;
import pl.jbujak.simulator.blocks.BlockType;
import pl.jbujak.simulator.gui.HotbarDrawer;
import pl.jbujak.simulator.gui.IDrawable;
import pl.jbujak.simulator.utils.Position;

public class Hotbar implements IDrawable{
	private int currentSelection;
	private BlockType[] hotbar;
	private HotbarDrawer hotbarDrawer;
	private int hotbarWidth;
	
	public Hotbar() {
		currentSelection = 0;
		hotbar = new BlockType[10];
		hotbarDrawer = new HotbarDrawer(this);
		hotbarWidth = Inventory.hotbarWidth;

		setItem(0, BlockType.GRASS);
		setItem(1, BlockType.REDSTONE);
		setItem(2, BlockType.WHITE_WOOL);
		
	}
	
	public void nextItem() {
		currentSelection = (currentSelection+1)%hotbarWidth;
	}
	
	public void prevItem() {
		currentSelection = (currentSelection-1)%hotbarWidth;
		if(currentSelection < 0) {currentSelection+=hotbarWidth;}
	}
	
	public void setItem(int position, BlockType blockType) {
		hotbar[position] = blockType;
		hotbarDrawer.setItem(position, blockType);
	}
	
	public Block getCurrentItem() {
		if(hotbar[currentSelection] == null) {return null;}
		return hotbar[currentSelection].getNewBlock();
	}
	
	public int getCurrentPosition() {
		return currentSelection;
	}
	
	public Position getLeftUpCornerPosition() {
		return hotbarDrawer.getLeftUpCornerPosition();
	}
	
	@Override
	public void draw(int windowWidth, int windowHeight) {
		hotbarDrawer.draw(windowWidth, windowHeight);
	}
}
