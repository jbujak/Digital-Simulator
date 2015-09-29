package pl.jbujak.simulator.gui;

import java.awt.image.BufferedImage;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashSet;

import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import pl.jbujak.simulator.blocks.BlockType;
import pl.jbujak.simulator.environment.Direction;
import pl.jbujak.simulator.environment.IWorld;
import pl.jbujak.simulator.utils.BlockTypeFaceValue;
import pl.jbujak.simulator.utils.Position;

public class VBOEngine {
	private final int sidesPerCube = 6;
	private final int verticesPerSide = 4;
	private final int coordinatesPerVertex = 3;
	private final int textureCoordsPerVertex = 2;
	
	private int[] numberOfCubesOfType;

	private BlockTypeFaceValue vboVertexHandle;
	private BlockTypeFaceValue vboTextureHandle;
	private BlockTypeFaceValue textureId;

	private ArrayList<HashSet<Position>> blocksToRender;
	
	private IWorld world;
	
	public VBOEngine(IWorld world) {
		this.vboVertexHandle = new BlockTypeFaceValue(world.getNumberOfBlockTypes());
		this.vboTextureHandle = new BlockTypeFaceValue(world.getNumberOfBlockTypes());
		this.numberOfCubesOfType = new int[world.getNumberOfBlockTypes()];
		this.blocksToRender = world.getBlocksToRender();
		this.textureId = new BlockTypeFaceValue(world.getNumberOfBlockTypes());
		prepareTextures();
		this.world = world;
	}


	
	public void draw() {
		for(Direction face: Direction.values()) {
			BlockType[] blockTypes = BlockType.values();
			for(BlockType blockType: blockTypes){
				draw(blockType, face);
			}
		}
	}

	public void prepareTextures() {
		for(BlockType blockType: BlockType.values()) {
			for(Direction face: Direction.values()) {
				String textureName = BlockType.getTextureName(blockType, face);
				BufferedImage image = TextureLoader.loadImage(textureName);
				int tempTextureId = TextureLoader.loadTexture(image);
				textureId.setValue(blockType, face, tempTextureId);
			}
		}
	}

	private void draw(BlockType blockType, Direction face) {
		glBindTexture(GL_TEXTURE_2D, textureId.getValue(blockType, face)); 

		glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle.getValue(blockType, face));
		glVertexPointer(coordinatesPerVertex, GL_INT, 0, 0L);
		
		glBindBuffer(GL_ARRAY_BUFFER, vboTextureHandle.getValue(blockType, face));
		glTexCoordPointer(textureCoordsPerVertex, GL_INT, 0, 0L);
		
		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_TEXTURE_COORD_ARRAY);
		
		glDrawArrays(GL_QUADS, 0, numberOfCubesOfType[blockType.value]*
				verticesPerSide*coordinatesPerVertex);
		
		glDisableClientState(GL_TEXTURE_COORD_ARRAY);
		glDisableClientState(GL_VERTEX_ARRAY);
	}
	
	public void update() {
		blocksToRender = world.getBlocksToRender();
		for(Direction face: Direction.values()) {
			createVBO(BlockType.BEDROCK, face);
			createVBO(BlockType.GRASS, face);
		}
	}

	private void createVBO(BlockType blockType, Direction face) {
		calculateNumberOfCubes(blockType);
		IntBuffer vertexArray = createVertexArray(blockType, face);
		IntBuffer textureCoordArray = createTextureArray(blockType);
				
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
		numberOfCubesOfType[blockType.value] = blocksToRender.get(blockType.value).size();
	}

	private IntBuffer createVertexArray(BlockType blockType, Direction face) {
		IntBuffer vertexArray = BufferUtils.createIntBuffer(numberOfCubesOfType[blockType.value]*
				verticesPerSide*coordinatesPerVertex);
	
		HashSet<Position> blocksToRenderNow = blocksToRender.get(blockType.value);
		
		for(Position blockToRenderNow: blocksToRenderNow) {
			int x = (int)blockToRenderNow.x;
			int y = (int)blockToRenderNow.y;
			int z = (int)blockToRenderNow.z;
			
			switch (face) {
			case UP:
				vertexArray.put(new int[] {
						x+0,y+1,z+1, x+1,y+1,z+1, x+1,y+1,z+0, x+0,y+1,z+0,
				});
				break;
			case DOWN:
				vertexArray.put(new int[] {
						x+0,y+0,z+0, x+1,y+0,z+0, x+1,y+0,z+1, x+0,y+0,z+1,
				});
				break;
			case LEFT: 
				vertexArray.put(new int[] {
						x+1,y+0,z+0, x+0,y+0,z+0, x+0,y+1,z+0, x+1,y+1,z+0,
				});
				break;
			case RIGHT: 
				vertexArray.put(new int[] {
						x+0,y+0,z+1, x+1,y+0,z+1, x+1,y+1,z+1, x+0,y+1,z+1,
				});
				break;
			case FRONT: 
				vertexArray.put(new int[] {
						x+0,y+0,z+0, x+0,y+0,z+1, x+0,y+1,z+1, x+0,y+1,z+0,
				});
				break;
			case BACK:
				vertexArray.put(new int[] {
						x+1,y+0,z+1, x+1,y+0,z+0, x+1,y+1,z+0, x+1,y+1,z+1,
				});
				break;
			}
		}
		vertexArray.flip();
		return vertexArray;
	}

	private IntBuffer createTextureArray(BlockType blockType) {
		IntBuffer textureCoordArray = BufferUtils.createIntBuffer(numberOfCubesOfType[blockType.value]*
				sidesPerCube*verticesPerSide*textureCoordsPerVertex);

		for(int i=0; i<numberOfCubesOfType[blockType.value]*sidesPerCube; i++) {
			textureCoordArray.put(new int[] {
					1,1, 0,1, 0,0, 1,0
			});
		}
		textureCoordArray.flip();
		
		return textureCoordArray;
	}
}
