package deck.lachlan.robotics.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RobotTest {

    @Nested
    @DisplayName("by default")
    class byDefault {
        @Test
        @DisplayName("a robot should not be on the table")
        void aRobotShouldNotBeOnTheTable() {
            assertThat(new Robot().getPosition())
                .isEqualTo(new Position(-1, -1));
        }
    }
}