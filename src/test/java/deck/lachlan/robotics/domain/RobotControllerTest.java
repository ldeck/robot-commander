package deck.lachlan.robotics.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RobotControllerTest {

    RobotController subject;

    @BeforeEach
    void setUp() {
        subject = new RobotController();
    }

    @Nested
    @DisplayName("when no instructions to read")
    class whenNoInstructionsToRead {
        @Test
        @DisplayName("the controller state should remain unchanged")
        void theControllerStateShouldRemainUnchanged(@Mock InputStream sysin) throws IOException {
            when(sysin.available()).thenReturn(0);
            subject.readInstructions(sysin);
            assertThat(subject.position()).isEqualTo(new Position(0, 0));
        }
    }
}