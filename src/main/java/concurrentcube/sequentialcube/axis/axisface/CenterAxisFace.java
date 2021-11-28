package concurrentcube.sequentialcube.axis.axisface;

import concurrentcube.sequentialcube.face.Face;
import concurrentcube.sequentialcube.face.PanelSeries;
import concurrentcube.sequentialcube.enums.Rotation;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CenterAxisFace extends AxisFace {
    public CenterAxisFace(Face face) {
        super(face);
    }

    public void rotate(Rotation rotation) {
        if (rotation == Rotation.Clockwise)
            rotateClockwise();
        else
            rotateAntiClockwise();
    }

    private void rotateClockwise() {
        face.setPanels(IntStream.range(0, size)
            .mapToObj(face::getColumn)
            .collect(Collectors.toCollection(ArrayList::new)));
        for (PanelSeries row : face.getPanels())
            row.reverse();
    }

    private void rotateAntiClockwise() {
        face.setPanels(IntStream.iterate(size - 1, i -> i - 1).limit(size)
            .mapToObj(face::getColumn)
            .collect(Collectors.toCollection(ArrayList::new)));
    }
}
