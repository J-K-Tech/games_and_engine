package kn.end.engine.gr.d2;

import kn.end.engine.util.AABBI;

import static org.lwjgl.opengl.GL11.*;

public class colorRect extends Object2D{
    float r,g,b;
    public boolean rainbow=false;
    public colorRect(AABBI sizepos,float []rgb) {
        super(sizepos);
        r=rgb[0];g=rgb[1];b=rgb[2];
    }
    public colorRect(AABBI sizepos) {
        super(sizepos);
        rainbow=true;
    }

    @Override
    public void draw(Object[] args) {
        glBegin(GL_QUADS);

        if (rainbow){
        glColor3f(1.0f, 0.0f, 0.0f); glTexCoord2f(0.0f, 0.0f); glVertex2f(-1.0f, -1.0f);
        glColor3f(0.0f, 1.0f, 0.0f); glTexCoord2f(1.0f, 0.0f); glVertex2f(1.0f, -1.0f);
        glColor3f(0.0f, 0.0f, 1.0f); glTexCoord2f(1.0f, 1.0f); glVertex2f(1.0f, 1.0f);
        glColor3f(1.0f, 1.0f, 0.0f); glTexCoord2f(0.0f, 1.0f); glVertex2f(-1.0f, 1.0f);
        }
        else{
        glColor3f(r, g, b); glTexCoord2f(0.0f, 0.0f); glVertex2f(-1.0f, -1.0f);
        glColor3f(r, g, b); glTexCoord2f(1.0f, 0.0f); glVertex2f(1.0f, -1.0f);
        glColor3f(r, g, b); glTexCoord2f(1.0f, 1.0f); glVertex2f(1.0f, 1.0f);
        glColor3f(r, g, b); glTexCoord2f(0.0f, 1.0f); glVertex2f(-1.0f, 1.0f);
        }

        glEnd();
    }
}
