package deck.lachlan.robotics;

import deck.lachlan.robotics.domain.InstructionFactory;
import deck.lachlan.robotics.domain.Interpreter;
import deck.lachlan.robotics.domain.Robot;
import deck.lachlan.robotics.domain.RobotController;
import deck.lachlan.robotics.domain.Table;
import deck.lachlan.robotics.domain.TrimmedIOReader;

public class Main {
    public static void main(String[] args) {
        new RobotController(
            new Table(5, new Robot()),
            new Interpreter(new InstructionFactory(), new TrimmedIOReader())
        )
        .readInstructions(System.in);
    }
}
