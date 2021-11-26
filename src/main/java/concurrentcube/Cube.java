package concurrentcube;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Cube {
    private final int size;
    private final EnumMap<Direction, Face> faces;

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
    }

    private Face initializeFace(Direction side) {
        return side !=  Direction.Bottom ?
            new Face(size, side.getInitialColor()) :
            new ReversedFace(size, side.getInitialColor());
    }

    public void rotate(int side, int layer) throws InterruptedException {
    }

    public String show() throws InterruptedException {
        return faces.values().stream().map(Face::toString).collect(Collectors.joining("\n"));
        //return faces.values().stream().reduce(new StringBuilder(), (s, face) -> s.append(face.toString())).toString();
    }
}