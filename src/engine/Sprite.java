package engine;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

/**
 * The Sprite class represents a image or node to be displayed.
 * In a 2D game a sprite will contain a velocity for the image to
 * move across the scene area. The game loop will call the update()
 * and collide() method at every interval of a key frame. A list of
 * animations can be used during different situations in the game
 * such as rocket thrusters, walking, jumping, etc.
 *
 * @author cdea
 */
public abstract class Sprite {

    /**
     * Current display node
     */
    public Node node;

    /**
     * velocity vector x direction
     */
    public double vX = 0;

    /**
     * velocity vector y direction
     */
    public double vY = 0;

    /**
     * dead?
     */
    public boolean isDead = false;

    /**
     * collision shape
     */
    public Rectangle collisionBounds;

    /**
     * Invincible, won't take damage
     */
    public boolean isInvincible = false;

    /**
     * Maximum hit points
     */
    public double maxHP = 100;

    /**
     * Current hit points
     */
    public double currentHP = 100;

    /**
     * Damage caused to another object when
     */
    public double damage = 0;

    /**
     * Duration in seconds. If -1, then it will last until collision
     */
    public double lifetime = -1;

    /**
     * Time of initialization in seconds
     */
    public double initializationTime = -1;

    /**
     * Updates this sprite object's velocity, or animations.
     */
    public abstract void update(GameWorld gameWorld);


    /**
     * Did this sprite collide into the other sprite?
     *
     * @param other - The other sprite.
     * @return boolean - Whether this or the other sprite collided, otherwise false.
     */
    public boolean collide(Sprite other) {

        if (collisionBounds == null || other.collisionBounds == null) {
            return false;
        }

        Rectangle otherRect = other.collisionBounds;
        Rectangle thisRect = collisionBounds;

        return !(thisRect.getTranslateX() + thisRect.getWidth() < otherRect.getTranslateX() ||
                thisRect.getTranslateY() + thisRect.getHeight() < otherRect.getTranslateY() ||
                thisRect.getTranslateX() > otherRect.getTranslateX() + otherRect.getWidth() ||
                thisRect.getTranslateY() > otherRect.getTranslateY() + otherRect.getHeight());
    }

    protected void checkDuration(GameWorld gameWorld) {
        double currentTime = gameWorld.getSecondsElapsed();
        if (initializationTime == -1) initializationTime = currentTime;
        if (lifetime != -1) {
            double timeAlive = currentTime - initializationTime;
            if (timeAlive > lifetime) handleDeath(gameWorld);
        }
    }

    // Update position of both the node (which is displayed, and the collision boundary)
    public void updatePosition(double distanceX, double distanceY) {
        node.setTranslateX(node.getTranslateX() + distanceX);
        node.setTranslateY(node.getTranslateY() + distanceY);
        collisionBounds.setTranslateX(collisionBounds.getTranslateX() + distanceX);
        collisionBounds.setTranslateY(collisionBounds.getTranslateY() + distanceY);
    }

    public void setXPosition(double x) {
        node.setTranslateX(x);
        collisionBounds.setTranslateX(x);
    }

    public void setYPosition(double y) {
        node.setTranslateY(y);
        collisionBounds.setTranslateY(y);
    }

    public void initializeHealth(double health) {
        maxHP = health;
        currentHP = health;
    }

    public void handleDeath(GameWorld gameWorld) {
        gameWorld.getSpriteManager().addSpritesToBeRemoved(this);
    }

    protected void updateCurrentHealth(double damage) {
        if (!isInvincible) {
            currentHP -= damage;
            if (currentHP <= 0) isDead = true;
        }
    }

    public void collision(Sprite other, GameWorld gameWorld) {
        updateCurrentHealth(other.damage);
        if (isDead) handleDeath(gameWorld);
    }

    public boolean isAlive() {
        return !isDead;
    }
}