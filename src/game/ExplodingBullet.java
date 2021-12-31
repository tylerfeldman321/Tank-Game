package game;

import javafx.scene.paint.Color;

public class ExplodingBullet extends Projectile {
    public ExplodingBullet(Weapon weapon, double centerX, double centerY, double vX, double vY) {
        super(weapon,7, centerX, centerY, vX, vY, 10, 30, false, 2, true,
                true, 10, new SmallBullet(), 3, Color.FIREBRICK);
    }

    public ExplodingBullet() {
        this(null, 0, 0, 0, 0);
    }
}
