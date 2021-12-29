package game;

import engine.GameWorld;
import engine.Sprite;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public abstract class Projectile extends Sprite {

    /**
     * Whether this will bounce off of walls
     */
    boolean bounce;

    public Projectile(double radius, double centerX, double centerY, double vX, double vY, double damage, double health,
                      boolean isInvincible, double lifetime, boolean bounce, Color color) {
        Rectangle rect = new Rectangle();
        rect.setTranslateX(centerX - radius);
        rect.setTranslateY(centerY - radius);
        rect.setWidth(2 * radius);
        rect.setHeight(2 * radius);
        collisionBounds = rect;

        Circle circle = new Circle();
        circle.setRadius(radius);
        circle.setTranslateX(centerX);
        circle.setTranslateY(centerY);
        circle.setFill(color);
        node = circle;

        // Initialize values
        this.vX = vX;
        this.vY = vY;
        initializeHealth(health);
        this.damage = damage;
        this.isInvincible = isInvincible;
        this.lifetime = lifetime;
        this.bounce = bounce;
    }

    @Override
    public void update(GameWorld gameWorld) {
        updatePosition(vX, vY);
        checkDuration(gameWorld);
    }

    @Override
    public void collision(Sprite other, GameWorld gameWorld) {
        if (other instanceof Wall && bounce) {
            bounceOffWall((Wall)other);
            return;
        }
        else {
            super.collision(other, gameWorld);
        }
    }

    private void bounceOffWall(Wall other) {

        Rectangle wallBounds = other.collisionBounds;
        Rectangle bulletBounds = collisionBounds;

        double intersectionUpper = Math.max(wallBounds.getTranslateY(), bulletBounds.getTranslateY());
        double intersectionLower = Math.min(wallBounds.getTranslateY()+wallBounds.getHeight(),
                bulletBounds.getTranslateY()+wallBounds.getHeight());
        double intersectionLeft = Math.max(wallBounds.getTranslateX(), bulletBounds.getTranslateX());
        double intersectionRight = Math.min(wallBounds.getTranslateX()+wallBounds.getWidth(),
                bulletBounds.getTranslateX()+wallBounds.getWidth());
        double intersectionHeight = intersectionLower - intersectionUpper;
        double intersectionWidth = intersectionRight - intersectionLeft;

        if (intersectionHeight > intersectionWidth) {
            vX = -vX;
        } else {
            vY = -vY;
        }
    }
}
