package kn.end.engine.gr.d2;

import kn.end.engine.gr.drawable;
import kn.end.engine.util.AABBI;

public class Object2D implements drawable {
    public AABBI sizepos;

    public Object2D(AABBI sizepos) {
        this.sizepos = sizepos;
    }

    @Override
    public void draw(Object[] args) {
    }
}
