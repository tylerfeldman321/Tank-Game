package game.walls;

import javafx.scene.paint.Color;

public class DestructableWall extends Wall {
    public DestructableWall(double startX, double startY, double endX, double endY, double width) {
        super(startX, startY, endX, endY, width, 30, 0, false, false, Color.GOLDENROD);
    }
}
