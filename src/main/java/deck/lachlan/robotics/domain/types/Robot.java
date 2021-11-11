package deck.lachlan.robotics.domain.types;

import lombok.Value;

import java.util.Optional;

@Value
public class Robot {
    Position position;
    Compass compass;

    public static Robot unplacedRobot() {
        return new Robot(
            new Position(-1, -1),
            null
        );
    }

    public Optional<String> getBearingsIfAvailable() {
        if (compass == null || !position.isPotentiallyValid()) {
            return Optional.empty();
        }
        return Optional.of(String.format("%s,%s", position, compass));
    }

    public Robot moved() {
        Position newPosition = position.moved(compass);
        return new Robot(newPosition, compass);
    }

    public Robot rotated(Rotate rotation) {
        return new Robot(position, compass.rotated(rotation));
    }
}
