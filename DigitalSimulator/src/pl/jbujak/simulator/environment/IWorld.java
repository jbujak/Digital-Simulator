package pl.jbujak.simulator.environment;

import java.util.ArrayList;
import java.util.HashSet;

import pl.jbujak.simulator.blocks.Block;
import pl.jbujak.simulator.gui.RenderBlock;
import pl.jbujak.simulator.utils.Position;

public interface IWorld {

	public abstract void changeBlock(Position position, Block newBlock);

	public abstract boolean isBlockSolid(Position position);

	public abstract Block[][][] getBlocks();

	public abstract ArrayList<HashSet<RenderBlock>> getBlocksToRender();

	public abstract IPlayer getPlayer();

	public abstract boolean isPositionOutOfWorld(Position position);

	public abstract int getNumberOfBlockTypes();
	
	public abstract void setSelectedBlock(Position position);
	
	public abstract Position getSelectedBlock();
	
	public abstract void setSelectedFace(Direction selectedFace);
}