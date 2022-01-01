package game;

import javafx.scene.paint.Color;

public class Mine3 extends Projectile {
    public Mine3(Weapon weapon, double centerX, double centerY, double vX, double vY) {
        super(weapon,8, centerX, centerY, vX, vY, 10, 0, false, 0.1, false,
                true, 0, null, 0, Color.YELLOW);
    }

    public Mine3() {
        this(null, 0, 0, 0, 0);
    }
}
