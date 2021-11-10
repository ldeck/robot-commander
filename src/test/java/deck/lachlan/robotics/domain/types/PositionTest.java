package deck.lachlan.robotics.domain.types;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class PositionTest {

    @Nested
    @DisplayName("isValid")
    class isValid {
        @ParameterizedTest
        @CsvSource({
            "0,0,true",
            "-1,0,false",
            "1,-1,false",
        })
        @DisplayName("should return true when both coordinates are positive")
        void shouldReturnTrueWhenBothCoordinatesArePositive(int x, int y, boolean expected) {
            assertThat(new Position(x, y).isPotentiallyValid()).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("moved")
    class moved {
        @ParameterizedTest
        @CsvSource({
            "1,1,NORTH,1,2",
            "1,1,EAST,2,1",
            "1,1,SOUTH,1,0",
            "1,1,WEST,0,1",
        })
        @DisplayName("should move in the direction provided")
        void shouldMoveInTheDirectionProvided(int x, int y, Compass compass, int newX, int newY) {
            assertThat(new Position(x, y).moved(compass))
            .isEqualTo(new Position(newX, newY));
        }
    }

    @Nested
    @DisplayName("toString")
    class toString {
        @ParameterizedTest
        @CsvSource({
            "0,0",
            "-1,0",
            "1,-1",
        })
        @DisplayName("should return x,y string")
        void shouldReturnXYString(int x, int y) {
            assertThat(new Position(x, y).toString())
                .isEqualTo(x + "," + y);
        }
    }
}