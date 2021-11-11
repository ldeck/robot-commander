package deck.lachlan.robotics.domain;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode
public class PlaceInstruction implements Instruction {
    private final String instruction;

    @Override
    public void attemptRobotOperation(Table table) {
        String[] coords = instruction.split(",");

        int x = Integer.parseInt(coords[0]);
        int y = Integer.parseInt(coords[1]);
        Compass compass = Compass.valueOf(coords[2].toUpperCase());

        Robot robot = new Robot(new Position(x, y), compass);
        table.tryPlaceRobot(robot);
    }
}
