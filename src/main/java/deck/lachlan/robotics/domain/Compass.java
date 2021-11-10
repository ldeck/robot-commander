package deck.lachlan.robotics.domain;

import java.util.List;

public enum Compass {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public Compass left() {
        return moved(-1);
    }

    public Compass right() {
        return moved(1);
    }

    private Compass moved(int direction) {
        int startIndex = currentIndex();
        int endIndex = startIndex + direction;
        if (endIndex < 0) {
            endIndex = values().length - 1;
        }
        else if (endIndex == values().length) {
            endIndex = 0;
        }
        return values()[endIndex];
    }

    private int currentIndex() {
        return List.of(values()).indexOf(this);
    }
}
