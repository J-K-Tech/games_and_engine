package kn.end.engine.IO;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

public class Keys {


    public static class Action{
        private actingObject actingobject;
        public int scancode;
        public boolean active=false;
        public Action(int key,actingObject actingobject) {
            this(key,GLFW_RELEASE,0,actingobject);
        }

        public int action;public int mods;

        public Action(int key, int action, int mods,actingObject actingobject) {
            this.actingobject=actingobject;
            this.scancode = key;
            this.action = action;
            this.mods = mods;
        }

    }


    private static Map<String, Action> actions=new HashMap<>();
    public static void handle(long window, int key, int scancode, int action, int mods){
        actions.forEach((name,action1)->{
            if (action1.active)
                if(scancode==action1.scancode)
                    if(action1.action==action)
                        if(mods==action1.mods)
                            action1.actingobject.act();
        });
    }
    public static void addAction(String name,Action k){
        actions.put(name,k);
    }

}
