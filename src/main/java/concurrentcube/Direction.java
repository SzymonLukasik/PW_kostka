package concurrentcube;

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

    @Override
    public String toString() {
        return Integer.valueOf(ordinal()).toString();
    }
}