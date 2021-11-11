package deck.lachlan.robotics.domain.types;

import lombok.EqualsAndHashCode;

import java.util.Optional;

@EqualsAndHashCode
public class Table {
    private final int width;
    private Robot robot;

    public Table(int width) {
        this.width = width;
    }

    public Optional<Robot> findRobot() {
        return Optional.ofNullable(robot);
    }

    public void tryPlaceRobot(Robot robot) {
        if (robot.getCompass() != null
            && robot.getPosition().isPotentiallyValid()
            && robot.getPosition().getX() < width
            && robot.getPosition().getY() < width
        ) {
            this.robot = robot;
        }
    }
}
