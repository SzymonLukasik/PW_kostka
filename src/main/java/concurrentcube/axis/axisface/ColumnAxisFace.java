package concurrentcube.axis.axisface;

import concurrentcube.Color;
import concurrentcube.face.Face;
import concurrentcube.face.PanelSeries;

import java.util.ListIterator;

public class ColumnAxisFace extends SideAxisFace {
    public ColumnAxisFace(Face face) {
        super(face);
    }

    @Override
    public PanelSeries getPanelSeries(int index) {
        return face.getColumn(index);
    }

    @Override
    public PanelSeries switchPanelSeries(PanelSeries panel_series, int index) {
        ListIterator<PanelSeries> row_iterator = face.getPanels().listIterator();
        while (row_iterator.hasNext()) {
            int row_idx = row_iterator.nextIndex();
            PanelSeries row = row_iterator.next();
            Color temp = row.getPanel(index);
            row.setPanel(index, panel_series.getPanel(row_idx));
            panel_series.setPanel(row_idx, temp);
        }
        return panel_series;
    }

    @Override
    public void reversePanelSeries(int index) {
        PanelSeries reversed = getPanelSeries(index);
        reversed.reverse();
        switchPanelSeries(reversed, index);
    }
}
