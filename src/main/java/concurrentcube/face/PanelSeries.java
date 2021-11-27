package concurrentcube.face;

import concurrentcube.Color;

import java.util.ArrayList;
import java.util.Collections;

public class PanelSeries {
    private ArrayList<concurrentcube.Color> panels;

    public PanelSeries(ArrayList<concurrentcube.Color> panels) {
        this.panels = panels;
    }

    public ArrayList<concurrentcube.Color> getPanels() {
        return panels;
    }

    public concurrentcube.Color getPanel(int index) {
        return panels.get(index);
    }

    public void setPanel(int index, concurrentcube.Color color) {
        panels.set(index, color);
    }

    public void reverse() {
        Collections.reverse(panels);
    }

    public static void swap(PanelSeries s1, PanelSeries s2) {
        ArrayList<concurrentcube.Color> temp = s1.panels;
        s1.panels = s2.panels;
        s2.panels = temp;
    }

    public StringBuilder toStringBuilder() {
        return panels.stream().collect(
            StringBuilder::new,
            StringBuilder::append,
            StringBuilder::append)
            .append("\n");
    }

    public StringBuilder toPrettyStringBuilder() {
        return panels.stream()
            .map(Color::toPrettyString)
            .collect(
                StringBuilder::new,
                StringBuilder::append,
                StringBuilder::append)
            .append(" ");
    }
}
