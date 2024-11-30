package kn.end.engine.gr.d2;

import kn.end.engine.gr.core.Shader;
import kn.end.engine.gr.drawable;
import kn.end.engine.util.AABBI;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import static kn.end.engine.gr.graph.mainWin;
import static org.lwjgl.opengl.EXTFramebufferObject.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.*;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL20.*;
public class Viewport extends Object2D{
    public int texture;
    private int framebuffer;
    private int depthBuffer;
    public Shader shaderProgram=null;
    private int modelLoc, viewLoc, projectionLoc, textureLoc;
    public Viewport(AABBI sizepos) {
        super(sizepos);
    }
    public void init() {
        framebuffer = glGenFramebuffersEXT();
        if (framebuffer == 0) {
            throw new RuntimeException("Failed to generate framebuffer");
        }

        texture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texture);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, sizepos.px2, sizepos.py2, 0, GL_RGBA, GL_UNSIGNED_BYTE, (ByteBuffer) null);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

        depthBuffer = glGenRenderbuffersEXT();
        glBindRenderbufferEXT(GL_RENDERBUFFER_EXT, depthBuffer);
        glRenderbufferStorageEXT(GL_RENDERBUFFER_EXT, GL_DEPTH_COMPONENT, sizepos.px2, sizepos.py2);

        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, framebuffer);
        glFramebufferTexture2DEXT(GL_FRAMEBUFFER_EXT, GL_COLOR_ATTACHMENT0_EXT, GL_TEXTURE_2D, texture, 0);
        glFramebufferRenderbufferEXT(GL_FRAMEBUFFER_EXT, GL_DEPTH_ATTACHMENT_EXT, GL_RENDERBUFFER_EXT, depthBuffer);

        int status = glCheckFramebufferStatusEXT(GL_FRAMEBUFFER_EXT);
        if (status != GL_FRAMEBUFFER_COMPLETE_EXT) {
            throw new RuntimeException("Framebuffer is not complete: " + status);
        }

        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
    }


    @Override
    public void draw(Object[] args) {
        try {
            glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, framebuffer);
            int status = glCheckFramebufferStatusEXT(GL_FRAMEBUFFER_EXT);
            if (status != GL_FRAMEBUFFER_COMPLETE_EXT) {
                throw new RuntimeException("Framebuffer is not complete: " + status);
            }

            glViewport(0, 0, sizepos.px2, sizepos.py2);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            glEnable(GL_TEXTURE_2D);

            ((List<drawable>) args[0]).forEach((d) -> {
                d.draw(null);
            });

            glDisable(GL_TEXTURE_2D);
            glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
            glViewport(0, 0, mainWin.sizepos.px2, mainWin.sizepos.py2);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            shader();
            glBindTexture(GL_TEXTURE_2D, texture);
            glEnable(GL_TEXTURE_2D);

            glBegin(GL_QUADS);

            glTexCoord2f(0.0f, 0.0f);
            glVertex2f(-1.0f, -1.0f);
            glTexCoord2f(1.0f, 0.0f);
            glVertex2f(1.0f, -1.0f);
            glTexCoord2f(1.0f, 1.0f);
            glVertex2f(1.0f, 1.0f);
            glTexCoord2f(0.0f, 1.0f);
            glVertex2f(-1.0f, 1.0f);

            glEnd();

            glDisable(GL_TEXTURE_2D);

            int error = glGetError();
            if (error != GL_NO_ERROR) {
                System.err.println("OpenGL Error after rendering: " + error);
            }

        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }




    private void shader() {
        if (shaderProgram != null) {
            shaderProgram.use();

            textureLoc = GL20.glGetUniformLocation(shaderProgram.getProgramID(), "texture0");

            if ( textureLoc == -1 ) {
                System.err.println("cannot find: "+
                        ((modelLoc == -1)?"model ":"")+
                        ((viewLoc == -1)?"view ":"")+
                        ((projectionLoc == -1)?"projection ":"")+
                ((textureLoc == -1)?"texture ":"")
                );
                return;
            }

            float[] mtx = new float[]{
            };

            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, texture);
            GL20.glUniform1i(textureLoc, 0);

            String infoLog = glGetProgramInfoLog(shaderProgram.getProgramID());
            if (infoLog!="")
            System.err.println(infoLog);
        }
    }

}
