package deck.lachlan.robotics.domain.types;

import lombok.Value;

@Value
public class Position {
    int x;
    int y;

    public boolean isPotentiallyValid() {
        return x >= 0 && y >= 0;
    }

    @Override
    public String toString() {
        return String.format("%s,%s", x, y);
    }

    public Position moved(Compass compass) {
        int newX = x;
        int newY = y;
        switch (compass) {
            case NORTH: newY++; break;
            case EAST: newX++; break;
            case SOUTH: newY--; break;
            default: newX--; break;
        }
        return new Position(newX, newY);
    }
}
