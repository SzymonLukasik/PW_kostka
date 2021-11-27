package concurrentcube;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Face {
    protected ArrayList<PanelSeries> panels;
    protected final int size;

    public Face(int size, Color initial_color) {
        panels = Stream.generate(() ->
                new PanelSeries(Stream.generate(() -> initial_color)
                    .limit(size)
                    .collect(Collectors.toCollection(ArrayList::new)))
            ).limit(size)
            .collect(Collectors.toCollection(ArrayList::new));
        this.size = size;
    }

    public PanelSeries getColumn(int index) {
        return new PanelSeries(IntStream.range(0, size)
            .mapToObj(row_idx -> panels.get(row_idx).getPanel(index))
            .collect(Collectors.toCollection(ArrayList::new)));
    }

    public StringBuilder toStringBuilder() {
        return panels.stream().map(PanelSeries::toStringBuilder)
            .reduce(new StringBuilder(), StringBuilder::append);
    }
}
