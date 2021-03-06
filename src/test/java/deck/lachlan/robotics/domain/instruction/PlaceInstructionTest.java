package deck.lachlan.robotics.domain.instruction;

import deck.lachlan.robotics.domain.types.Compass;
import deck.lachlan.robotics.domain.types.Position;
import deck.lachlan.robotics.domain.types.Robot;
import deck.lachlan.robotics.domain.types.Table;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class PlaceInstructionTest {

    @Mock
    Table table;

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

    @ParameterizedTest
    @CsvSource({
        "0,0,north",
        "0,1,south",
        "1,1,west",
        "2,1,east",
    })
    @DisplayName("should be resilient to whitespace")
    void shouldBeResilientToWhitespace(int x, int y, String compass) {
        String rawInstruction = String.format(" place  %s , %s , %s ", x, y, compass);
        PlaceInstruction instruction = new PlaceInstruction(rawInstruction);

        instruction.attemptRobotOperation(table);

        Robot expectedRobot = new Robot(new Position(x, y), Compass.valueOf(compass.toUpperCase()));
        verify(table).tryPlaceRobot(expectedRobot);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "place",
        "start 1,2,west",
        "place 1,2,3,north",
        "place 1,2,sou-east"
    })
    @DisplayName("ignores ill-formatted placement instructions")
    void ignoresIllFormattedPlacementInstructions(String rawInstruction) {
        PlaceInstruction instruction = new PlaceInstruction(rawInstruction);

        instruction.attemptRobotOperation(table);

        verifyNoMoreInteractions(table);
    }
}