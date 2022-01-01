package game.projectiles;

import game.weapons.Weapon;
import javafx.scene.paint.Color;

public class SmallBullet extends Projectile {
    public SmallBullet(Weapon weapon, double centerX, double centerY, double vX, double vY) {
        super(weapon,2, centerX, centerY, vX, vY, 10, 0, false, 2, true,
                false, 0, null, 0, Color.BLACK);
    }

    public SmallBullet() {
        this(null, 0, 0, 0, 0);
    }
}
