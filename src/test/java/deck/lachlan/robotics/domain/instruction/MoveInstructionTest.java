package deck.lachlan.robotics.domain.instruction;

import deck.lachlan.robotics.domain.types.Robot;
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
class MoveInstructionTest {

    MoveInstruction subject = new MoveInstruction();
    
    @Test
    @DisplayName("should update table with moved robot if possible")
    void shouldUpdateTableWithMovedRobotIfPossible(@Mock Table table, @Mock Robot robot, @Mock Robot movedRobot) {
        when(table.findRobot()).thenReturn(Optional.of(robot));
        when(robot.moved()).thenReturn(movedRobot);

        subject.attemptRobotOperation(table);

        verify(table).tryPlaceRobot(movedRobot);
    }

    @Test
    @DisplayName("should do nothing when no robot on the table")
    void shouldDoNothingWhenNoRobotOnTheTable(@Mock Table table, @Mock Robot robot, @Mock Robot movedRobot) {
        when(table.findRobot()).thenReturn(Optional.empty());

        subject.attemptRobotOperation(table);

        verifyNoMoreInteractions(table);
    }
}