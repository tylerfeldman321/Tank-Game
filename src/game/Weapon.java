package game;

public abstract class Weapon {
    /**
     * Shots allowed per second
     */
    public double rateOfFire = 0;

    /**
     * What kind of projectiles it shoots
     */

    /**
     * How many of these projectiles are allowed to exist at one time
     */
    public int maxProjectiles;

    /**
     * How many projectiles are shot at once
     */
    public int projectilesPerShot;

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
    public void fire() { }


}
