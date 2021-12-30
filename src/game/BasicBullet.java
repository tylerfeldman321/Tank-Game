package game;

import javafx.scene.paint.Color;

public class BasicBullet extends Projectile {
    public BasicBullet(Weapon weapon, double centerX, double centerY, double vX, double vY) {
        super(weapon,5, centerX, centerY, vX, vY, 10, 0, false, 10, true,
                true, Color.RED);
    }

    @Override
    public Projectile copy(Weapon weapon, double centerX, double centerY, double vX, double vY) {
        return new BasicBullet(weapon, centerX, centerY, vX, vY);
    }
}
