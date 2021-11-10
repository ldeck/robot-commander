package deck.lachlan.robotics.domain;

public class Robot {
    private Position position;

    public Robot() {
        this.position = new Position(-1, -1);
    }

    public Position getPosition() {
        return position;
    }
}
