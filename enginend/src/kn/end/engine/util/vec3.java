package kn.end.engine.util;
public class vec3 {
    float x;
    float y;
    float z;

    public vec3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static vec3 abs(vec3 val){
        val.x=java.lang.Math.abs(val.x);
        val.y=java.lang.Math.abs(val.y);
        val.z=java.lang.Math.abs(val.z);
        return val;
    }
}
