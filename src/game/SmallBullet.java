package game;

import javafx.scene.paint.Color;

public class SmallBullet extends Projectile {
    public SmallBullet(double centerX, double centerY, double vX, double vY) {
        super(2, centerX, centerY, vX, vY, 10, 0, true, 3, true,
                false, Color.BLACK);
    }
}
