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
        rect.setX(centerX - radius);
        rect.setY(centerY - radius);
        rect.setWidth(2 * radius);
        rect.setHeight(2 * radius);
        collisionBounds = rect;

        Circle circle = new Circle();
        circle.setRadius(radius);
        circle.setCenterX(0);
        circle.setCenterY(0);
        circle.setTranslateX(centerX);
        circle.setTranslateY(centerY);
        circle.setFill(Color.BLACK);

        node = circle;

        this.vX = vX;
        this.vY = vY;

        initalizeHealth(0);
        damage = 10;
    }

    @Override
    public void update() {
        node.setTranslateX(node.getTranslateX() + vX);
        node.setTranslateY(node.getTranslateY() + vY);
        collisionBounds.setX(collisionBounds.getX() + vX);
        collisionBounds.setY(collisionBounds.getY() + vY);
    }

    public void handleDeath(GameWorld gameWorld) {
        super.handleDeath(gameWorld);
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

        double intersectionUpper = Math.max(wallBounds.getY(), bulletBounds.getY());
        double intersectionLower = Math.min(wallBounds.getY()+wallBounds.getHeight(),
                bulletBounds.getY()+wallBounds.getHeight());
        double intersectionLeft = Math.max(wallBounds.getX(), bulletBounds.getX());
        double intersectionRight = Math.min(wallBounds.getX()+wallBounds.getWidth(),
                bulletBounds.getX()+wallBounds.getWidth());
        double intersectionHeight = intersectionLower - intersectionUpper;
        double intersectionWidth = intersectionRight - intersectionLeft;

        if (intersectionHeight > intersectionWidth) {
            vX = -vX;
        } else {
            vY = -vY;
        }

    }
}
