package concurrentcube.axis.axisface;

import concurrentcube.face.Face;
import concurrentcube.face.PanelSeries;

public class RowAxisFace extends SideAxisFace {
    public RowAxisFace(Face face) {
        super(face);
    }

    @Override
    public PanelSeries getPanelSeries(int index) {
        return face.getPanels().get(index);
    }

    @Override
    public PanelSeries switchPanelSeries(PanelSeries panel_series, int index) {
        PanelSeries.swap(panel_series, face.getPanels().get(index));
        return panel_series;
    }

    @Override
    public void reversePanelSeries(int index) {
        face.getPanels().get(index).reverse();
    }
}
