package deck.lachlan.robotics.domain.instruction;

import deck.lachlan.robotics.domain.types.Robot;
import deck.lachlan.robotics.domain.types.Rotate;
import deck.lachlan.robotics.domain.types.Table;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@RequiredArgsConstructor
public class RotateInstruction implements Instruction {
    private final Rotate rotation;

    @Override
    public void attemptRobotOperation(Table table) {
        table.findRobot().ifPresent(robot -> {
            Robot turnedRobot = robot.rotated(rotation);
            table.tryPlaceRobot(turnedRobot);
        });
    }
}
