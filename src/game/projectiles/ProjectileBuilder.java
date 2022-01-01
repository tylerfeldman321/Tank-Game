package game.projectiles;

import game.weapons.Weapon;

public class ProjectileBuilder {

    public Projectile projectile;
    public Weapon weapon;

    public ProjectileBuilder(Projectile projectile, Weapon weapon) {
        this.projectile = projectile;
        this.weapon = weapon;
    }

    public Projectile build(double centerX, double centerY, double vX, double vY) {
        Projectile newProjectile = projectile.copy(weapon, centerX, centerY, vX, vY);
        return newProjectile;
    }

}