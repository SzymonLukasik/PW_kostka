package concurrentcube;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Face {
    protected final ArrayList<ArrayList<Color>> panels;
    protected final int size;

    public Face(int size, Color initial_color) {
        panels = Stream.generate(() ->
                Stream.generate(() -> initial_color)
                    .limit(size)
                    .collect(Collectors.toCollection(ArrayList::new))
            ).limit(size)
            .collect(Collectors.toCollection(ArrayList::new));
        this.size = size;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (ArrayList<Color> row : panels) {
            for (Color panel : row)
                s.append(panel);
            s.append("\n");
        }
        return s.toString();
    }
}
