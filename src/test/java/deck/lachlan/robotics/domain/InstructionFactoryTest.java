package deck.lachlan.robotics.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class InstructionFactoryTest {

    InstructionFactory subject = new InstructionFactory();

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
    @DisplayName("should support report instructions")
    void shouldSupportReportInstructions() {
        assertThat(subject.instructionFor("report"))
            .contains(new ReportInstruction());
    }
}