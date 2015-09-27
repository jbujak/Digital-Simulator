package pl.jbujak.simulator.environment;

import pl.jbujak.simulator.blocks.Block;
import pl.jbujak.simulator.blocks.BlockType;

public class Inventory {
	private int currentSelection;
	private BlockType[] hotbar;
	
	public Inventory() {
		currentSelection = 0;
		hotbar = new BlockType[10];
		hotbar[0] = BlockType.GRASS;
		hotbar[1] = BlockType.BEDROCK;
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
}
