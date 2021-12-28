package game;

import engine.Sprite;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Tank extends Sprite {
    double tankRadius = 10;
    double frontAngle = 0;
    double bulletRadius = 5;
    double bulletVelocity = 5;

    public Tank(double centerX, double centerY) {
        Circle circle = new Circle();
        circle.setRadius(tankRadius);
        circle.setCenterX(0);
        circle.setCenterY(0);
        circle.setTranslateX(centerX);
        circle.setTranslateY(centerY);
        circle.setFill(Color.BLACK);
        node = circle;

        // Set the collision bounds to the square circumscribed in the circle.
        // This is so that bullets won't collide with the tank that fired it
        double circumscribedLength = tankRadius / Math.sqrt(2);
        Rectangle rect = new Rectangle();
        rect.setTranslateX(centerX - circumscribedLength);
        rect.setTranslateY(centerY - circumscribedLength);
        rect.setWidth(2 * circumscribedLength);
        rect.setHeight(2 * circumscribedLength);
        collisionBounds = rect;

        initalizeHealth(100);
        damage = 0;
    }

    public void update() { ; }

    public Bullet fire(double x, double y) {
        double deltaX = node.getTranslateX() - x;
        double deltaY = node.getTranslateY() - y;
        double angle = Math.atan2(deltaY, deltaX) + Math.PI;
        return fire(angle);
    }

    public Bullet fire() {
        return fire(frontAngle);
    }

    public Bullet fire(double angle) {

        Bullet bullet;

        double radiusSum = tankRadius + bulletRadius;
        double offsetX = radiusSum * Math.cos(angle);
        double offsetY = radiusSum * Math.sin(angle);

        double bulletX = node.getTranslateX() + offsetX;
        double bulletY = node.getTranslateY() + offsetY;

        double bulletVX = bulletVelocity * Math.cos(angle);
        double bulletVY = bulletVelocity * Math.sin(angle);

        bullet = new Bullet(bulletRadius, bulletX, bulletY, bulletVX, bulletVY);

        return bullet;
    }

}
