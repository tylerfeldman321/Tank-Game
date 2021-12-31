package game;

import javafx.scene.paint.Color;

public class BasicBullet extends Projectile {
    public BasicBullet(Weapon weapon, double centerX, double centerY, double vX, double vY) {
        super(weapon,5, centerX, centerY, vX, vY, 10, 0, false, 5, true,
                true, 0, null, 0, Color.RED);
    }

    public BasicBullet() {
        this(null, 0, 0, 0, 0);
    }
}
