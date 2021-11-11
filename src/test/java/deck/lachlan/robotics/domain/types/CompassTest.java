package deck.lachlan.robotics.domain.types;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

class CompassTest {

    @Nested
    @DisplayName("find")
    class find {
        @ParameterizedTest
        @EnumSource(Compass.class)
        @DisplayName("should return compass by lowercase name")
        void shouldReturnCompassByLowercaseName(Compass compass) {
            assertThat(Compass.findCompass(compass.name().toLowerCase())).contains(compass);
        }

        @Test
        @DisplayName("should return empty for bogus compass values")
        void shouldReturnEmptyForBogusCompassValues() {
            assertThat(Compass.findCompass("foo")).isEmpty();
        }
    }

    @Nested
    @DisplayName("left")
    class left {
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

        @ParameterizedTest
        @CsvSource({
            "north, west",
            "east, north",
            "south, east",
            "west, south"
        })
        @DisplayName("should use left rotation to move compass anti-clockwise")
        void shouldUseLeftRotationToMoveCompassAntiClockwise(String startDirection, String expectedDirection) {
            Compass start = Compass.valueOf(startDirection.toUpperCase());
            Compass expected = Compass.valueOf(expectedDirection.toUpperCase());
            assertThat(start.rotated(Rotate.LEFT)).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("right")
    class right {
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

        @ParameterizedTest
        @CsvSource({
            "north, east",
            "east, south",
            "south, west",
            "west, north"
        })
        @DisplayName("should use right rotation to move compass clockwise")
        void shouldUseRightRotationToMoveCompassClockwise(String startDirection, String expectedDirection) {
            Compass start = Compass.valueOf(startDirection.toUpperCase());
            Compass expected = Compass.valueOf(expectedDirection.toUpperCase());
            assertThat(start.rotated(Rotate.RIGHT)).isEqualTo(expected);
        }
    }
}