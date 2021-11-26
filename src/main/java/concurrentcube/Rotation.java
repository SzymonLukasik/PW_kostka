package concurrentcube;

public enum Rotation {
    Clockwise,
    AntiClockwise;

    public static Rotation getRotation(Direction direction) {
        switch (direction) {
            case Up:
            case Left:
            case Front:
                return Clockwise;
            default:
                return AntiClockwise;
        }
    }
}
