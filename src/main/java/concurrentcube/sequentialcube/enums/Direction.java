package concurrentcube.sequentialcube.enums;

public enum Direction {
    Up(Color.White),
    Left(Color.Green),
    Front(Color.Red),
    Right(Color.Blue),
    Back(Color.Orange),
    Bottom(Color.Yellow);

    private final Color initialColor;

    Direction(Color color) {
        initialColor = color;
    }

    public Color getInitialColor() {
        return initialColor;
    }

    public boolean isFrontBack() {
        return this == Direction.Front || this == Direction.Back;
    }

    public static Direction getDirection(int index) {
        return values()[index];
    }
    @Override
    public String toString() {
        return Integer.valueOf(ordinal()).toString();
    }
}
