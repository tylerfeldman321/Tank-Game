package game.walls;

import engine.GameWorld;
import engine.Sprite;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Wall extends Sprite {

    /**
     * Whether this bounces projectiles off it
     */
    private boolean bouncy;

    public Wall(double startX, double startY, double endX, double endY, double width, double health, double damage,
                boolean isInvincible, boolean bouncy, Color color) {
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

        rect.setFill(color);
        collisionBounds = rect;
        node = rect;

        initializeHealth(health);
        this.damage = damage;
        this.isInvincible = isInvincible;
        this.bouncy = bouncy;
    }

    @Override
    public void update(GameWorld gameWorld) { }

    public boolean isBouncy() {
        return this.bouncy;
    }

}
