package game;

import javafx.scene.paint.Color;

public class SmallBullet extends Projectile {
    public SmallBullet(Weapon weapon, double centerX, double centerY, double vX, double vY) {
        super(weapon,2, centerX, centerY, vX, vY, 10, 0, true, 3, true,
                false, Color.BLACK);
    }

    @Override
    public Projectile copy(Weapon weapon, double centerX, double centerY, double vX, double vY) {
        return new SmallBullet(weapon, centerX, centerY, vX, vY);
    }
}
