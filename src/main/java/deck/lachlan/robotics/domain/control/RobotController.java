package deck.lachlan.robotics.domain.control;

import deck.lachlan.robotics.domain.io.Interpreter;
import deck.lachlan.robotics.domain.types.Table;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.InputStream;

@RequiredArgsConstructor
public class RobotController {
    private final Table table;
    private final Interpreter interpreter;

    @SneakyThrows
    public void readInstructions(InputStream io) {
        interpreter
            .instructionOperators(io)
            .forEach(instruction -> instruction.attemptRobotOperation(table));
    }
}
