package deck.lachlan.robotics;

import deck.lachlan.robotics.domain.RobotController;

public class Main {
    public static void main(String[] args) {
        new RobotController()
            .readInstructions(System.in);
    }
}
