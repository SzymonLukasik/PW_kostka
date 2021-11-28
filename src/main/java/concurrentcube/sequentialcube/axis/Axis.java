package concurrentcube.sequentialcube.axis;

import concurrentcube.sequentialcube.axis.axisface.CenterAxisFace;
import concurrentcube.sequentialcube.axis.axisface.ColumnAxisFace;
import concurrentcube.sequentialcube.axis.axisface.RowAxisFace;
import concurrentcube.sequentialcube.axis.axisface.SideAxisFace;
import concurrentcube.sequentialcube.enums.Direction;
import concurrentcube.sequentialcube.enums.Rotation;
import concurrentcube.sequentialcube.face.Face;

import java.util.EnumMap;
import java.util.stream.Stream;

public class Axis {
    private final int size;
    private final CenterAxisFace frontFace, backFace;
    private final SideAxisFace upFace, rightFace, bottomFace, leftFace;

    public Axis(int size,
                CenterAxisFace frontFace, CenterAxisFace backFace,
                SideAxisFace upFace, SideAxisFace rightFace,
                SideAxisFace bottomFace, SideAxisFace leftFace) {
        this.size = size;
        this.frontFace = frontFace;
        this.backFace = backFace;
        this.upFace = upFace;
        this.rightFace = rightFace;
        this.bottomFace = bottomFace;
        this.leftFace = leftFace;
    }

    public static Axis getUpAxis(int size, EnumMap<Direction, Face> faces) {
        return new Axis(size,
            new CenterAxisFace(faces.get(Direction.Up)),
            new CenterAxisFace(faces.get(Direction.Bottom)),
            new RowAxisFace(faces.get(Direction.Back)),
            new RowAxisFace(faces.get(Direction.Right)),
            new RowAxisFace(faces.get(Direction.Front)),
            new RowAxisFace(faces.get(Direction.Left)));
    }

    public static Axis getLeftAxis(int size, EnumMap<Direction, Face> faces) {
        return new Axis(size,
            new CenterAxisFace(faces.get(Direction.Left)),
            new CenterAxisFace(faces.get(Direction.Right)),
            new ColumnAxisFace(faces.get(Direction.Up)),
            new ColumnAxisFace(faces.get(Direction.Front)),
            new ColumnAxisFace(faces.get(Direction.Bottom)),
            new ColumnAxisFace(faces.get(Direction.Back)));
    }

    public static Axis getBackAxis(int size, EnumMap<Direction, Face> faces) {
        return new Axis(size,
            new CenterAxisFace(faces.get(Direction.Back)),
            new CenterAxisFace(faces.get(Direction.Front)),
            new RowAxisFace(faces.get(Direction.Up)),
            new ColumnAxisFace(faces.get(Direction.Left)),
            new RowAxisFace(faces.get(Direction.Bottom)),
            new ColumnAxisFace(faces.get(Direction.Right)));
    }


    public Stream<SideAxisFace> getRotationFacesStream(Rotation rotation) {
        if (rotation == Rotation.Clockwise)
            return Stream.of(rightFace, bottomFace, leftFace, upFace);
        else
            return Stream.of(leftFace, bottomFace, rightFace, upFace);
    }

    public void rotateSeries(Rotation rotation, int index) {
        getRotationFacesStream(rotation)
            .reduce(
                upFace.getPanelSeries(index),
                (panelsSeries, sideAxisFace) -> sideAxisFace.switchPanelSeries(panelsSeries, index),
                (oldPanelSeries, newPanelSeries) -> newPanelSeries);
        if (rotation == Rotation.Clockwise) {
            upFace.reversePanelSeries(index);
            bottomFace.reversePanelSeries(index);
        } else {
            leftFace.reversePanelSeries(index);
            rightFace.reversePanelSeries(index);
        }
    }

    public void rotate(Rotation rotation, int index) {
        if (index == 0)
            frontFace.rotate(rotation);
        else if (index == size - 1)
            backFace.rotate(rotation);
        rotateSeries(rotation, index);
    }
}
