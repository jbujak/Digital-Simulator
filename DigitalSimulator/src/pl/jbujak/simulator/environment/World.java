package pl.jbujak.simulator.environment;

import java.util.ArrayList;
import java.util.HashSet;

import pl.jbujak.simulator.blocks.*;
import pl.jbujak.simulator.gui.CameraEngine;
import pl.jbujak.simulator.gui.RenderBlock;

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

		player = new Player(0, 5, 10, cameraEngine, this);
		blocks = new Block[xSize][ySize][zSize];

		prepareBlocksToRender();
		
		//initializeWorld();
				
		for(int x = 0; x < xSize; x++)
			for(int y = 0; y < 1; y++)
				for(int z = 0; z < zSize; z++) {
					changeBlock(x, y, z, new BedrockBlock());
				}
		
		for(int z = 0; z < zSize; z++) {
			changeBlock(10, 1, z, new GrassBlock());
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
	
	public Boolean isBlockSolid(double x, double y, double z) {
		int xCoordinate = (int)Math.floor(x);
		int yCoordinate = (int)Math.floor(y);
		int zCoordinate = (int)Math.floor(z);
		
		if(yCoordinate < 0) return true;
		if(blocks[xCoordinate][yCoordinate][zCoordinate] == null) return false;
		return true;
	}
	
	
	
	public Block[][][] getBlocks() {return blocks;}
	public ArrayList<HashSet<RenderBlock>> getBlocksToRender()
	{return blocksToRender;}
	public Player getPlayer() {return player;}
	public int getXSize() {return xSize;}
	public int getYSize() {return ySize;}
	public int getZSize() {return zSize;}
	
}
