package deck.lachlan.robotics;

import deck.lachlan.robotics.domain.control.RobotController;
import deck.lachlan.robotics.domain.instruction.InstructionFactory;
import deck.lachlan.robotics.domain.io.Interpreter;
import deck.lachlan.robotics.domain.io.TrimmedIOReader;
import deck.lachlan.robotics.domain.types.Table;

public class Main {
    public static void main(String[] args) {
        new RobotController(
            new Table(5),
            new Interpreter(new InstructionFactory(System.out), new TrimmedIOReader())
        )
        .readInstructions(System.in);
    }
}
