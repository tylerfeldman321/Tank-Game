package game;

import javafx.scene.paint.Color;

public class Mine extends Projectile {
    public Mine(Weapon weapon, double centerX, double centerY, double vX, double vY) {
        super(weapon,7, centerX, centerY, vX, vY, 10, 0, false, 10, false,
                true, 10, new SmallBullet(), 3, Color.YELLOW);
    }

    public Mine() {
        this(null, 0, 0, 0, 0);
    }
}
