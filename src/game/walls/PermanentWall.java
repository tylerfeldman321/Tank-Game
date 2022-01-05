package game.walls;

import javafx.scene.paint.Color;

public class PermanentWall extends Wall {
    public PermanentWall(double startX, double startY, double endX, double endY, double width) {
        super(startX, startY, endX, endY, width, 10, 0, true, true, Color.BLACK);
    }
}
