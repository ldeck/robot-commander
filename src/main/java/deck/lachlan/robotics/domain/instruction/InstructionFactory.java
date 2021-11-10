package deck.lachlan.robotics.domain.instruction;

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
        else if ("move".equals(input)) {
            return Optional.of(new MoveInstruction());
        }
        return Optional.of(new PlaceInstruction(input));
    }
}
