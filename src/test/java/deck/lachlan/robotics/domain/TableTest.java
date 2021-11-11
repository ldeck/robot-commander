package deck.lachlan.robotics.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class TableTest {

    Table subject;

    @BeforeEach
    void setUp() {
        subject = new Table(Integer.MAX_VALUE);
    }

    @Nested
    @DisplayName("find robot")
    class findRobot {
        @Test
        @DisplayName("should be empty when not placed on me")
        void shouldBeEmptyWhenNotPlacedOnMe() {
            assertThat(subject.findRobot()).isEmpty();
        }

        @Test
        @DisplayName("should return the robot when found on the table")
        void shouldReturnTheRobotWhenFoundOnTheTable(@Mock Robot robot) {
            subject.tryPlaceRobot(robot);
            assertThat(subject.findRobot()).contains(robot);
        }
    }
}