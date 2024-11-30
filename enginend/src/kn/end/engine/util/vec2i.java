package kn.end.engine.util;

public class vec2i {
    int x;
    int y;

    public vec2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static vec2i abs(vec2i val){
        val.x=java.lang.Math.abs(val.x);
        val.y=java.lang.Math.abs(val.y);
        return val;
    }
}
