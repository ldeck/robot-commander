package deck.lachlan.robotics.domain.types;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class TableTest {
    private static final int ABSTRACT_WIDTH = 100;

    Table subject = new Table(ABSTRACT_WIDTH);

    @Nested
    @DisplayName("try place robot")
    class tryPlaceRobot {
        @Test
        @DisplayName("should fail for no compass")
        void shouldFailForNoCompass() {
            Robot robot = new Robot(new Position(0, 0), null);
            subject.tryPlaceRobot(robot);
            assertThat(subject.findRobot()).isEmpty();
        }

        @ParameterizedTest
        @CsvSource({
            "-1,0",
            "0,-1",
            ABSTRACT_WIDTH + ",0",
            "0," + ABSTRACT_WIDTH
        })
        @DisplayName("should fail for outside bounds positions")
        void shouldFailForOutsideBoundsPositions(int x, int y) {
            Robot robot = new Robot(new Position(x, y), Compass.NORTH);
            subject.tryPlaceRobot(robot);
            assertThat(subject.findRobot()).isEmpty();
        }
    }
}