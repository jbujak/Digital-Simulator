package pl.jbujak.simulator.world;

import java.util.ArrayList;
import java.util.HashSet;

import pl.jbujak.simulator.blocks.Block;
import pl.jbujak.simulator.player.IPlayer;
import pl.jbujak.simulator.utils.Position;

public interface IWorld {

	public abstract void changeBlock(Position position, Block newBlock);

	public abstract boolean isBlockSolid(Position position);

	public abstract Block[][][] getBlocks();

	public abstract ArrayList<HashSet<Position>> getBlocksToRender();

	public abstract IPlayer getPlayer();

	public abstract boolean isPositionOutOfWorld(Position position);

	public abstract int getNumberOfBlockTypes();
	
	public abstract void setSelectedBlock(Position position);
	
	public abstract Position getSelectedBlock();
	
	public abstract void setSelectedFace(Direction selectedFace);

	boolean blocksChanged();

	Direction getSelectedFace();
}