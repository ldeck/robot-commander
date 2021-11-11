package deck.lachlan.robotics.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PlaceInstructionTest {

    @Mock Table table;

    @ParameterizedTest
    @CsvSource({
        "0,0,north",
        "0,1,south",
        "1,1,west",
        "2,1,east",
    })
    @DisplayName("should place robot for valid positions")
    void shouldPlaceRobotForValidPositions(int x, int y, String compass) {
        String rawInstruction = String.format("place %s,%s,%s", x, y, compass);
        PlaceInstruction instruction = new PlaceInstruction(rawInstruction);

        instruction.attemptRobotOperation(table);

        Robot expectedRobot = new Robot(new Position(x, y), Compass.valueOf(compass.toUpperCase()));
        verify(table).tryPlaceRobot(expectedRobot);
    }

}