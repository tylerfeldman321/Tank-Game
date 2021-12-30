package game;

import java.util.HashSet;
import java.util.Set;

public class Weapon {

    /**
     * Tank that owns this weapon
     */
    public Tank owner;

    /**
     * Shots allowed per second
     */
    public double rateOfFire;

    /**
     * What kind of projectiles it shoots
     */
    public ProjectileBuilder projectileBuilder;

    /**
     * How many of these projectiles are allowed to exist at one time
     */
    public int maxProjectiles;

    /**
     * How many projectiles are shot at once
     */
    public int projectilesPerShot;

    /**
     * Total velocity of projectile
     */
    public double velocity;

    /**
     * Distribution or range of angles that the weapon will fire in
     */

    /**
     * Rapidfire? i.e. does this continuously shoot while holding the key down
     */
    public boolean rapidFire;

    /**
     *
     */
    public Set<Projectile> projectileList = new HashSet<>();

    public Weapon(Tank owner, ProjectileBuilder.ProjectileType projectileType, double rateOfFire, int maxProjectiles, int projectilesPerShot, double velocity, boolean rapidFire) {
        this.owner = owner;
        this.projectileBuilder = new ProjectileBuilder(projectileType);
        this.rateOfFire = rateOfFire;
        this.maxProjectiles = maxProjectiles;
        this.projectilesPerShot = projectilesPerShot;
        this.velocity = velocity;
        this.rapidFire = rapidFire;
    }

    public Projectile fire(double x, double y) {
        double deltaX = owner.node.getTranslateX() - x;
        double deltaY = owner.node.getTranslateY() - y;
        double angleDegrees = Math.toDegrees(Math.atan2(deltaY, deltaX) + Math.PI);
        return fire(angleDegrees);
    }

    public Projectile fire(double angleDegrees) {
        double angleRadians = Math.toRadians(angleDegrees);

        Projectile projectile;

        double radiusSum = owner.tankRadius + getProjectileRadius();
        double offsetX = radiusSum * Math.cos(angleRadians);
        double offsetY = radiusSum * Math.sin(angleRadians);

        double projectileX = owner.node.getTranslateX() + offsetX;
        double projectileY = owner.node.getTranslateY() + offsetY;

        double projectileVX = velocity * Math.cos(angleRadians);
        double projectileVY = velocity * Math.sin(angleRadians);

        projectile = createProjectile(projectileX, projectileY, projectileVX, projectileVY);

        return projectile;
    }

    private Projectile createProjectile(double x, double y, double vX, double vY) {
        return projectileBuilder.build(x, y, vX, vY);
    }

    private double getProjectileRadius() {
        return projectileBuilder.build(0,0,0,0).radius;
    }

}
