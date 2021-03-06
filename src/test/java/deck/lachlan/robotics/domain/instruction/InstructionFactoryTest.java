package deck.lachlan.robotics.domain.instruction;

import deck.lachlan.robotics.domain.types.Rotate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class InstructionFactoryTest {

    @InjectMocks
    InstructionFactory subject;

    @Mock
    PrintStream outStream;

    @ParameterizedTest
    @ValueSource(strings = {
        "place",
        "place 0,0,north",
        "place 2,1,south",
        "place 2,5,east",
        "place 3,0,west",
    })
    @DisplayName("should support place instructions")
    void shouldSupportPlaceInstructions(String instruction) {
        assertThat(subject.instructionFor(instruction))
            .contains(new PlaceInstruction(instruction));
    }

    @Test
    @DisplayName("should support move instructions")
    void shouldSupportMoveInstructions() {
        assertThat(subject.instructionFor("move"))
            .contains(new MoveInstruction());
    }

    @ParameterizedTest
    @EnumSource(Rotate.class)
    @DisplayName("should support left and right instruction")
    void shouldSupportLeftAndRightInstruction(Rotate direction) {
        assertThat(subject.instructionFor(direction.name().toLowerCase()))
            .contains(new RotateInstruction(direction));
    }

    @Test
    @DisplayName("should support report instructions")
    void shouldSupportReportInstructions() {
        assertThat(subject.instructionFor("report"))
            .contains(new ReportInstruction(outStream));
    }
}