package pl.jbujak.simulator.world;

import java.util.HashSet;
import java.util.Map;

import pl.jbujak.simulator.blocks.*;
import pl.jbujak.simulator.gui.BlockBorder;
import pl.jbujak.simulator.gui.BlocksToRenderManager;
import pl.jbujak.simulator.gui.CameraEngine;
import pl.jbujak.simulator.gui.DrawEngine;
import pl.jbujak.simulator.player.IPlayer;
import pl.jbujak.simulator.player.Player;
import pl.jbujak.simulator.utils.Position;

public class World implements IWorld {
	public final int numberOfBlockTypes;
	
	private boolean blocksChanged=false;

	public final int xSize;
	public final int ySize;
	public final int zSize;

	private Block[][][] blocks;
	private BlocksToRenderManager blocksToRenderManager;
	private IPlayer player;
	
	private Position selectedBlock;
	private Direction selectedFace;

	public World(int xSize, int ySize, int zSize, IWorldGenerator generator) {
		this.xSize = xSize;
		this.ySize = ySize;
		this.zSize = zSize;

		numberOfBlockTypes = BlockType.values().length;
		blocks = new Block[xSize][ySize][zSize];
		
		Position startPosition = new Position(5, 5, 5);
		CameraEngine cameraEngine = new CameraEngine();
		
		registerBlocks();
		
		player = new Player(startPosition, this, cameraEngine);
		blocksToRenderManager = new BlocksToRenderManager(blocks);
		
		DrawEngine.addShape3D(new BlockBorder(this));

		generator.generate(this);
	}
	
	public void setSelectedBlock(Position selectedBlock) {
		this.selectedBlock = selectedBlock;
	}
	
	public Position getSelectedBlock() {
		return selectedBlock;
	}
	
	public void setSelectedFace(Direction selectedFace) {
		this.selectedFace = selectedFace;
	}
	
	public Direction getSelectedFace() {
		return selectedFace;
	}
	
	public boolean blocksChanged() {
		boolean result = blocksChanged;
		blocksChanged = false;
		return result;
	}
	
	public void changeBlock(Position position, Block newBlock) {
		if(position == null) {return;}
		int x = (int)position.x;
		int y = (int)position.y;
		int z = (int)position.z;
		
		blocksToRenderManager.changeBlock(position, newBlock);
		blocks[x][y][z] = newBlock;
		blocksChanged=true;
	}
	
	public boolean isBlockSolid(Position position) {
		int xCoordinate = (int)Math.floor(position.x);
		int yCoordinate = (int)Math.floor(position.y);
		int zCoordinate = (int)Math.floor(position.z);
		
		if(isPositionOutOfWorld(position)) return true;

		if(blocks[xCoordinate][yCoordinate][zCoordinate] == null) return false;
		return true;
	}
	
	
	
	public Block[][][] getBlocks() {return blocks;}

	public Map<BlockType, HashSet<Position>> getBlocksToRender()
	{return blocksToRenderManager.getBlocksToRender();}

	public IPlayer getPlayer() {return player;}
	
	public boolean isPositionOutOfWorld(Position position) {
		if (position.x < 0) {
			return true;
		}
		if (position.y < 0) {
			return true;
		}
		if (position.z < 0) {
			return true;
		}

		if (position.x >= xSize) {
			return true;
		}
		if (position.y >= ySize) {
			return true;
		}
		if (position.z >= zSize) {
			return true;
		}

		return false;

	}

	@Override
	public int getNumberOfBlockTypes() {
		return numberOfBlockTypes;
	}
	
	private void registerBlocks() {
		for(BlockType blockType: BlockType.values()) {
			Block.registerBlock(blockType.getNewBlock());
		}

	}
}
