package deck.lachlan.robotics.domain;

import lombok.SneakyThrows;

import java.io.InputStream;

public class RobotController {
    private final Table table;
    private final Interpreter interpreter;

    public RobotController(Table table, Interpreter interpreter) {
        this.table = table;
        this.interpreter = interpreter;
    }

    @SneakyThrows
    public void readInstructions(InputStream io) {
        interpreter
            .instructionOperators(io)
            .forEach(instruction -> instruction.attemptRobotOperation(table));
    }
}
