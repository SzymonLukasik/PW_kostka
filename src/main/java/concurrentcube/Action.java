package concurrentcube;

import concurrentcube.sequentialcube.enums.Direction;

public enum Action {
    UpAxis,
    LeftAxis,
    FrontAxis,
    Show;

    public static Action getAxisAction(Direction direction) {
        switch (direction) {
            case Up:
            case Bottom:
                return UpAxis;
            case Left:
            case Right:
                return LeftAxis;
            default:
                return FrontAxis;
        }
    }

    public boolean isAxisAction() {
        return this  != Show;
    }
}