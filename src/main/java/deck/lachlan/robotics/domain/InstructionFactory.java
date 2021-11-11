package deck.lachlan.robotics.domain;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.io.PrintStream;
import java.util.Optional;

@EqualsAndHashCode
@RequiredArgsConstructor
public class InstructionFactory {
    private final PrintStream outStream;

    public Optional<Instruction> instructionFor(String input) {
        if ("report".equals(input)) {
            return Optional.of(new ReportInstruction(outStream));
        }
        return Optional.of(new PlaceInstruction(input));
    }
}
