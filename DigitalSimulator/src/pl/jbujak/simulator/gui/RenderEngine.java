package pl.jbujak.simulator.gui;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.glfw.GLFW.*;

import java.awt.image.BufferedImage;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashSet;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GLContext;

import pl.jbujak.simulator.blocks.BlockType;
import pl.jbujak.simulator.environment.Direction;
import pl.jbujak.simulator.environment.World;

public class RenderEngine {
	
	private final int sidesPerCube = 6;
	private final int verticesPerSide = 4;
	private final int coordinatesPerVertex = 3;
	private final int textureCoordsPerVertex = 2;
	
	private long windowHandle;
	private ArrayList<HashSet<RenderBlock>> blocksToRender;
	
	private BlockTypeFaceValue vboVertexHandle;
	private BlockTypeFaceValue vboTextureHandle;
	private BlockTypeFaceValue textureId;
	
	private int numberOfCubesOfType[];

	public RenderEngine(long windowHandle, World world) {
		this.windowHandle = windowHandle;
		this.blocksToRender = world.getBlocksToRender();
		this.numberOfCubesOfType = new int[world.numberOfBlockTypes];

		this.vboVertexHandle = new BlockTypeFaceValue(world.numberOfBlockTypes);
		this.vboTextureHandle = new BlockTypeFaceValue(world.numberOfBlockTypes);
		this.textureId = new BlockTypeFaceValue(world.numberOfBlockTypes);

		GLContext.createFromCurrent();
		
		prepareTextures();
		

		updateScene();
		for(Direction face: Direction.values()) {
			createVBO(BlockType.BEDROCK, face);
			createVBO(BlockType.GRASS, face);
		}
		render();
	}

	public void updateScene() {
		IntBuffer widthBuffer = BufferUtils.createIntBuffer(4);
		IntBuffer heightBuffer = BufferUtils.createIntBuffer(4);
		glfwGetWindowSize(windowHandle, widthBuffer, heightBuffer);
		
		int currentWidth = widthBuffer.get(0);
		int currentHeight = heightBuffer.get(0);

		glViewport(0, 0, currentWidth, currentHeight);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		double aspect = currentHeight / (double) currentWidth;
		glFrustum(-0.1, 0.1, aspect * -0.1, aspect * 0.1, 0.3, 100.0);

		glClearColor(1, 1, 1, 1);

		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_DEPTH_TEST);
	}
	
	public void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		for(Direction face: Direction.values()) {
			draw(BlockType.BEDROCK, face);
			draw(BlockType.GRASS, face);
		}
		
		glfwSwapBuffers(windowHandle);
	}
	
	private void prepareTextures() {
		
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
	
	private IntBuffer createVertexArray(BlockType blockType, Direction face) {
		IntBuffer vertexArray = BufferUtils.createIntBuffer(numberOfCubesOfType[blockType.value]*
				verticesPerSide*coordinatesPerVertex);
	
		HashSet<RenderBlock> blocksToRenderNow = blocksToRender.get(blockType.value);
		
		for(RenderBlock renderBlock: blocksToRenderNow) {
			int x = renderBlock.x;
			int y = renderBlock.y;
			int z = renderBlock.z;
			
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

	private void calculateNumberOfCubes(BlockType blockType) {
		numberOfCubesOfType[blockType.value] = blocksToRender.get(blockType.value).size();
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

class BlockTypeFaceValue {
	
	private final int numberOfFaces = 6;

	private int value[][];
	
	public BlockTypeFaceValue(int numberOfBlockTypes) {
		value = new int[numberOfBlockTypes][numberOfFaces];
	}
	
	public int getValue(BlockType blockType, Direction face) {
		return value[blockType.value][face.value];
	}
	
	public void setValue(BlockType blockType, Direction face, int value) {
		this.value[blockType.value][face.value] = value;
	}
	
	

}