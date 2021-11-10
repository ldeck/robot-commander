package deck.lachlan.robotics.domain.instruction;

import deck.lachlan.robotics.domain.types.Robot;
import deck.lachlan.robotics.domain.types.Table;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class MoveInstruction implements Instruction {
    @Override
    public void attemptRobotOperation(Table table) {
        table.findRobot().ifPresent(robot -> {
            Robot movedRobot = robot.moved();
            table.tryPlaceRobot(movedRobot);
        });
    }
}
