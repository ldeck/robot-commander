package deck.lachlan.robotics.domain;

import lombok.Value;

@Value
public class Position {
    int x;
    int y;

    public boolean isValid() {
        return x >= 0 && y >= 0;
    }

    @Override
    public String toString() {
        return String.format("%s,%s", x, y);
    }
}
