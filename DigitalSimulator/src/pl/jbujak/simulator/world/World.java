package pl.jbujak.simulator.world;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import pl.jbujak.simulator.blocks.*;
import pl.jbujak.simulator.gui.BlockBorder;
import pl.jbujak.simulator.gui.BlocksToRenderManager;
import pl.jbujak.simulator.gui.CameraEngine;
import pl.jbujak.simulator.gui.DrawEngine;
import pl.jbujak.simulator.player.IPlayer;
import pl.jbujak.simulator.player.Player;
import pl.jbujak.simulator.utils.Position;
import pl.jbujak.simulator.utils.PowerableUtils;

public class World {
	public final int numberOfBlockTypes;
	public final int chunkSize = 16;

	public static World instance;
	
	private final int maxXSize = 256;
	private final int maxYSize = 128;
	private final int maxZSize = 256;

	private Set<Position> changedChunks;

	private Block[][][] blocks;
	private BlocksToRenderManager blocksToRenderManager;
	private IPlayer player;
	
	private int xSize;
	private int ySize;
	private int zSize;
	private Set<Position> chunks;
	
	private Position selectedBlock;
	private Direction selectedFace;
	private static boolean isCreated=false;

	
	public static World create(int xSize, int ySize, int zSize) {
		if(isCreated) return instance;
		instance = new World(xSize, ySize, zSize);
		return instance;
	}
	
	private World(int xSize, int ySize, int zSize) {
		changeSize(xSize, ySize, zSize);
		
		numberOfBlockTypes = BlockType.values().length;
		blocks = new Block[maxXSize][maxYSize][maxZSize];
		
		Position startPosition = new Position(5, 5, 5);
		CameraEngine cameraEngine = new CameraEngine();
		
		registerBlocks();
		
		player = new Player(startPosition, this, cameraEngine);
		blocksToRenderManager = new BlocksToRenderManager(blocks);
		
		DrawEngine.addShape3D(new BlockBorder(this));
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
		return !changedChunks.isEmpty();
	}
	
	public Set<Position> getChangedChunks() {
		Set<Position> result = new HashSet<>(changedChunks);
		changedChunks = new HashSet<>();
		
		return result;
	}
	
	public void setChangedChunks(Set<Position> newChangedChanks) {
		changedChunks = newChangedChanks;
	}
	
	public void changeBlock(Position position, Block newBlock) {
		if(position == null) {return;}
		int x = (int)position.x;
		int y = (int)position.y;
		int z = (int)position.z;
		
		blocksToRenderManager.changeBlock(position, newBlock);
		blocks[x][y][z] = newBlock;

		changedChunks.add(getChunk(position));
		
		PowerableUtils.updateNearBlocks(position);
	}
	
	public boolean isBlockSolid(Position position) {
		int xCoordinate = (int)Math.floor(position.x);
		int yCoordinate = (int)Math.floor(position.y);
		int zCoordinate = (int)Math.floor(position.z);
		
		if(isPositionOutOfWorld(position)) return true;

		if(blocks[xCoordinate][yCoordinate][zCoordinate] == null) return false;
		return blocks[xCoordinate][yCoordinate][zCoordinate].isSolid();
	}
	
	public void addChangedChunk(Position chunk) {
		changedChunks.add(chunk);
	}
	
	
	
	public Block[][][] getBlocks() {return blocks;}

	public Map<BlockType, HashSet<Position>> getBlocksToRender(Position chunk)
	{return blocksToRenderManager.getBlocksToRender(chunk);}

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

	public int getNumberOfBlockTypes() {
		return numberOfBlockTypes;
	}
	
	public int getXSize() {
		return xSize;
	}

	public int getYSize() {
		return ySize;
	}

	public int getZSize() {
		return zSize;
	}
	
	public void changeSize(int xSize, int ySize, int zSize) {
		this.xSize = xSize;
		this.ySize = ySize;
		this.zSize = zSize;
		
		changedChunks = new HashSet<>();
		chunks = new HashSet<>();
		for(int x = 0; x < xSize; x += 16){
			for(int z = 0; z < zSize; z += 16) {
				chunks.add(new Position(x/16, 0, z/16));
			}
		}


	}
	
	public Set<Position> getChunks() {
		return chunks;
	}
	
	public Block getBlock(Position position) {
		int x = (int)position.x;
		int y = (int)position.y;
		int z = (int)position.z;
		
		return blocks[x][y][z];
	}
	
	public Position getChunk(Position position) {
		return new Position(Math.floor(position.x/chunkSize), 0, Math.floor(position.z/chunkSize));
	}

	private void registerBlocks() {
		for(BlockType blockType: BlockType.values()) {
			Block.registerBlock(blockType.getNewBlock());
		}

	}
}
