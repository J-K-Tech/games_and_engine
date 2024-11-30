package kn.end.engine.util;

import static java.lang.Math.abs;
public class AABBI {
    public int px;
    public int py;
    public int px2;
    public int py2;
    private boolean issizepos=false;

    /**
     * @param px X position
     * @param py Y position
     * @param px2 X second position / width
     * @param py2 Y second position / height
     */
    public AABBI(int px, int py, int px2, int py2){
        this.px = px;
        this.py = py;
        this.px2 = px2;
        this.py2 = py2;
    }
    public AABBI asSizePos(){
        this.px2= Math.abs(px2);
        this.py2= Math.abs(py2);
        issizepos=true;
        return this;
    }
    public vec2i size(){
        return vec2i.abs(new vec2i(px-px2,py-py2));
    }
    public static AABBI sum(AABBI aabbi,AABBI aabbi2){
        return new AABBI(
                aabbi2.px+aabbi.px,aabbi2.py+aabbi.py,
                aabbi2.px2+aabbi.px2,aabbi2.py2+aabbi.py2
        );
    }
    public static AABBI div(AABBI aabbi,AABBI aabbi2){
        return new AABBI(
                aabbi2.px/aabbi.px,aabbi2.py/aabbi.py,
                aabbi2.px2/aabbi.px2,aabbi2.py2/aabbi.py2
        );
    }
    public static AABBI shift(AABBI aabbi,short shift){
        if (shift<0){
            shift= (short) -shift;
        return new AABBI(
                aabbi.px>>shift,aabbi.py>>shift,
                aabbi.px2>>shift,aabbi.py2>>shift
        );
        }else {
            return new AABBI(
                    aabbi.px<<shift,aabbi.py<<shift,
                    aabbi.px2<<shift,aabbi.py2<<shift

            );
        }
    }
    public static AABBI abs(AABBI aabbi){
        return new AABBI(
                Math.abs(aabbi.px), Math.abs(aabbi.py),
                Math.abs(aabbi.px2), Math.abs(aabbi.py2)
        );
    }
}
