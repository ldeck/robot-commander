package deck.lachlan.robotics;

import deck.lachlan.robotics.domain.types.Compass;
import deck.lachlan.robotics.domain.types.Position;
import deck.lachlan.robotics.domain.types.Robot;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class MainIntegrationTest {

    InputStream systemIn;
    PrintStream systemOut;
    ByteArrayInputStream testInput;
    ByteArrayOutputStream testOutput;

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

    private void setupSystemIOWith(String instructions) {
        testInput = new ByteArrayInputStream(instructions.getBytes());
        System.setIn(testInput);

        testOutput = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(testOutput);
        System.setOut(out);
    }

    @Nested
    @DisplayName("place and report")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class placeAndReport {

        public Stream<Robot> validRobots() {
            return IntStream.range(0, 5).boxed()
                .flatMap(x -> IntStream.range(0, 5).boxed()
                    .flatMap(y -> Arrays.stream(Compass.values())
                        .map(c -> new Robot(new Position(x, y), c))));
        }

        @ParameterizedTest
        @MethodSource("validRobots")
        @DisplayName("should be able to set robot on table")
        void shouldBeAbleToSetRobotOnTable(Robot robot) {
            setupSystemIOWith(String.join("\n",
                String.format("PLACE %s,%s", robot.getPosition(), robot.getCompass()),
                "REPORT"
            ));

            Main.main(new String[0]);

            assertThat(testOutput.toString()).isEqualTo(String.format("%s,%s,%s\n",
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
            setupSystemIOWith(String.join("\n",
                String.format("PLACE %s,%s,%s", x, y, compass),
                "REPORT"
            ));

            Main.main(new String[0]);

            assertThat(testOutput.toString()).isEqualTo("");
        }
    }

    @Nested
    @DisplayName("place, move and report")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class placeMoveAndReport {
        private Stream<Arguments> xySlices(int startX, int maxX, int startY, int maxY, Compass compass, int deltaX, int deltaY) {
            return IntStream.range(startX, maxX)
                .boxed()
                .flatMap(x -> IntStream.range(startY, maxY)
                        .boxed()
                        .map(y -> Arguments.arguments(x, y, compass, deltaX, deltaY)));
        }

        public Stream<Arguments> northBoundPossibles() {
            return xySlices(0, 5, 0,4, Compass.NORTH, 0, 1);
        }

        public Stream<Arguments> southBoundPossibles() {
            return xySlices(0, 5, 1,5, Compass.SOUTH, 0, -1);
        }

        public Stream<Arguments> eastBoundPossibles() {
            return xySlices(0, 4, 0,5, Compass.EAST, 1, 0);
        }

        public Stream<Arguments> westBoundPossibles() {
            return xySlices(1, 5, 0,5, Compass.WEST, -1, 0);
        }

        @ParameterizedTest
        @MethodSource({
            "northBoundPossibles",
            "southBoundPossibles",
            "eastBoundPossibles",
            "westBoundPossibles"
        })
        @DisplayName("can move when placed inside bounds")
        void canMoveWhenPlacedInsideBounds(int x, int y, Compass compass, int deltaX, int deltaY) {
            setupSystemIOWith(String.join("\n",
                String.format("PLACE %s,%S", new Position(x, y), compass),
                "MOVE",
                "REPORT"
            ));

            Main.main(new String[0]);

            assertThat(testOutput.toString()).isEqualTo(String.format("%s,%s,%s\n",
                x + deltaX,
                y + deltaY,
                compass
            ));
        }
    }

    @Nested
    @DisplayName("place, rotate and report")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class placeRotateAndReport {

        public Stream<Arguments> leftPossibleMoves() {
            return IntStream.range(0, 5)
                .boxed()
                .flatMap(x -> IntStream.range(0, 5)
                    .boxed()
                    .flatMap(y -> Arrays.stream(Compass.values())
                        .map(c -> Arguments.arguments(x, y, c, "LEFT", c.left()))));
        }

        public Stream<Arguments> rightPossibleMoves() {
            return IntStream.range(0, 5)
                .boxed()
                .flatMap(x -> IntStream.range(0, 5)
                    .boxed()
                    .flatMap(y -> Arrays.stream(Compass.values())
                        .map(c -> Arguments.arguments(x, y, c, "RIGHT", c.right()))));
        }

        @ParameterizedTest
        @MethodSource({
            "leftPossibleMoves",
            "rightPossibleMoves"
        })
        @DisplayName("should rotate when placed inside bounds")
        void shouldRotateWhenPlacedInsideBounds(int x, int y, Compass compass, String direction, Compass expectedCompass) {
            setupSystemIOWith(String.join("\n",
                String.format("PLACE %s,%S", new Position(x, y), compass),
                direction,
                "REPORT"
            ));

            Main.main(new String[0]);

            assertThat(testOutput.toString()).isEqualTo(String.format("%s,%s,%s\n",
                x,
                y,
                expectedCompass
            ));
        }
    }

    @Nested
    @DisplayName("scenarios")
    class scenarios {
        @Test
        @DisplayName("scenario a")
        void scenarioA() {
            setupSystemIOWith(String.join("\n",
                "PLACE 0,0,NORTH",
                "MOVE",
                "REPORT"
            ));

            Main.main(new String[0]);

            assertThat(testOutput.toString()).isEqualTo("0,1,NORTH\n");
        }

        @Test
        @DisplayName("scenario b")
        void scenarioB() {
            setupSystemIOWith(String.join("\n",
                "PLACE 0,0,NORTH",
                "LEFT",
                "REPORT"
            ));

            Main.main(new String[0]);

            assertThat(testOutput.toString()).isEqualTo("0,0,WEST\n");
        }

        @Test
        @DisplayName("scenario c")
        void scenarioC() {
            setupSystemIOWith(String.join("\n",
                "PLACE 1,2,EAST",
                "MOVE",
                "MOVE",
                "LEFT",
                "MOVE",
                "REPORT"
            ));

            Main.main(new String[0]);

            assertThat(testOutput.toString()).isEqualTo("3,3,NORTH\n");
        }
    }
}