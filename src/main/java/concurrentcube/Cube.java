package concurrentcube;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Cube {
    private final int size;
    private final EnumMap<Direction, Face> faces;
    private final Axis upAxis, leftAxis, frontAxis;
    public Cube(int size,
                BiConsumer<Integer, Integer> beforeRotation,
                BiConsumer<Integer, Integer> afterRotation,
                Runnable beforeShowing,
                Runnable afterShowing) {
        this.size = size;
        faces = Arrays.stream(Direction.values())
            .collect(Collectors.toMap(
                Function.identity(),
                this::initializeFace,
                (l, r) -> {throw new IllegalArgumentException();}, // This will not happen.
                () -> new EnumMap<>(Direction.class)));
        upAxis = Axis.getUpAxis(size, faces);
        leftAxis = Axis.getLeftAxis(size, faces);
        frontAxis = Axis.getFrontAxis(size, faces);
    }

    private Face initializeFace(Direction side) {
        return side !=  Direction.Bottom ?
            new Face(size, side.getInitialColor()) :
            new ReversedFace(size, side.getInitialColor());
    }

    private Axis getAxis(Direction direction) {
        switch (direction) {
            case Up:
            case Bottom:
                return upAxis;
            case Left:
            case Right:
                return leftAxis;
            default:
                return frontAxis;
        }
    }

    private int getIndex(Rotation rotation, int layer) {
        if (rotation == Rotation.AntiClockwise)
            return size - 1 - layer;
        return layer;
    }

    public void rotate(int side, int layer) throws InterruptedException {
        Direction direction = Direction.getDirection(side);
        Rotation rotation = Rotation.getRotation(direction);
        Axis axis = getAxis(direction);
        int index = getIndex(rotation, layer);
        axis.rotate(rotation, index);
    }

    public String show() throws InterruptedException {
        return faces.values().stream().map(Face::toString).collect(Collectors.joining("\n"));
        //return faces.values().stream().reduce(new StringBuilder(), (s, face) -> s.append(face.toString())).toString();
    }
}