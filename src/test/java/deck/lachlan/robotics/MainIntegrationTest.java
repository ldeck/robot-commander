package deck.lachlan.robotics;

import deck.lachlan.robotics.domain.types.Compass;
import deck.lachlan.robotics.domain.types.Position;
import deck.lachlan.robotics.domain.types.Robot;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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

    @Nested
    @DisplayName("place and report")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class placeAndReport {

        public Stream<Robot> validRobots() {
            List<Robot> robots = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    for (int k = 0; k < Compass.values().length; k++) {
                        robots.add(new Robot(new Position(i, j), Compass.values()[k]));
                    }
                }
            }
            return robots.stream();
        }

        @ParameterizedTest
        @MethodSource("validRobots")
        @DisplayName("should be able to set robot on table")
        void shouldBeAbleToSetRobotOnTable(Robot robot) {
            String instructions = String.join("\n",
                String.format("PLACE %s,%s", robot.getPosition(), robot.getCompass()),
                "REPORT"
            );

            ByteArrayInputStream testInput = new ByteArrayInputStream(instructions.getBytes());
            System.setIn(testInput);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PrintStream testOutput = new PrintStream(out);
            System.setOut(testOutput);

            Main.main(new String[0]);

            assertThat(out.toString()).isEqualTo(String.format("%s,%s,%s\n",
                robot.getPosition().getX(),
                robot.getPosition().getY(),
                robot.getCompass()
            ));
        }

        @ParameterizedTest
        @CsvSource({
            "-1,0,NORTH",
            "0,-1,NORTH",
            "5,0,NORTH",
            "0,5,NORTH",
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

    @Nested
    @DisplayName("place, move and report")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class placeMoveAndReport {
        public Stream<Arguments> northBoundPossibles() {
            List<Arguments> coords = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 4; j++) {
                    coords.add(Arguments.arguments(i, j));
                }
            }
            return coords.stream();
        }

        @ParameterizedTest
        @MethodSource("northBoundPossibles")
        @DisplayName("can move when placed inside bounds")
        void canMoveNorthWhenPlacedInsideBounds(int x, int y) {
            String instructions = String.join("\n",
                String.format("PLACE %s,NORTH", new Position(x, y)),
                "MOVE",
                "REPORT"
            );

            ByteArrayInputStream testInput = new ByteArrayInputStream(instructions.getBytes());
            System.setIn(testInput);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PrintStream testOutput = new PrintStream(out);
            System.setOut(testOutput);

            Main.main(new String[0]);

            assertThat(out.toString()).isEqualTo(String.format("%s,%s,%s\n",
                x,
                y + 1,
                "NORTH"
            ));
        }
    }
}