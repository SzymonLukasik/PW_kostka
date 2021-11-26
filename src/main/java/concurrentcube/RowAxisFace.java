package concurrentcube;

public class RowAxisFace extends SideAxisFace {
    public RowAxisFace(Face face) {
        super(face);
    }

    @Override
    public PanelSeries getPanelsSerie(int index) {
        return face.panels.get(index);
    }

    @Override
    public PanelSeries switchPanelsSerie(PanelSeries panels_serie, int index) {
        PanelSeries.swap(panels_serie, face.panels.get(index));
        return panels_serie;
    }
}
