package game;

import engine.GameWorld;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Weapon {

    /**
     * The GameWorld. Need this to keep track of seconds between shots
     */
    public GameWorld gameWorld;

    /**
     * Tank that owns this weapon
     */
    public Tank owner;

    /**
     * Shots allowed per second
     */
    public double rateOfFire;

    /**
     * Time between shots
     */
    public double timeAllowedBetweenShots;

    /**
     * Time of previous shot
     */
    public double timeOfPreviousShot = 0;

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
     * Max number of shots able to be fired
     */
    public int maxAmmo;

    /**
     * Current number of shots fired so far
     */
    public int currentAmmo;

    /**
     * How many projectiles are shot at once
     */
    public int numProjectilesPerShot;

    /**
     * Total velocity of projectile
     */
    public double velocity;

    /**
     * Range of angles that the weapon will fire in
     */
    public double projectileSpreadDegrees;

    /**
     * How much extra distance away to spawn the bullet so that it doesn't collide with the tank firing it
     */
    private double padding = 2;

    /**
     * Radius of projectile
     */
    private double radius;

    /**
     * Radius to shoot bullets from firing location to prevent collisions with firer
     */
    private double radiusFromFiringLocation;

    public Weapon(GameWorld gameWorld, double radiusFromFiringLocation,
                  Projectile projectile, double rateOfFire, int maxProjectiles, int maxAmmo,
                  int numProjectilesPerShot, double velocity, double projectileSpreadDegrees) {
        this.gameWorld = gameWorld;
        this.projectileBuilder = new ProjectileBuilder(projectile, this);
        this.rateOfFire = rateOfFire;
        this.maxNumProjectiles = maxProjectiles;
        this.numProjectilesPerShot = numProjectilesPerShot;
        this.velocity = velocity;
        this.projectileSpreadDegrees = projectileSpreadDegrees;
        this.radius = projectile.radius;
        this.maxAmmo = maxAmmo;
        this.currentAmmo = maxAmmo;
        this.radiusFromFiringLocation = radiusFromFiringLocation;

        if (rateOfFire < 0) {
            // Throw error
        } else if (rateOfFire == 0) {
            timeAllowedBetweenShots = Double.MAX_VALUE;
        } else {
            this.timeAllowedBetweenShots = 1 / rateOfFire;
        }
    }

    public void fire(double x, double y, double firingLocationX, double firingLocationY) {
        double deltaX = firingLocationX - x;
        double deltaY = firingLocationY - y;
        double angleDegrees = Math.toDegrees(Math.atan2(deltaY, deltaX) + Math.PI);
        fire(angleDegrees, firingLocationX, firingLocationY);
    }

    public void fire(double angleDegrees, double firingLocationX, double firingLocationY) {
        if (cantFire()) return;
        incrementNumProjectiles();
        for (int i = 0; i < numProjectilesPerShot; i++) {
            fireSingleProjectile(angleDegrees, firingLocationX, firingLocationY);
        }
    }

    private void fireSingleProjectile(double angleDegrees, double firingLocationX, double firingLocationY) {
        Random random = new Random();

        double angleDeviation = (random.nextDouble() - 0.5) * projectileSpreadDegrees;
        angleDegrees += angleDeviation;

        double angleRadians = Math.toRadians(angleDegrees);

        Projectile projectile;

        double radiusSum = radiusFromFiringLocation + radius + padding;
        double offsetX = radiusSum * Math.cos(angleRadians);
        double offsetY = radiusSum * Math.sin(angleRadians);

        double projectileX = firingLocationX + offsetX;
        double projectileY = firingLocationY + offsetY;

        double projectileVX = velocity * Math.cos(angleRadians);
        double projectileVY = velocity * Math.sin(angleRadians);

        projectile = createProjectile(projectileX, projectileY, projectileVX, projectileVY);

        gameWorld.addSprites(projectile);
    }

    public void setGameWorldAndOwnerRadius(GameWorld gameWorld, double radiusFromFiringLocation) {
        this.gameWorld = gameWorld;
        this.radiusFromFiringLocation = radiusFromFiringLocation;
    }

    private boolean cantFire() {
        if (needToWaitToFireAgain()) return true;
        if (tooManyProjectiles()) return true;
        if (outOfAmmo()) return true;
        return false;
    }

    private boolean needToWaitToFireAgain() {
        double currentTime = gameWorld.getSecondsElapsed();
        double timeFromPreviousShot = currentTime - timeOfPreviousShot;
        if (timeFromPreviousShot < timeAllowedBetweenShots) {
            return true;
        }
        timeOfPreviousShot = currentTime;
        return false;
    }

    private boolean tooManyProjectiles() {
        if (currentNumProjectiles >= maxNumProjectiles) {
            return true;
        }
        return false;
    }

    private boolean outOfAmmo() {
        if (currentAmmo <= 0) {
            return true;
        }
        return false;
    }

    private Projectile createProjectile(double x, double y, double vX, double vY) {
        return projectileBuilder.build(x, y, vX, vY);
    }

    private void incrementNumProjectiles() {
        currentNumProjectiles++;
        currentAmmo--;
    }

    public void decrementNumProjectiles() {
        currentNumProjectiles--;
    }

}
