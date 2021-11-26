package concurrentcube;

import java.util.ListIterator;

public class ReversedFace extends Face {

    public ReversedFace(int size, Color initial_color) {
        super(size, initial_color);
    }


    public String toString() {
        StringBuilder s = new StringBuilder();
        ListIterator<PanelSeries> row_iterator = panels.listIterator(size);
        while (row_iterator.hasPrevious()) {
            ListIterator<Color> panel_iterator = row_iterator.previous().getPanels().listIterator(size);
            while (panel_iterator.hasPrevious())
                s.append(panel_iterator.previous());
            s.append("\n");
        }
        return s.toString();
    }
}
