package deck.lachlan.robotics.domain.instruction;

import deck.lachlan.robotics.domain.types.Compass;
import deck.lachlan.robotics.domain.types.Position;
import deck.lachlan.robotics.domain.types.Robot;
import deck.lachlan.robotics.domain.types.Table;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
@EqualsAndHashCode
public class PlaceInstruction implements Instruction {
    private final String instruction;

    @Override
    public void attemptRobotOperation(Table table) {
        String[] coords = instruction.split(",");

        int x = -1;
        if (coords[0].matches("^place \\d")) {
            x = Integer.parseInt(coords[0].split(" ")[1]);
        }

        final int y = Integer.parseInt(coords[1]);

        Optional<Compass> possibleCompass = Compass.findCompass(coords[2]);

        int finalX = x;
        possibleCompass.ifPresent(compass -> {
            final Robot robot = new Robot(
                new Position(
                    finalX,
                    y
                ),
                compass
            );
            table.tryPlaceRobot(robot);
        });
    }
}
