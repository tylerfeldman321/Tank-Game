package game;

import engine.GameWorld;
import engine.Sprite;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Tank extends Sprite {

    GameWorld gameWorld;

    double tankHealth = 10;
    double tankRadius = 10;
    double frontAngleDegrees = 0;

    // Movement parameters
    double tankForwardSpeed = 2;
    double tankBackwardSpeed = 1.5;
    double tankTurnSpeed = 3;

    // Weapon
    public Weapon weapon;

    public Tank(GameWorld gameWorld, double centerX, double centerY) {
        Circle circle = new Circle();
        circle.setRadius(tankRadius);
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

        initializeHealth(tankHealth);
        damage = 0;
        this.gameWorld = gameWorld;
    }

    public void update(GameWorld gameWorld) { ; }

    public void setWeapon() {
        this.weapon = new Weapon(gameWorld, this, ProjectileType.BASIC_BULLET, 10,
                3, 10, 1, 3, 0, true);
    }

    public void fireWeapon(double x, double y) {
        weapon.fire(x, y);
    }

    public void fireWeapon() {
        weapon.fire(frontAngleDegrees);
    }

    public void moveForward() {
        move(tankForwardSpeed);
    }

    public void moveBackward() {
        move(-tankBackwardSpeed);
    }

    private void move(double distance) {
        double angleRadians = Math.toRadians(frontAngleDegrees);
        double distanceX = distance * Math.cos(angleRadians);
        double distanceY = distance * Math.sin(angleRadians);
        updatePosition(distanceX, distanceY);
    }

    public void turnCCW() {
        turn(tankTurnSpeed);
    }

    public void turnCW() {
        turn(-tankTurnSpeed);
    }

    private void turn(double degrees) {
        frontAngleDegrees += degrees;
    }


}
