package concurrentcube;

public abstract class SideAxisFace extends AxisFace{
    public SideAxisFace(Face face) {
        super(face);

    }

    public abstract PanelSeries getPanelsSerie(int index);
    public abstract PanelSeries switchPanelsSerie(PanelSeries panels_serie, int index);
}
