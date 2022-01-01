package game;

import javafx.scene.paint.Color;

public class Mine1 extends Projectile {
    public Mine1(Weapon weapon, double centerX, double centerY, double vX, double vY) {
        super(weapon,10, centerX, centerY, vX, vY, 10, 0, false, 0.1, false,
                true, 4, new Mine2(), 0, Color.ORANGERED);
    }

    public Mine1() {
        this(null, 0, 0, 0, 0);
    }
}
