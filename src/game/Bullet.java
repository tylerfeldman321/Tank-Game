package game;

import engine.GameWorld;
import engine.Sprite;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Bullet extends Sprite {

    boolean bounce = true;

    public Bullet(double radius, double centerX, double centerY, double vX, double vY) {
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
        circle.setFill(Color.BLACK);

        node = circle;

        this.vX = vX;
        this.vY = vY;

        initializeHealth(0);
        damage = 10;
    }

    @Override
    public void update() {
        updatePosition(vX, vY);
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
