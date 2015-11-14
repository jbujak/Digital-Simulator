package pl.jbujak.simulator.gui;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

import pl.jbujak.simulator.blocks.BlockTextureManager;
import pl.jbujak.simulator.blocks.BlockType;
import pl.jbujak.simulator.utils.BlockTypeFaceValue;
import pl.jbujak.simulator.utils.Position;
import pl.jbujak.simulator.world.Direction;
import pl.jbujak.simulator.world.IWorld;

public class VBOEngine {
	private final int sidesPerCube = 6;
	private final int verticesPerSide = 4;
	private final int coordinatesPerVertex = 3;
	private final int textureCoordsPerVertex = 2;
	
	//private int[] numberOfCubesOfType;
	private Map<BlockType, Integer> numberOfCubesOfType;

	private BlockTypeFaceValue vboVertexHandle;
	private BlockTypeFaceValue vboTextureHandle;
	private BlockTypeFaceValue textureId;

	private Map<BlockType, HashSet<Position>> blocksToRender;
	
	private IWorld world;
	
	public VBOEngine(IWorld world) {
		this.vboVertexHandle = new BlockTypeFaceValue();
		this.vboTextureHandle = new BlockTypeFaceValue();
		this.numberOfCubesOfType = new HashMap<BlockType, Integer>();
		this.blocksToRender = world.getBlocksToRender();
		this.textureId = new BlockTypeFaceValue();
		prepareTextures();
		this.world = world;
	}


	
	public void draw() {
		glEnable(GL_TEXTURE_2D);
			BlockType[] blockTypes = BlockType.values();
		for(BlockType blockType: blockTypes) {
			for(Direction face: blockType.getFaces()) {
				if(blockType.isTransparent()) {glDisable(GL_CULL_FACE);}
				else {glEnable(GL_CULL_FACE);}

				draw(blockType, face);
			}
		}
	}

	public void prepareTextures() {
		for(BlockType blockType: BlockType.values()) {
			for(Direction face: Direction.values()) {
				if(BlockTextureManager.isRegistered(blockType)) {
					int tempTextureId = TextureLoader.loadTexture(BlockTextureManager.getTextureId(blockType, face));
					textureId.setValue(blockType, face, tempTextureId);
				}
			}
		}
	}

	private void draw(BlockType blockType, Direction face) {
		glBindTexture(GL_TEXTURE_2D, textureId.getValue(blockType, face)); 

		glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle.getValue(blockType, face));
		glVertexPointer(coordinatesPerVertex, GL_FLOAT, 0, 0L);
		
		glBindBuffer(GL_ARRAY_BUFFER, vboTextureHandle.getValue(blockType, face));
		glTexCoordPointer(textureCoordsPerVertex, GL_FLOAT, 0, 0L);
		
		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_TEXTURE_COORD_ARRAY);
		
		glDrawArrays(GL_QUADS, 0, numberOfCubesOfType.get(blockType)*
				verticesPerSide*coordinatesPerVertex);
		
		glDisableClientState(GL_TEXTURE_COORD_ARRAY);
		glDisableClientState(GL_VERTEX_ARRAY);
	}
	
	public void update() {
		blocksToRender = world.getBlocksToRender();
		for(Direction face: Direction.values()) {
			for(BlockType blockType: BlockType.values())
				createVBO(blockType, face);
		}
	}

	private void createVBO(BlockType blockType, Direction face) {
		calculateNumberOfCubes(blockType);
		FloatBuffer vertexArray = createVertexArray(blockType, face);
		FloatBuffer textureCoordArray = createTextureArray(blockType);
				
		vboVertexHandle.setValue(blockType, face, glGenBuffers());

		glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle.getValue(blockType, face));
		glBufferData(GL_ARRAY_BUFFER, vertexArray, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		vboTextureHandle.setValue(blockType, face, glGenBuffers());

		glBindBuffer(GL_ARRAY_BUFFER, vboTextureHandle.getValue(blockType, face));
		glBufferData(GL_ARRAY_BUFFER, textureCoordArray, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);

	}

	private void calculateNumberOfCubes(BlockType blockType) {
		numberOfCubesOfType.put(blockType, blocksToRender.get(blockType).size());
	}

	private FloatBuffer createVertexArray(BlockType blockType, Direction face) {
		FloatBuffer vertexArray = BufferUtils.createFloatBuffer(numberOfCubesOfType.get(blockType)*
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

	private FloatBuffer createTextureArray(BlockType blockType) {
		FloatBuffer textureCoordArray = BufferUtils.createFloatBuffer(numberOfCubesOfType.get(blockType)*
				sidesPerCube*verticesPerSide*textureCoordsPerVertex);

		for(int i=0; i<numberOfCubesOfType.get(blockType)*sidesPerCube; i++) {
			textureCoordArray.put(new float[] {
					1,1, 0,1, 0,0, 1,0
			});
		}
		textureCoordArray.flip();
		
		return textureCoordArray;
	}
}
