package deck.lachlan.robotics.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InterpreterTest {

    @InjectMocks
    Interpreter subject;

    @Mock InstructionFactory instructionFactory;
    @Mock TrimmedIOReader ioReader;

    @Test
    @DisplayName("should do nothing when no instructions to interpret")
    void shouldDoNothingWhenNoInstructionsToInterpret(@Mock InputStream in) {
        when(ioReader.linesFor(in)).thenReturn(Stream.empty());
        assertThat(subject.instructionOperators(in)).isEmpty();
        verifyNoInteractions(instructionFactory);
    }

    @Test
    @DisplayName("should return empty when no interpreted instructions found")
    void shouldReturnEmptyWhenNoInterpretedInstructionsFound(@Mock InputStream in) {
        when(ioReader.linesFor(in)).thenReturn(Stream.of("one", "two"));
        when(instructionFactory.instructionFor("one")).thenReturn(Optional.empty());
        when(instructionFactory.instructionFor("two")).thenReturn(Optional.empty());
        assertThat(subject.instructionOperators(in)).isEmpty();
    }

    @Test
    @DisplayName("should return the stream of interpreted instructions")
    void shouldReturnTheStreamOfInterpretedInstructions(
        @Mock InputStream in,
        @Mock Instruction one,
        @Mock Instruction two
    ) {
        when(ioReader.linesFor(in)).thenReturn(Stream.of("one", "two"));
        when(instructionFactory.instructionFor("one")).thenReturn(Optional.of(one));
        when(instructionFactory.instructionFor("two")).thenReturn(Optional.of(two));
        assertThat(subject.instructionOperators(in)).containsExactly(one, two);
    }
}