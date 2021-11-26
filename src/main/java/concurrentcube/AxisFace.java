package concurrentcube;

public abstract class AxisFace {
    protected final Face face;
    protected final int size;

    public AxisFace(Face face) {
        this.face = face;
        this.size = face.size;
    }
}
