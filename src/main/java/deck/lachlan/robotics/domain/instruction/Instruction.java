package deck.lachlan.robotics.domain.instruction;

import deck.lachlan.robotics.domain.types.Table;

public interface Instruction {
    void attemptRobotOperation(Table table);
}
