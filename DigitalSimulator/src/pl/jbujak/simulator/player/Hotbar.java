package pl.jbujak.simulator.player;

import pl.jbujak.simulator.blocks.BlockType;
import pl.jbujak.simulator.gui.HotbarDrawer;
import pl.jbujak.simulator.gui.IDrawable;
import pl.jbujak.simulator.utils.Position;

public class Hotbar implements IDrawable{
	public final int hotbarWidth;

	private int currentSelection;
	private BlockType[] hotbar;
	private HotbarDrawer hotbarDrawer;
	
	public Hotbar() {
		currentSelection = 0;
		hotbar = new BlockType[10];
		hotbarDrawer = new HotbarDrawer(this);
		hotbarWidth = Inventory.hotbarWidth;

		setItem(0, BlockType.GRASS);
		setItem(1, BlockType.REDSTONE_LINE);
		setItem(2, BlockType.WHITE_WOOL);
		setItem(3, BlockType.REDSTONE_TORCH);
		
	}
	
	public void nextItem() {
		currentSelection = (currentSelection+1)%hotbarWidth;
	}
	
	public void prevItem() {
		currentSelection = (currentSelection-1)%hotbarWidth;
		if(currentSelection < 0) {currentSelection+=hotbarWidth;}
	}
	
	public void setItem(int n) {
		currentSelection = n % 10;
	}
	
	public void setItem(int position, BlockType blockType) {
		hotbar[position] = blockType;
		hotbarDrawer.setItem(position, blockType);
	}
	
	public BlockType getCurrentItem() {
		if(hotbar[currentSelection] == null) {return null;}
		return hotbar[currentSelection];
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
