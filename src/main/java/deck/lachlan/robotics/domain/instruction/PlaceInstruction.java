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
        String trimmed = instruction.trim();
        if (!trimmed.matches("^place\\s+\\d\\s*,\\s*\\d\\s*,\\s*\\w+$")) {
            return;
        }

        String[] coords = trimmed.split("\\s*,\\s*");

        int x = -1;
        if (coords[0].matches("^place\\s+\\d")) {
            x = Integer.parseInt(coords[0].split("\\s+")[1]);
        }

        final int y = Integer.parseInt(coords[1].trim());

        Optional<Compass> possibleCompass = Compass.findCompass(coords[2].trim());

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
