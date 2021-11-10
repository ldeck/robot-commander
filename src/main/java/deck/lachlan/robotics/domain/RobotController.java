package deck.lachlan.robotics.domain;

import lombok.SneakyThrows;

import java.io.InputStream;

public class RobotController {

    @SneakyThrows
    public void readInstructions(InputStream io) {
        io.available();
    }

    public Position position() {
        return new Position(0, 0);
    }
}
