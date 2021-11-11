package deck.lachlan.robotics.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.PrintStream;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportInstructionTest {

    @InjectMocks
    ReportInstruction subject;

    @Mock
    PrintStream outStream;

    @Test
    @DisplayName("should report the position of the robot when it has its bearings")
    void shouldReportThePositionOfTheRobotWhenItHasItsBearings(@Mock Table table, @Mock Robot robot) {
        when(table.getRobot()).thenReturn(robot);
        when(robot.getBearingsIfAvailable()).thenReturn(Optional.of("0,0,NORTH"));

        subject.attemptRobotOperation(table);

        verify(outStream).println("0,0,NORTH");
    }

    @Test
    @DisplayName("should not report anything when robot does not have its bearings")
    void shouldNotReportAnythingWhenRobotDoesNotHaveItsBearings(@Mock Table table, @Mock Robot robot) {
        when(table.getRobot()).thenReturn(robot);
        when(robot.getBearingsIfAvailable()).thenReturn(Optional.empty());

        subject.attemptRobotOperation(table);

        verifyNoInteractions(outStream);
    }
}