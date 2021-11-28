package concurrentcube.sequentialcube;

import concurrentcube.sequentialcube.enums.Color;
import concurrentcube.sequentialcube.enums.Direction;
import concurrentcube.sequentialcube.enums.Rotation;
import concurrentcube.sequentialcube.axis.Axis;
import concurrentcube.sequentialcube.face.Face;
import concurrentcube.sequentialcube.face.HorizontallyReversedFace;
import concurrentcube.sequentialcube.face.VerticallyReversedFace;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SequentialCube {
    private final int size;
    private final EnumMap<Direction, Face> faces;
    private final Axis upAxis, leftAxis, frontAxis;
    public SequentialCube(int size) {
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
        switch (side) {
            case Up:
            case Left:
            case Front:
                return new Face(size, side.getInitialColor());
            case Right:
            case Back:
                return new HorizontallyReversedFace(size, side.getInitialColor());
            default: // Bottom
                return new VerticallyReversedFace(size, side.getInitialColor());
        }
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

    public void rotateSequential(int side, int layer)  {
        Direction direction = Direction.getDirection(side);
        Rotation rotation = Rotation.getRotation(direction);
        Axis axis = getAxis(direction);
        axis.rotate(rotation, layer);
    }

    public String showSequential() {
        System.out.println(toPrettyStringBuilder());
        return toStringBuilder().toString();
    }

    public StringBuilder toStringBuilder() {
        return faces.values().stream().map(Face::toStringBuilder)
            .reduce(new StringBuilder(), StringBuilder::append);
        //return faces.values().stream().reduce(new StringBuilder(), (s, face) -> s.append(face.toString())).toString();
    }

    public StringBuilder toPrettyStringBuilder() {
        Face blank_face = new Face(size, Color.Blank);
        Stream<Supplier<Stream<Face>>> levels_stream = Stream.of(
            () -> Stream.of(blank_face, faces.get(Direction.Up)),
            () -> Stream.of(faces.get(Direction.Left), faces.get(Direction.Front),
                faces.get(Direction.Right), faces.get(Direction.Back)),
            () -> Stream.of(blank_face, faces.get(Direction.Bottom)));

        return levels_stream.map(faces_stream -> IntStream.range(0, size).mapToObj(
            index -> faces_stream.get().map(face -> face.getRowPrettyStringBuilder(index))
                .reduce(new StringBuilder(), StringBuilder::append))
            .reduce(new StringBuilder(), (collector, row_string_builder) -> collector.append(row_string_builder).append("\n")))
            .reduce(new StringBuilder(), StringBuilder::append);
    }
}