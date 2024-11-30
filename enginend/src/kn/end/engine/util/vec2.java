package kn.end.engine.util;
public class vec2 {
    float x;
    float y;

    public vec2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public static vec2 abs(vec2 val){
        val.x=java.lang.Math.abs(val.x);
        val.y=java.lang.Math.abs(val.y);
        return val;
    }
}
