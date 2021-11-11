package deck.lachlan.robotics;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

class MainIntegrationTest {

    InputStream systemIn;
    PrintStream systemOut;

    @BeforeEach
    void setUp() {
        systemIn = System.in;
        systemOut = System.out;
    }

    @AfterEach
    void tearDown() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    @Test
    @DisplayName("should be able to set robot on table")
    void shouldBeAbleToSetRobotOnTable() {
        String instructions = String.join("\n",
            "PLACE 0,0,NORTH",
            "REPORT"
        );

        ByteArrayInputStream testInput = new ByteArrayInputStream(instructions.getBytes());
        System.setIn(testInput);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream testOutput = new PrintStream(out);
        System.setOut(testOutput);

        Main.main(new String[0]);

        assertThat(out.toString()).isEqualTo("0,0,NORTH\n");
    }

    @ParameterizedTest
    @CsvSource({
        "-1,0,NORTH",
        "0,-1,NORTH",
        "0,0,BOOVE",
    })
    @DisplayName("should ignore an invalid placement")
    void shouldIgnoreAnInvalidPlacement(int x, int y, String compass) {
        String instructions = String.join("\n",
            String.format("PLACE %s,%s,%s", x, y, compass),
            "REPORT"
        );

        ByteArrayInputStream testInput = new ByteArrayInputStream(instructions.getBytes());
        System.setIn(testInput);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream testOutput = new PrintStream(out);
        System.setOut(testOutput);

        Main.main(new String[0]);

        assertThat(out.toString()).isEqualTo("");
    }
}