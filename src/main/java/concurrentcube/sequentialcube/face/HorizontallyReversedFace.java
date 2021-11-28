package concurrentcube.sequentialcube.face;

import concurrentcube.sequentialcube.enums.Color;

import java.util.ListIterator;

public class HorizontallyReversedFace extends Face {
    public HorizontallyReversedFace(int size, Color initial_color) {
        super(size, initial_color);
    }

    @Override
    public StringBuilder toStringBuilder() {
        StringBuilder s = new StringBuilder();
        for (PanelSeries row : panels) {
            ListIterator<Color> panel_iterator = row.getPanels().listIterator(size);
            while (panel_iterator.hasPrevious())
                s.append(panel_iterator.previous());
        }
        return s;
    }

    @Override
    public StringBuilder getRowPrettyStringBuilder(int index) {
        StringBuilder s = new StringBuilder();
        ListIterator<Color> panel_iterator = panels.get(index).getPanels().listIterator(size);
        while (panel_iterator.hasPrevious())
            s.append(panel_iterator.previous().toPrettyString());
        s.append(" ");
        return s;
    }
}
