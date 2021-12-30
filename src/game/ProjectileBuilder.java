package game;

public class ProjectileBuilder {

    public enum ProjectileType {
        BASIC_BULLET,
        SMALL_BULLET,
        EXPLODING_BULLET
    }

    public ProjectileType projectileType;

    public ProjectileBuilder(ProjectileType projectileType) {
        this.projectileType = projectileType;
    }

    public Projectile build(double centerX, double centerY, double vX, double vY) {
        Projectile projectile = null;
        switch (projectileType) {
            case BASIC_BULLET: projectile = new BasicBullet(centerX, centerY, vX, vY); break;
            case SMALL_BULLET: projectile = new SmallBullet(centerX, centerY, vX, vY); break;
            case EXPLODING_BULLET: projectile = new ExplodingBullet(centerX, centerY, vX, vY); break;
        }
        return projectile;
    }

}