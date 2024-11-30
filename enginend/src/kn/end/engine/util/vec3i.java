package kn.end.engine.util;

public class vec3i {
    int x;
    int y;
    int z;

    public vec3i(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static vec3i abs(vec3i val){
        val.x=java.lang.Math.abs(val.x);
        val.y=java.lang.Math.abs(val.y);
        val.z=java.lang.Math.abs(val.z);
        return val;
    }
}
