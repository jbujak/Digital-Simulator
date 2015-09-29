package pl.jbujak.simulator.fake;

import java.util.ArrayList;
import java.util.HashSet;

import pl.jbujak.simulator.blocks.Block;
import pl.jbujak.simulator.environment.Direction;
import pl.jbujak.simulator.environment.IPlayer;
import pl.jbujak.simulator.environment.IWorld;
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
	public void changeBlock(Position position, Block newBlock) {
		int x = (int)position.x;
		int y = (int)position.y;
		int z = (int)position.z;
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
	public ArrayList<HashSet<Position>> getBlocksToRender() {
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

	@Override
	public void setSelectedBlock(Position position) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Position getSelectedBlock() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSelectedFace(Direction selectedFace) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean blocksChanged() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Direction getSelectedFace() {
		// TODO Auto-generated method stub
		return null;
	}

}
