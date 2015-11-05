package pl.jbujak.simulator.player;

import pl.jbujak.simulator.gui.IDrawable;
import pl.jbujak.simulator.gui.InventoryDrawer;

public class Inventory implements IDrawable{
	InventoryDrawer inventoryDrawer;
	
	public Inventory() {
		inventoryDrawer = new InventoryDrawer(this);
	}
	
	public void draw(int windowWidth, int windowHeight) {
		inventoryDrawer.draw(windowWidth, windowHeight);
	}
}
