package kn.end.engine.gr;
import kn.end.engine.IO.Keys;
import kn.end.engine.IO.actingObject;
import kn.end.engine.util.AABBI;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.NULL;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;

public class graph {
    public Window mainWin;
    private List<drawable> drawables=new ArrayList<>();
    public graph(AABBI sizepos,String name){
        GLFWErrorCallback.createPrint(System.err).set();

        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        mainWin = new Window(glfwCreateWindow(sizepos.px2,sizepos.py2, name, NULL, NULL));
        mainWin.sizepos=sizepos;
        if ( mainWin.ID == NULL )
            throw new RuntimeException("Failed to create the GLFW window");
        if (sizepos.px>0&&sizepos.py>0)
        glfwSetWindowPos(mainWin.ID,sizepos.px,sizepos.py);
        else
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(mainWin.ID, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    mainWin.ID,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );

            glfwMakeContextCurrent(mainWin.ID);
            // Enable v-sync
            glfwSwapInterval(1);

            // Make the window visible
            glfwShowWindow(mainWin.ID);
            GL.createCapabilities();
            glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

            glfwSetKeyCallback(mainWin.ID, (window, key, scancode, action, mods) -> {
                Keys.handle(window,key,scancode,action,mods);
            });
            Keys.addAction("quit",new Keys.Action(GLFW_KEY_END, GLFW_RELEASE,
                    GLFW_MOD_CONTROL | GLFW_MOD_ALT, new actingObject() {
                @Override
                public void act() {
                    glfwSetWindowShouldClose(mainWin.ID, true); // We will detect this in the rendering loop
                }
            }
            ));
        }
    }
    public void draw(){
        glClear(GL_COLOR_BUFFER_BIT |GL_DEPTH_BUFFER_BIT );
        drawables.forEach((self)->{
            self.draw();
        });
        glfwSwapBuffers(mainWin.ID);
        glfwPollEvents();
    }


}
