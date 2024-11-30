package kn.end.engine.gr.d2;
import de.matthiasmann.twl.utils.PNGDecoder;
import kn.end.engine.gr.core.Shader;
import kn.end.engine.util.AABBI;

import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

public class animatedRect extends Object2D{

    private int[] textureIDs;
    private int frame=0;
    private final int max;
    private final int delayMax;
    private int delay=0;

    public animatedRect(AABBI aabbi, String texturePath, int frames, int delay) {
        super(aabbi);
        textureIDs=new int[frames];
        max=frames;
        this.delayMax = delay;
        try {
            for (int i = 0; i <frames ; i++) {
                textureIDs[i] = loadTexture(texturePath+String.valueOf(i)+".png");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int loadTexture(String filePath) throws IOException {
        // Load the PNG file
        PNGDecoder decoder = new PNGDecoder(Shader.class.getResourceAsStream(filePath));

        // Create a ByteBuffer to hold the texture data
        ByteBuffer buffer = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
        decoder.decode(buffer, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
        buffer.flip(); // Prepare buffer for reading

        // Generate a texture ID and bind it
        int textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);

        // Upload the texture to OpenGL
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

        // Set texture parameters
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        // Unbind the texture
        glBindTexture(GL_TEXTURE_2D, 0);

        return textureID;
    }


@Override
public void draw(Object[] args) {

        glEnable(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, textureIDs[frame]);
        delay++;
        if (delay>=delayMax){
            delay=0;
        frame++;
    }if (frame==max)frame=0;
        glBegin(GL_QUADS);

        // Bottom-left
        glTexCoord2f(0.0f, 0.0f);
        glVertex2f(-1.0f, -1.0f);

        // Bottom-right
        glTexCoord2f(1.0f, 0.0f);
        glVertex2f(1.0f, -1.0f);

        // Top-right
        glTexCoord2f(1.0f, 1.0f);
        glVertex2f(1.0f, 1.0f);

        // Top-left
        glTexCoord2f(0.0f, 1.0f);
        glVertex2f(-1.0f, 1.0f);

        glEnd();

        glBindTexture(GL_TEXTURE_2D, 0);
        glDisable(GL_TEXTURE_2D);

    }

    public void cleanup() {
        for(int textureID:textureIDs)
        glDeleteTextures(textureID);
    }

}
