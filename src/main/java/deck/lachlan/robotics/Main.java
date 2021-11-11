package deck.lachlan.robotics;

import deck.lachlan.robotics.domain.InstructionFactory;
import deck.lachlan.robotics.domain.Interpreter;
import deck.lachlan.robotics.domain.RobotController;
import deck.lachlan.robotics.domain.Table;
import deck.lachlan.robotics.domain.TrimmedIOReader;

public class Main {
    public static void main(String[] args) {
        new RobotController(
            new Table(5),
            new Interpreter(new InstructionFactory(System.out), new TrimmedIOReader())
        )
        .readInstructions(System.in);
    }
}
