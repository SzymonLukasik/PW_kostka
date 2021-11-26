package concurrentcube;

import java.util.EnumMap;
import java.util.Map;

public enum Side {
    Up(Color.White),
    Left(Color.Green),
    Front(Color.Red),
    Right(Color.Blue),
    Back(Color.Orange),
    Bottom(Color.Yellow);

    private final Color initialColor;

    Side(Color color) {
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
