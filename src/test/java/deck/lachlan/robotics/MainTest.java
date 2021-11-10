package deck.lachlan.robotics;

import deck.lachlan.robotics.domain.RobotController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedConstruction;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MainTest {

    @Test
    @DisplayName("should initialise controller and read instructions from standard input")
    void shouldInitialiseControllerAndReadInstructionsFromStandardInput() {
        try (MockedConstruction<RobotController> mocked = mockConstruction(RobotController.class)) {
            Main.main(new String[0]);
            assertThat(mocked.constructed()).hasSize(1);
            verify(mocked.constructed().get(0)).readInstructions(System.in);
        }
    }
}