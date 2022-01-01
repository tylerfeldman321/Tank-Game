package game.projectiles;

import engine.GameWorld;
import engine.Sprite;
import game.walls.Wall;
import game.weapons.Weapon;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Projectile extends Sprite {

    public Weapon weapon;

    /**
     * Whether this will bounce off of walls
     */
    public boolean bounce;
    public double radius;
    public boolean collidesWithOtherProjectiles;
    public int numChildren;
    public Projectile childProjectile;
    public double childVelocity;
    public Color color;

    public Projectile(Weapon weapon, double radius, double centerX, double centerY, double vX, double vY, double damage, double health,
                      boolean isInvincible, double lifetime, boolean bounce, boolean collidesWithOtherProjectiles,
                      int numChildren, Projectile childProjectile, double childVelocity, Color color) {
        Rectangle rect = new Rectangle();
        rect.setTranslateX(centerX - radius);
        rect.setTranslateY(centerY - radius);
        rect.setWidth(2 * radius);
        rect.setHeight(2 * radius);
        collisionBounds = rect;

        Circle circle = new Circle();
        circle.setRadius(radius);
        circle.setTranslateX(centerX);
        circle.setTranslateY(centerY);
        circle.setFill(color);
        node = circle;

        // Initialize values
        this.vX = vX;
        this.vY = vY;
        initializeHealth(health);
        this.damage = damage;
        this.isInvincible = isInvincible;
        this.lifetime = lifetime;
        this.bounce = bounce;
        this.radius = radius;
        this.collidesWithOtherProjectiles = collidesWithOtherProjectiles;
        this.weapon = weapon;
        this.numChildren = numChildren;
        this.childProjectile = childProjectile;
        this.childVelocity = childVelocity;
        this.color = color;
    }

    @Override
    public void update(GameWorld gameWorld) {
        updatePosition(vX, vY);
        checkDuration(gameWorld);
    }

    @Override
    public void handleDeath(GameWorld gameWorld) {
        super.handleDeath(gameWorld);
        if (weapon != null) {
            weapon.decrementNumProjectiles();
        }
        createChildren();
    }

    private void createChildren() {
        if (numChildren <= 0 || childProjectile == null) return;
        double angleBetweenChildren = 360 / (double)numChildren;
        Weapon childWeapon = new Weapon(weapon.gameWorld, 0, childProjectile, Double.MAX_VALUE,
                Integer.MAX_VALUE, Integer.MAX_VALUE, 1, childVelocity, 0, false);
        double childAngle;
        for (int i = 0; i < numChildren; i++) {
            childAngle = angleBetweenChildren * i;
            childWeapon.fire(childAngle, this.node.getTranslateX(), this.node.getTranslateY());
        }
    }

    public Projectile copy(Weapon weapon, double centerX, double centerY, double vX, double vY) {
        return new Projectile(weapon, radius, centerX, centerY, vX, vY, damage, maxHP, isInvincible, lifetime, bounce,
                collidesWithOtherProjectiles, numChildren, childProjectile, childVelocity, color);
    }

    @Override
    public void collision(Sprite other, GameWorld gameWorld) {
        if (other instanceof Projectile && (!collidesWithOtherProjectiles || !((Projectile) other).collidesWithOtherProjectiles)) {
            return;
        }
        else if (other instanceof Wall && bounce) {
            bounceOffWall((Wall)other);
            return;
        }
        else {
            super.collision(other, gameWorld);
        }
    }

    private void bounceOffWall(Wall other) {

        Rectangle wallBounds = other.collisionBounds;
        Rectangle bulletBounds = collisionBounds;

        double intersectionUpper = Math.max(wallBounds.getTranslateY(), bulletBounds.getTranslateY());
        double intersectionLower = Math.min(wallBounds.getTranslateY()+wallBounds.getHeight(),
                bulletBounds.getTranslateY()+wallBounds.getHeight());
        double intersectionLeft = Math.max(wallBounds.getTranslateX(), bulletBounds.getTranslateX());
        double intersectionRight = Math.min(wallBounds.getTranslateX()+wallBounds.getWidth(),
                bulletBounds.getTranslateX()+wallBounds.getWidth());
        double intersectionHeight = intersectionLower - intersectionUpper;
        double intersectionWidth = intersectionRight - intersectionLeft;

        if (intersectionHeight > intersectionWidth) {
            if (vX >= 0) setXPosition(wallBounds.getTranslateX() - 2*radius);
            else setXPosition(wallBounds.getTranslateX() + wallBounds.getWidth() + 2*radius);
            vX = -vX;
        } else {
            if (vY >= 0) setYPosition(wallBounds.getTranslateY() - 2*radius);
            else setYPosition(wallBounds.getTranslateY() + wallBounds.getHeight() + 2*radius);
            vY = -vY;
        }
    }
}
