package concurrentcube.sequentialcube.face;

import concurrentcube.sequentialcube.enums.Color;

import java.util.ArrayList;
import java.util.Collections;

public class PanelSeries {
    private ArrayList<Color> panels;

    public PanelSeries(ArrayList<Color> panels) {
        this.panels = panels;
    }

    public ArrayList<Color> getPanels() {
        return panels;
    }

    public Color getPanel(int index) {
        return panels.get(index);
    }

    public void setPanel(int index, Color color) {
        panels.set(index, color);
    }

    public void reverse() {
        Collections.reverse(panels);
    }

    public static void swap(PanelSeries s1, PanelSeries s2) {
        ArrayList<Color> temp = s1.panels;
        s1.panels = s2.panels;
        s2.panels = temp;
    }

    public StringBuilder toStringBuilder() {
        return panels.stream().collect(
            StringBuilder::new,
            StringBuilder::append,
            StringBuilder::append);
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
