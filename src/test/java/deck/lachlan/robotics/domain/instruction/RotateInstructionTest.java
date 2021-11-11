package deck.lachlan.robotics.domain.instruction;

import deck.lachlan.robotics.domain.types.Robot;
import deck.lachlan.robotics.domain.types.Rotate;
import deck.lachlan.robotics.domain.types.Table;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RotateInstructionTest {

    @Test
    @DisplayName("should do nothing when robot off the table")
    void shouldDoNothingWhenRobotOffTheTable(@Mock Rotate rotation, @Mock Table table) {
        when(table.findRobot()).thenReturn(Optional.empty());
        new RotateInstruction(rotation).attemptRobotOperation(table);
        verifyNoMoreInteractions(table);
    }

    @Test
    @DisplayName("should rotate when on the table")
    void shouldRotateWhenOnTheTable(
        @Mock Rotate rotation,
        @Mock Table table,
        @Mock Robot robot,
        @Mock Robot newRobot
    ) {
        when(table.findRobot()).thenReturn(Optional.of(robot));
        when(robot.rotated(rotation)).thenReturn(newRobot);
        new RotateInstruction(rotation).attemptRobotOperation(table);
        verify(table).tryPlaceRobot(newRobot);
    }
}