package pl.jbujak.simulator.environment;

import java.util.ArrayList;
import java.util.HashSet;

import pl.jbujak.simulator.blocks.*;
import pl.jbujak.simulator.gui.CameraEngine;
import pl.jbujak.simulator.gui.RenderBlock;
import pl.jbujak.simulator.utils.Position;

public class World {
	public final int numberOfBlockTypes;

	private final int xSize = 64;
	private final int ySize = 64;
	private final int zSize = 64;

	private Block[][][] blocks;
	private ArrayList<HashSet<RenderBlock>> blocksToRender;
	private Player player;

	public World(CameraEngine cameraEngine) {
		numberOfBlockTypes = BlockType.values().length;
		
		Position startPosition = new Position(5, 5, 10);
		blocks = new Block[xSize][ySize][zSize];
		player = new Player(startPosition, cameraEngine, this);

		prepareBlocksToRender();
		
		//initializeWorld();
				
		for(int x = 0; x < xSize; x++)
			for(int y = 0; y < 1; y++)
				for(int z = 0; z < zSize; z++) {
					changeBlock(x, y, z, new BedrockBlock());
				}
		
		for(int z = 0; z < zSize; z++) {
			if(z%2 == 0) {
				changeBlock(10, 1, z, new GrassBlock());
				changeBlock(10, 2, z, new GrassBlock());
				changeBlock(10, 3, z, new GrassBlock());
			}
			else {
				changeBlock(10, 1, z, new BedrockBlock());
				changeBlock(10, 2, z, new BedrockBlock());
				changeBlock(10, 3, z, new BedrockBlock());
			}
		}
	}

	private void prepareBlocksToRender() {
		blocksToRender = new ArrayList<HashSet<RenderBlock>>();
		for(int i=0; i<numberOfBlockTypes; i++) {
			blocksToRender.add(new HashSet<RenderBlock>());
		}
	}
	
	public void changeBlock(int x, int y, int z, Block newBlock) {
		if(blocks[x][y][z] != null) {
			BlockType previousBlockType = blocks[x][y][z].getBlockType();
			RenderBlock previousRenderBlock = new RenderBlock(x, y, z);
			blocksToRender.get(previousBlockType.value).remove(previousRenderBlock);
		}
		
		BlockType newBlockType = newBlock.getBlockType();
		RenderBlock newRenderBlock = new RenderBlock(x, y, z);
		blocksToRender.get(newBlockType.value).add(newRenderBlock);
		
		blocks[x][y][z] = newBlock;
	}
	
	public Boolean isBlockSolid(Position position) {
		int xCoordinate = (int)Math.floor(position.x);
		int yCoordinate = (int)Math.floor(position.y);
		int zCoordinate = (int)Math.floor(position.z);
		
		if(yCoordinate < 0) return true;
		if(blocks[xCoordinate][yCoordinate][zCoordinate] == null) return false;
		return true;
	}
	
	
	
	public Block[][][] getBlocks() {return blocks;}
	public ArrayList<HashSet<RenderBlock>> getBlocksToRender()
	{return blocksToRender;}
	public Player getPlayer() {return player;}
	
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

		if (position.x >= xSize-0.2) {
			return true;
		}
		if (position.y >= ySize-0.2) {
			return true;
		}
		if (position.z >= zSize-0.2) {
			return true;
		}

		return false;

	}
	
}
