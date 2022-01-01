package game;

import javafx.scene.paint.Color;

public class Mine2 extends Projectile {
    public Mine2(Weapon weapon, double centerX, double centerY, double vX, double vY) {
        super(weapon,9, centerX, centerY, vX, vY, 10, 0, false, 0.1, false,
                true, 3, new Mine3(), 0, Color.ORANGE);
    }

    public Mine2() {
        this(null, 0, 0, 0, 0);
    }
}