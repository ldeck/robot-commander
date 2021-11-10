package deck.lachlan.robotics.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class Robot {
    private Position position;

    public Robot() {
        this.position = new Position(-1, -1);
    }
}
