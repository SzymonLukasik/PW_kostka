package concurrentcube;

public enum Color {
    White("\u001b[48;5;231m"),
    Green("\u001b[48;5;47m"),
    Red("\u001b[48;5;160m"),
    Blue("\u001b[48;5;20m"),
    Orange("\u001b[48;5;202m"),
    Yellow("\u001b[48;5;226m"),
    Blank("");

    private final String background_color;

    private Color(String background_color) { this.background_color = background_color; }

    public static final String ANSI_RESET = "\u001b[0m";

    @Override
    public String toString() { return this != Blank ? Integer.valueOf(ordinal()).toString() : " "; }

    public String toPrettyString() { return background_color + " " + this + " " + ANSI_RESET; }
}
