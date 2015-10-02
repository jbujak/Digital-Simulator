package pl.jbujak.simulator.player;

import pl.jbujak.simulator.blocks.Block;
import pl.jbujak.simulator.blocks.BlockType;
import pl.jbujak.simulator.gui.HotbarDrawer;
import pl.jbujak.simulator.gui.IDrawable;

public class Hotbar implements IDrawable{
	private int currentSelection;
	private BlockType[] hotbar;
	private HotbarDrawer hotbarDrawer;
	
	public Hotbar() {
		currentSelection = 0;
		hotbar = new BlockType[10];
		hotbar[0] = BlockType.GRASS;
		hotbar[1] = BlockType.BEDROCK;
		
		hotbarDrawer = new HotbarDrawer(this);
	}
	
	public void nextItem() {
		currentSelection = (currentSelection+1)%10;
	}
	
	public void prevItem() {
		currentSelection = (currentSelection-1)%10;
		if(currentSelection < 0) {currentSelection+=10;}
	}
	
	public Block getCurrentItem() {
		if(hotbar[currentSelection] == null) {return null;}
		return hotbar[currentSelection].getNewBlock();
	}
	
	@Override
	public void draw() {
		hotbarDrawer.draw();
	}
}
