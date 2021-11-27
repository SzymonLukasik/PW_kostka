package concurrentcube;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CenterAxisFace extends AxisFace {
    public CenterAxisFace(Face face) {
        super(face);
    }

    void rotate(Rotation rotation) {

    }

    void rotateClockwise() {
        face.panels = IntStream.range(0, size)
            .mapToObj(col_idx -> face.getColumn(col_idx))
            .collect(Collectors.toCollection(ArrayList::new));
        for (PanelSeries row : face.panels)
            row.reverse();
    }

    void rotateAntiClockwise() {
        face.panels = IntStream.iterate(size - 1, i -> i - 1).limit(size)
            .mapToObj(col_idx -> face.getColumn(col_idx))
            .collect(Collectors.toCollection(ArrayList::new));
    }
}
