package deck.lachlan.robotics.domain.instruction;

import deck.lachlan.robotics.domain.types.Rotate;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.io.PrintStream;
import java.util.List;
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
        else if (List.of("left", "right").contains(input)) {
            Rotate rotate = Rotate.valueOf(input.toUpperCase());
            return Optional.of(new RotateInstruction(rotate));
        }
        return Optional.of(new PlaceInstruction(input));
    }
}
