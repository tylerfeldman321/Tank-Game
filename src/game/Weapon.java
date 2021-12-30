package game;

import java.util.HashSet;
import java.util.Random;
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
    public int maxNumProjectiles;

    /**
     * Current number of projectiles that this
     */
    public int currentNumProjectiles = 0;

    /**
     * How many projectiles are shot at once
     */
    public int projectilesPerShot;

    /**
     * Total velocity of projectile
     */
    public double velocity;

    /**
     * Range of angles that the weapon will fire in
     */
    public double projectileSpreadDegrees;

    /**
     * Rapidfire? i.e. does this continuously shoot while holding the key down
     */
    public boolean rapidFire;

    /**
     * How much extra distance away to spawn the bullet so that it doesn't collide with the tank firing it
     */
    private double padding = 1;

    public Weapon(Tank owner, ProjectileBuilder.ProjectileType projectileType, double rateOfFire, int maxProjectiles,
                  int projectilesPerShot, double velocity, double projectileSpreadDegrees, boolean rapidFire) {
        this.owner = owner;
        this.projectileBuilder = new ProjectileBuilder(projectileType);
        this.rateOfFire = rateOfFire;
        this.maxNumProjectiles = maxProjectiles;
        this.projectilesPerShot = projectilesPerShot;
        this.velocity = velocity;
        this.projectileSpreadDegrees = projectileSpreadDegrees;
        this.rapidFire = rapidFire;
    }

    public Projectile fire(double x, double y) {
        double deltaX = owner.node.getTranslateX() - x;
        double deltaY = owner.node.getTranslateY() - y;
        double angleDegrees = Math.toDegrees(Math.atan2(deltaY, deltaX) + Math.PI);
        return fire(angleDegrees);
    }

    public Projectile fire(double angleDegrees) {

        Random random = new Random();

        // Will be uniformly distributed between -projectileSpreadDegrees/2 and projectileSpreadDegrees/2
        double angleDeviation = (random.nextDouble() - 0.5) * projectileSpreadDegrees;
        angleDegrees += angleDeviation;

        double angleRadians = Math.toRadians(angleDegrees);

        Projectile projectile;

        double radiusSum = owner.tankRadius + getProjectileRadius() + padding;
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
