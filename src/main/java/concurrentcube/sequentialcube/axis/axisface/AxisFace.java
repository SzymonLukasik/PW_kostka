package concurrentcube.sequentialcube.axis.axisface;

import concurrentcube.sequentialcube.face.Face;

public abstract class AxisFace {
    protected final Face face;
    protected final int size;

    public AxisFace(Face face) {
        this.face = face;
        this.size = face.getSize();
    }
}
