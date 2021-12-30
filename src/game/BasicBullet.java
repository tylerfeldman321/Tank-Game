package game;

import javafx.scene.paint.Color;

public class BasicBullet extends Projectile {
    public BasicBullet(double centerX, double centerY, double vX, double vY) {
        super(5, centerX, centerY, vX, vY, 10, 0, false, 10, true,
                true, Color.RED);
    }
}
