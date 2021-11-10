package deck.lachlan.robotics.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class CompassTest {

    @Nested
    @DisplayName("turn left")
    class turnLeft {
        @ParameterizedTest
        @CsvSource({
            "north, west",
            "east, north",
            "south, east",
            "west, south"
        })
        @DisplayName("should move compass anti-clockwise")
        void shouldMoveCompassAntiClockwise(String startDirection, String expectedDirection) {
            Compass start = Compass.valueOf(startDirection.toUpperCase());
            Compass expected = Compass.valueOf(expectedDirection.toUpperCase());
            assertThat(start.left()).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("turn right")
    class turnRight {
        @ParameterizedTest
        @CsvSource({
            "north, east",
            "east, south",
            "south, west",
            "west, north"
        })
        @DisplayName("should move compass clockwise")
        void shouldMoveCompassClockwise(String startDirection, String expectedDirection) {
            Compass start = Compass.valueOf(startDirection.toUpperCase());
            Compass expected = Compass.valueOf(expectedDirection.toUpperCase());
            assertThat(start.right()).isEqualTo(expected);
        }
    }
}