package deck.lachlan.robotics.domain;

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
        this.robot = robot;
    }
}
