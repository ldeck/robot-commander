package deck.lachlan.robotics.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum Compass {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public static Optional<Compass> findCompass(String name) {
        return Arrays.stream(values())
            .filter(compass -> compass.name().equalsIgnoreCase(name))
            .findFirst();
    }

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
