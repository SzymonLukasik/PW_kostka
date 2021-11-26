package concurrentcube;

import java.util.ListIterator;

public class ColumnAxisFace extends SideAxisFace {
    public ColumnAxisFace(Face face) {
        super(face);
    }

    private int getColIndex(int index) {
        return size - index;
    }

    @Override
    public PanelSeries getPanelsSerie(int index) {
        int col_idx = index; //getColIndex(index);
        return face.getColumn(col_idx);
    }

    @Override
    public PanelSeries switchPanelsSerie(PanelSeries panels_serie, int index) {
        int col_idx = index; //getColIndex(index);
        ListIterator<PanelSeries> row_iterator = face.panels.listIterator();
        while (row_iterator.hasNext()) {
            int row_idx = row_iterator.nextIndex();
            PanelSeries row = row_iterator.next();
            Color temp = row.getPanel(col_idx);
            panels_serie.setPanel(row_idx, temp);
        }
        return panels_serie;
    }
}
