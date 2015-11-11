package pl.jbujak.simulator.gui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL12;

import static org.lwjgl.opengl.GL11.*;

//TextureLoader class was originally written by Krythic
public class TextureLoader {
    private static final int BYTES_PER_PIXEL = 4;//3 for RGB, 4 for RGBA
    private static final int textureHeight = 16;
    private static final int textureWidth = 16;
    private static final int texturesInRow = 16;
    private static final String textureFile = "/textures/textures.png";
    
    	public static int loadTexture(int id) {
				BufferedImage image = TextureLoader.loadImage(textureFile);
				int textureId = TextureLoader.loadTexture(image, id);
				return textureId;
    	}

    	private static int loadTexture(BufferedImage image, int id){

            int textureColumn = (id % texturesInRow) * textureWidth;
            int textureRow = (id / texturesInRow) * textureHeight;

          int[] pixels = new int[textureWidth * textureHeight];
            image.getRGB(textureColumn, textureRow, textureWidth, textureHeight, pixels, 0, textureWidth);

            ByteBuffer buffer = BufferUtils.createByteBuffer(textureWidth * textureHeight * BYTES_PER_PIXEL); 
            

            for(int y = 0; y < textureHeight; y++){
                for(int x = 0; x < textureWidth; x++){
                    int pixel = pixels[y * textureWidth + x];
                    buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
                    buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
                    buffer.put((byte) (pixel & 0xFF));               // Blue component
                    buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
                }
            }

            buffer.flip(); 

            // You now have a ByteBuffer filled with the color data of each pixel.
            // Now just create a texture ID and bind it. Then you can load it using 
            // whatever OpenGL method you want, for example:

          int textureID = glGenTextures(); //Generate texture ID
            glBindTexture(GL_TEXTURE_2D, textureID); //Bind texture ID

            //Setup wrap mode
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

            //Setup texture scaling filtering
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

            //Send texel data to OpenGL
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, textureWidth, textureHeight, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

            //Return the texture ID so we can bind it later again
          return textureID;
       }

       private static BufferedImage loadImage(String loc)
       {
            try {
               return ImageIO.read(TextureLoader.class.getResource(loc));
            } catch (IOException e) {
            }
		   return null;
       }
}