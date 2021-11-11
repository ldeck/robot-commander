package deck.lachlan.robotics.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class PlaceInstruction implements Instruction {
    private final String instruction;

    public PlaceInstruction(String instruction) {
        this.instruction = instruction;
    }

    @Override
    public void attemptRobotOperation(Table table) {

    }
}
