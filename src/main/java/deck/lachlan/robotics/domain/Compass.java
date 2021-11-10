package deck.lachlan.robotics.domain;

import java.util.List;

public enum Compass {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public Compass left() {
        int startIndex = List.of(values()).indexOf(this);
        if (startIndex == 0) {
            startIndex = values().length;
        }
        return values()[startIndex - 1];
    }
}
