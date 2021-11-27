package concurrentcube.face;

import concurrentcube.Color;
import concurrentcube.face.Face;
import concurrentcube.face.PanelSeries;

import java.util.ListIterator;

public class VerticallyReversedFace extends Face {

    public VerticallyReversedFace(int size, Color initial_color) {
        super(size, initial_color);
    }

    @Override
    public StringBuilder toStringBuilder() {
        StringBuilder s = new StringBuilder();
        ListIterator<PanelSeries> row_iterator = panels.listIterator(size);
        while (row_iterator.hasPrevious()) {
            for (Color panel : row_iterator.previous().getPanels())
                s.append(panel);
            s.append("\n");
        }
        return s;
    }

    @Override
    public StringBuilder getRowPrettyStringBuilder(int index) {
        return panels.get(size - 1 - index).toPrettyStringBuilder();
    }
}
