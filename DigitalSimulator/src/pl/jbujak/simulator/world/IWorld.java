package pl.jbujak.simulator.world;

import java.util.HashSet;
import java.util.Map;

import pl.jbujak.simulator.blocks.Block;
import pl.jbujak.simulator.blocks.BlockType;
import pl.jbujak.simulator.player.IPlayer;
import pl.jbujak.simulator.utils.Position;

public interface IWorld {
	public abstract void changeBlock(Position position, Block newBlock);

	public abstract boolean isBlockSolid(Position position);

	public abstract Block[][][] getBlocks();

	public abstract Map<BlockType, HashSet<Position>> getBlocksToRender();

	public abstract IPlayer getPlayer();

	public abstract boolean isPositionOutOfWorld(Position position);

	public abstract int getNumberOfBlockTypes();
	
	public abstract int getXSize();

	public abstract int getYSize();

	public abstract int getZSize();
	
	public abstract void setSelectedBlock(Position position);
	
	public abstract Position getSelectedBlock();
	
	public abstract void setSelectedFace(Direction selectedFace);

	public abstract boolean blocksChanged();

	Direction getSelectedFace();
}