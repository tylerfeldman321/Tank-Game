package game;

public class ProjectileBuilder {

    public enum ProjectileType {
        BASIC_BULLET,
        SMALL_BULLET,
        EXPLODING_BULLET
    }

    public ProjectileType projectileType;
    public Weapon weapon;

    public ProjectileBuilder(ProjectileType projectileType, Weapon weapon) {
        this.projectileType = projectileType;
        this.weapon = weapon;
    }

    public Projectile build(double centerX, double centerY, double vX, double vY) {
        Projectile projectile = null;
        switch (projectileType) {
            case BASIC_BULLET: projectile = new BasicBullet(weapon, centerX, centerY, vX, vY); break;
            case SMALL_BULLET: projectile = new SmallBullet(weapon, centerX, centerY, vX, vY); break;
            case EXPLODING_BULLET: projectile = new ExplodingBullet(weapon, centerX, centerY, vX, vY); break;
        }
        return projectile;
    }

}