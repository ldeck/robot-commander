package deck.lachlan.robotics.domain;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.io.InputStream;
import java.util.Optional;
import java.util.stream.Stream;

@EqualsAndHashCode
@RequiredArgsConstructor
public class Interpreter {
    private final InstructionFactory instructionFactory;
    private final TrimmedIOReader ioReader;

    public Stream<Instruction> instructionOperators(InputStream in) {
        return ioReader.linesFor(in)
            .map(instructionFactory::instructionFor)
            .flatMap(Optional::stream);
    }
}
