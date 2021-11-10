package deck.lachlan.robotics.domain;

import java.util.ArrayList;
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
        List<Compass> items =  new ArrayList<>(List.of(values()));
        int startIndex = items.indexOf(this);

        // psuedo infinite list either direction
        items.addAll(List.of(values()));
        items.addAll(List.of(values()));

        startIndex += values().length;

        int endIndex = startIndex + direction;
        return items.get(endIndex);
    }
}
