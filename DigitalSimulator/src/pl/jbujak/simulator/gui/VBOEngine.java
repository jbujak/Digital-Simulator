package pl.jbujak.simulator.gui;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

import pl.jbujak.simulator.blocks.Block;
import pl.jbujak.simulator.blocks.BlockTextureManager;
import pl.jbujak.simulator.blocks.BlockType;
import pl.jbujak.simulator.utils.BlockTypeFaceValue;
import pl.jbujak.simulator.utils.Position;
import pl.jbujak.simulator.world.Direction;
import pl.jbujak.simulator.world.World;

public class VBOEngine {
	
	private final int sightRange = 4;
	private final int verticesPerSide = 4;
	private final int coordinatesPerVertex = 3;
	private final int textureCoordsPerVertex = 2;
	private final int componentsPerColor = 3;
	
	//private int[] numberOfCubesOfType;
	private Map<Position, Map<BlockType, Integer>> numberOfCubesOfType;

	private Map<Position, BlockTypeFaceValue> vboVertexHandle;
	private Map<Position, BlockTypeFaceValue> vboTextureHandle;
	private Map<Position, BlockTypeFaceValue> vboColorHandle;
	private BlockTypeFaceValue textureId;

	private Map<BlockType, HashSet<Position>> blocksToRender;
	
	private World world;
	private Block[][][] blocks;
	
	public VBOEngine(World world) {
		this.vboVertexHandle = new HashMap<>();
		this.vboTextureHandle = new HashMap<>();
		this.vboColorHandle = new HashMap<>();
		this.numberOfCubesOfType = new HashMap<>();

		for(Position chunk: world.chunks) {
			vboVertexHandle.put(chunk, new BlockTypeFaceValue());
			vboTextureHandle.put(chunk, new BlockTypeFaceValue());
			vboColorHandle.put(chunk, new BlockTypeFaceValue());
			numberOfCubesOfType.put(chunk, new HashMap<>());
		}
		
		this.textureId = new BlockTypeFaceValue();
		prepareTextures();
		this.world = world;
		this.blocks = world.getBlocks();
	}


	
	public void draw() {
		glEnable(GL_TEXTURE_2D);
		BlockType[] blockTypes = BlockType.values();
		for(BlockType blockType: blockTypes) {
			for(Direction face: blockType.getFaces()) {
				if(blockType.isTransparent()) {glDisable(GL_CULL_FACE);}
				else {glEnable(GL_CULL_FACE);}
				
				Position firstRenderedChunk = firstRenderedChunk();
				Position lastRenderedChunk = lastRenderedChunk();
				
				for(double x = firstRenderedChunk.x; x <= lastRenderedChunk.x; x++) {
					for(double z = firstRenderedChunk.z; z <= lastRenderedChunk.z; z++) {
						draw(blockType, face, new Position(x, 0, z));
					}
				}
			}
		}
	}
	
	public void update() {
		Set<Position> changedChunks = world.getChangedChunks();
		Set<Position> unchangedChunks = new HashSet<>();

		blocks = world.getBlocks();
		for(Position chunk: changedChunks) {
			if(!isChunkRendered(chunk)) {
				unchangedChunks.add(chunk);
				continue;
			}
			
			blocksToRender = world.getBlocksToRender(chunk);
			for(Direction face: Direction.values()) {
				for(BlockType blockType: BlockType.values())
					createVBO(blockType, face, chunk);
			}
		}
		world.setChangedChunks(unchangedChunks);
	}
	
	private boolean isChunkRendered(Position chunk) {
		if(chunk.x < firstRenderedChunk().x || chunk.y > lastRenderedChunk().y)
			return false;
		if(chunk.z < firstRenderedChunk().z || chunk.z > lastRenderedChunk().z)
			return false;
		
		return true;
	}
	
	private Position firstRenderedChunk() {
		Position playerPosition = world.getPlayer().getPosition();

		double playerChunkX = Math.floor(playerPosition.x / 16);
		double playerChunkZ = Math.floor(playerPosition.z / 16);
		
		double firstChunkX = Math.max(0, playerChunkX - sightRange);
		double firstChunkZ = Math.max(0, playerChunkZ - sightRange);
		
		return new Position(firstChunkX, 0, firstChunkZ);
	}
	
	private Position lastRenderedChunk() {
		Position playerPosition = world.getPlayer().getPosition();

		double playerChunkX = Math.floor(playerPosition.x / 16);
		double playerChunkZ = Math.floor(playerPosition.z / 16);
		
		double lastChunkX = Math.min(world.xSize/world.chunkSize - 1, playerChunkX + sightRange);
		double lastChunkZ = Math.min(world.zSize/world.chunkSize - 1, playerChunkZ + sightRange);
		
		return new Position(lastChunkX, 0, lastChunkZ);
	}

	private void prepareTextures() {
		for(BlockType blockType: BlockType.values()) {
			for(Direction face: Direction.values()) {
				if(BlockTextureManager.isRegistered(blockType)) {
					int tempTextureId = TextureLoader.loadTexture(BlockTextureManager.getTextureId(blockType, face));
					textureId.setValue(blockType, face, tempTextureId);
				}
			}
		}
	}

	private void draw(BlockType blockType, Direction face, Position chunk) {
		glBindTexture(GL_TEXTURE_2D, textureId.getValue(blockType, face)); 
		
		glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle.get(chunk).getValue(blockType, face));
		glVertexPointer(coordinatesPerVertex, GL_FLOAT, 0, 0L);
		
		glBindBuffer(GL_ARRAY_BUFFER, vboTextureHandle.get(chunk).getValue(blockType, face));
		glTexCoordPointer(textureCoordsPerVertex, GL_FLOAT, 0, 0L);
		
		glBindBuffer(GL_ARRAY_BUFFER, vboColorHandle.get(chunk).getValue(blockType, face));
		glColorPointer(componentsPerColor, GL_FLOAT, 0, 0L);
		
		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_TEXTURE_COORD_ARRAY);
		glEnableClientState(GL_COLOR_ARRAY);
		
		glDrawArrays(GL_QUADS, 0, numberOfCubesOfType.get(chunk).get(blockType)*
				verticesPerSide*coordinatesPerVertex);
		
		glDisableClientState(GL_TEXTURE_COORD_ARRAY);
		glDisableClientState(GL_VERTEX_ARRAY);
		glDisableClientState(GL_COLOR_ARRAY);
	}

	private void createVBO(BlockType blockType, Direction face, Position chunk) {
		calculateNumberOfCubes(blockType, chunk);

		FloatBuffer vertexArray = createVertexArray(blockType, face, chunk);
		FloatBuffer textureCoordArray = createTextureArray(blockType, face, chunk);
		FloatBuffer colorArray = createColorArray(blockType, face, chunk);
				
		vboVertexHandle.get(chunk).setValue(blockType, face, glGenBuffers());

		glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle.get(chunk).getValue(blockType, face));
		glBufferData(GL_ARRAY_BUFFER, vertexArray, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		vboTextureHandle.get(chunk).setValue(blockType, face, glGenBuffers());

		glBindBuffer(GL_ARRAY_BUFFER, vboTextureHandle.get(chunk).getValue(blockType, face));
		glBufferData(GL_ARRAY_BUFFER, textureCoordArray, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		vboColorHandle.get(chunk).setValue(blockType, face, glGenBuffers());
		
		glBindBuffer(GL_ARRAY_BUFFER, vboColorHandle.get(chunk).getValue(blockType, face));
		glBufferData(GL_ARRAY_BUFFER, colorArray, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);

	}

	private void calculateNumberOfCubes(BlockType blockType, Position chunk) {
		numberOfCubesOfType.get(chunk).put(blockType, blocksToRender.get(blockType).size());
	}

	private FloatBuffer createVertexArray(BlockType blockType, Direction face, Position chunk) {
		FloatBuffer vertexArray = BufferUtils.createFloatBuffer(numberOfCubesOfType.get(chunk).get(blockType)*
				verticesPerSide*coordinatesPerVertex);
	
		HashSet<Position> blocksToRenderNow = blocksToRender.get(blockType);
		
		for(Position blockToRenderNow: blocksToRenderNow) {
			float x = (float)blockToRenderNow.x;
			float y = (float)blockToRenderNow.y;
			float z = (float)blockToRenderNow.z;
			float distanceFromNeighbour = 0.0001f;
			float textureOffset = blockType.getTextureOffset(face);
			
			switch (face) {
			case UP:
				vertexArray.put(new float[] {
						x+0,y+1-textureOffset,z+1,
						x+1,y+1-textureOffset,z+1,
						x+1,y+1-textureOffset,z+0,
						x+0,y+1-textureOffset,z+0,
				});
				break;
			case DOWN:
				vertexArray.put(new float[] {
						x+0,y+distanceFromNeighbour+textureOffset,z+0, 
						x+1,y+distanceFromNeighbour+textureOffset,z+0, 
						x+1,y+distanceFromNeighbour+textureOffset,z+1, 
						x+0,y+distanceFromNeighbour+textureOffset,z+1,
				});
				break;
			case LEFT: 
				vertexArray.put(new float[] {
						x+1,y+0,z+distanceFromNeighbour+textureOffset, 
						x+0,y+0,z+distanceFromNeighbour+textureOffset, 
						x+0,y+1,z+distanceFromNeighbour+textureOffset, 
						x+1,y+1,z+distanceFromNeighbour+textureOffset,
				});
				break;
			case RIGHT: 
				vertexArray.put(new float[] {
						x+0,y+0,z+1-textureOffset, 
						x+1,y+0,z+1-textureOffset, 
						x+1,y+1,z+1-textureOffset, 
						x+0,y+1,z+1-textureOffset,
				});
				break;
			case FRONT: 
				vertexArray.put(new float[] {
						x+distanceFromNeighbour+textureOffset,y+0,z+0, 
						x+distanceFromNeighbour+textureOffset,y+0,z+1, 
						x+distanceFromNeighbour+textureOffset,y+1,z+1, 
						x+distanceFromNeighbour+textureOffset,y+1,z+0,
				});
				break;
			case BACK:
				vertexArray.put(new float[] {
						x+1-textureOffset,y+0,z+1, 
						x+1-textureOffset,y+0,z+0, 
						x+1-textureOffset,y+1,z+0, 
						x+1-textureOffset,y+1,z+1,
				});
				break;
			}
		}
		vertexArray.flip();
		return vertexArray;
	}

	private FloatBuffer createTextureArray(BlockType blockType, Direction face, Position chunk) {
		FloatBuffer textureCoordArray = BufferUtils.createFloatBuffer(numberOfCubesOfType.get(chunk).get(blockType)*
				verticesPerSide*textureCoordsPerVertex);

		HashSet<Position> blocksToRenderNow = blocksToRender.get(blockType);

		for(Position blockToRenderNow: blocksToRenderNow) {
			int x = (int)blockToRenderNow.x;
			int y = (int)blockToRenderNow.y;
			int z = (int)blockToRenderNow.z;
			
			if(face != Direction.UP && face != Direction.DOWN) {
				textureCoordArray.put(new float[] {
						1,1, 0,1, 0,0, 1,0,
				});
			}
			
			else if(blocks[x][y][z].getOrientation() == Direction.FRONT) {
				textureCoordArray.put(new float[] {
						0,0, 1,0, 1,1, 0,1, 
				});
			}
			
			else if(blocks[x][y][z].getOrientation() == Direction.LEFT) {
				textureCoordArray.put(new float[] {
						1,0, 1,1, 0,1, 0,0, 
				});
			}

			else if(blocks[x][y][z].getOrientation() == Direction.BACK) {
				textureCoordArray.put(new float[] {
						1,1, 0,1, 0,0, 1,0,
				});
			}

			else if(blocks[x][y][z].getOrientation() == Direction.RIGHT) {
				textureCoordArray.put(new float[] {
						0,1, 0,0, 1,0, 1,1, 
				});
			}

		}
		textureCoordArray.flip();
		
		return textureCoordArray;
	}
	
	private FloatBuffer createColorArray(BlockType blockType, Direction face, Position chunk) {
		FloatBuffer colorArray = BufferUtils.createFloatBuffer(numberOfCubesOfType.get(chunk).get(blockType)*
				verticesPerSide * componentsPerColor);
		HashSet<Position> blocksToRenderNow = blocksToRender.get(blockType);
		
		for(Position blockToRenderNow: blocksToRenderNow) {
			int x = (int)blockToRenderNow.x;
			int y = (int)blockToRenderNow.y;
			int z = (int)blockToRenderNow.z;

			colorArray.put(blocks[x][y][z].getColor(face));
		}
		
		colorArray.flip();
		
		return colorArray;
	}
}
