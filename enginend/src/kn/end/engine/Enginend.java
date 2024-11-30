package kn.end.engine;

import kn.end.engine.gr.graph;
import kn.end.engine.util.AABBI;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

public class Enginend {

    public graph gr;
    public boolean shouldclose=false;
    public final boolean customclose;
    public Enginend(int w, int h){
        this(w,h,false,"");
    }
    public Enginend(int w, int h,String name){
        this(w,h,false,name);
    }
    public Enginend(){
        this(320,240,false,"");
    }
    public Enginend(String name){
        this(320,240,false,name);
    }
    public Enginend(int w, int h, boolean customclose){
        this(w,h,customclose,"");
    }
    public Enginend(int w, int h, boolean customclose,String name){
        this.customclose = customclose;
        gr=new graph(new AABBI(0,0,w,h).asSizePos(),name);
    }
    public void update(){
        gr.draw();
        if(!customclose)shouldclose=glfwWindowShouldClose(gr.mainWin.ID);
    }

    public static void setDebug(boolean val){
        if (!wasset)debug=val;
    }
    private static final boolean jvmNoOut=System.getProperty("noOut")!=null;
    private static boolean wasset=false;
    private static boolean debug=true;
    public static void print(String var){
        if (jvmNoOut)
            System.out.println(var);
    }
    public static void error(String var){
        System.err.println(var);
    }
    public static void debug(String var){
        if (debug&&jvmNoOut){
            System.out.println(var);
        }
    }

}
