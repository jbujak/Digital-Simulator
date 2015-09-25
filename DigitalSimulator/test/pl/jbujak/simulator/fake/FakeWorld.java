package pl.jbujak.simulator.fake;

import java.util.ArrayList;
import java.util.HashSet;

import pl.jbujak.simulator.blocks.Block;
import pl.jbujak.simulator.environment.IPlayer;
import pl.jbujak.simulator.environment.IWorld;
import pl.jbujak.simulator.gui.RenderBlock;
import pl.jbujak.simulator.utils.Position;

public class FakeWorld implements IWorld{
	public boolean isBlockSolid;
	public boolean isPositionOutOfWorld;
	public int numberOfBlockTypes;
	
	Block[][][] blocks;
	
	public FakeWorld() {
		blocks = new Block[25][5][25];
	}

	@Override
	public void changeBlock(int x, int y, int z, Block newBlock) {
		blocks[x][y][z] = newBlock;
	}

	@Override
	public boolean isBlockSolid(Position position) {
		return isBlockSolid;
	}

	@Override
	public Block[][][] getBlocks() {
		return blocks;
	}

	@Override
	public ArrayList<HashSet<RenderBlock>> getBlocksToRender() {
		return null;
	}

	@Override
	public IPlayer getPlayer() {
		return null;
	}

	@Override
	public boolean isPositionOutOfWorld(Position position) {
		return isPositionOutOfWorld;
	}

	@Override
	public int getNumberOfBlockTypes() {
		return numberOfBlockTypes;
	}

}
