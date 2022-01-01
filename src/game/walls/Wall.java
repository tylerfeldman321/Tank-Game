package game.walls;

import engine.GameWorld;
import engine.Sprite;

import javafx.scene.shape.Rectangle;

public class Wall extends Sprite {

    public Wall(double startX, double startY, double endX, double endY, double width) {
        Rectangle rect = new Rectangle();

        assert(startX == endX || startY == endY);

        // Line is vertical
        if (startX == endX)
        {
            rect.setTranslateX(startX);
            rect.setTranslateY(Math.min(startY, endY));
            rect.setWidth(width);
            rect.setHeight(Math.abs(startY - endY));
        }
        // Line is horizontal
        else if (startY == endY)
        {
            rect.setTranslateX(Math.min(startX, endX));
            rect.setTranslateY(startY);
            rect.setHeight(width);
            rect.setWidth(Math.abs(startX - endX));
        }

        collisionBounds = rect;
        node = rect;

        initializeHealth(10);
        damage = 0;
        isInvincible = true;
    }

    @Override
    public void update(GameWorld gameWorld) { }
}
