package concurrentcube.axis.axisface;

import concurrentcube.face.Face;
import concurrentcube.face.PanelSeries;

public abstract class SideAxisFace extends AxisFace {
    public SideAxisFace(Face face) {
        super(face);
    }

    public abstract PanelSeries getPanelSeries(int index);
    public abstract PanelSeries switchPanelSeries(PanelSeries panel_series, int index);
    public abstract void reversePanelSeries(int index);
}
