package concurrentcube;

public enum Color {
    White,
    Green,
    Red,
    Blue,
    Orange,
    Yellow;

    @Override
    public String toString() {
        return Integer.valueOf(ordinal()).toString();
    }
}
