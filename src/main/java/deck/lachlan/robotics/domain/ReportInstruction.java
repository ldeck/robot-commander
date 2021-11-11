package deck.lachlan.robotics.domain;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.io.PrintStream;

@EqualsAndHashCode
@RequiredArgsConstructor
public class ReportInstruction implements Instruction {
    private final PrintStream outStream;

    @Override
    public void attemptRobotOperation(Table table) {
        table.findRobot()
            .flatMap(Robot::getBearingsIfAvailable)
            .ifPresent(outStream::println);
    }
}
