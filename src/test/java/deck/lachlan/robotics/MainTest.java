package deck.lachlan.robotics;

import deck.lachlan.robotics.domain.control.RobotController;
import deck.lachlan.robotics.domain.instruction.InstructionFactory;
import deck.lachlan.robotics.domain.io.Interpreter;
import deck.lachlan.robotics.domain.io.TrimmedIOReader;
import deck.lachlan.robotics.domain.types.Table;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedConstruction;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MainTest {

    @Test
    @DisplayName("should initialise controller and read instructions from standard input")
    void shouldInitialiseControllerAndReadInstructionsFromStandardInput() {
        try (MockedConstruction<RobotController> mocked = mockConstruction(RobotController.class,
            (mock, context) -> {
                assertThat(context.arguments()).isEqualTo(List.of(
                    new Table(5),
                    new Interpreter(new InstructionFactory(System.out), new TrimmedIOReader())
                ));
            })) {

            Main.main(new String[0]);
            assertThat(mocked.constructed()).hasSize(1);
            verify(mocked.constructed().get(0)).readInstructions(System.in);
        }
    }
}