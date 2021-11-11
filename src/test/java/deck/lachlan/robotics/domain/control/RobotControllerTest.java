package deck.lachlan.robotics.domain.control;

import deck.lachlan.robotics.domain.io.Interpreter;
import deck.lachlan.robotics.domain.types.Table;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class RobotControllerTest {

    @InjectMocks
    RobotController subject;

    @Mock
    Table table;
    @Mock
    Interpreter interpreter;

    @Nested
    @DisplayName("when no instructions to read")
    class whenNoInstructionsToRead {
        @Test
        @DisplayName("the controller state should remain unchanged")
        void theControllerStateShouldRemainUnchanged(@Mock InputStream sysin) {
            subject.readInstructions(sysin);

            verify(interpreter).instructionOperators(sysin);
            verifyNoInteractions(table);
        }
    }
}