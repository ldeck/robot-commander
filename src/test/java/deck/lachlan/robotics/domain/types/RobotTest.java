package deck.lachlan.robotics.domain.types;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RobotTest {

    @Nested
    @DisplayName("by default")
    class byDefault {
        @Test
        @DisplayName("a robot should not be on the table")
        void aRobotShouldNotBeOnTheTable() {
            assertThat(Robot.unplacedRobot()).isEqualTo(new Robot(
                new Position(-1, -1),
                null
            ));
        }
    }

    @Nested
    @DisplayName("bearings")
    class bearings {
        @Test
        @DisplayName("should be empty when no compass defined")
        void shouldBeEmptyWhenNoCompassDefined() {
            Robot robot = new Robot(new Position(0, 0), null);
            assertThat(robot.getBearingsIfAvailable()).isEmpty();
        }

        @Test
        @DisplayName("should be empty when position row is negative")
        void shouldBeEmptyWhenPositionRowIsNegative() {
            Robot robot = new Robot(new Position(-1, 0), Compass.EAST);
            assertThat(robot.getBearingsIfAvailable()).isEmpty();
        }

        @Test
        @DisplayName("should be empty when position col is negative")
        void shouldBeEmptyWhenPositionColIsNegative() {
            Robot robot = new Robot(new Position(0, -1), Compass.EAST);
            assertThat(robot.getBearingsIfAvailable()).isEmpty();
        }

        @ParameterizedTest
        @CsvSource({
            "0,0,NORTH"
        })
        @DisplayName("should print x, y, compass when all defined")
        void shouldPrintXYCompassWhenAllDefined(int row, int col, Compass compass) {
            Robot robot = new Robot(new Position(row, col), compass);
            assertThat(robot.getBearingsIfAvailable())
                .contains(String.format("%s,%s,%s", row, col, compass));
        }
    }

    @Nested
    @DisplayName("moved")
    class moved {
        @Test
        @DisplayName("should return new robot with adjusted position via compass")
        void shouldReturnNewRobotWithAdjustedPositionViaCompass(
            @Mock Position position,
            @Mock Position newPosition,
            @Mock Compass compass
        ) {
            Robot robot = new Robot(position, compass);
            when(position.moved(compass)).thenReturn(newPosition);
            assertThat(robot.moved()).isEqualTo(new Robot(newPosition, compass));
        }
    }
}