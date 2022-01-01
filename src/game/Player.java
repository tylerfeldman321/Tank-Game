package game;

import engine.GameWorld;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Player extends Tank {
    double playerHealth = 10;

    final BooleanProperty leftPressed = new SimpleBooleanProperty(false);
    final BooleanProperty rightPressed = new SimpleBooleanProperty(false);
    final BooleanProperty upPressed = new SimpleBooleanProperty(false);
    final BooleanProperty downPressed = new SimpleBooleanProperty(false);
    final BooleanProperty firePressed = new SimpleBooleanProperty(false);
    final BooleanProperty minePressed = new SimpleBooleanProperty(false);

    public Player(GameWorld gameWorld, double centerX, double centerY) {
        super(gameWorld, centerX, centerY);
        initializeHealth(playerHealth);
    }

    @Override
    public void update(GameWorld gameWorld) {
        if (leftPressed.get()) {
            if (downPressed.get()) turnCCW();
            else turnCW();
        }
        if (rightPressed.get()) {
            if (downPressed.get()) turnCW();
            else turnCCW();
        }
        if (upPressed.get()) moveForward();
        if (downPressed.get()) moveBackward();

        // Fire weapon every frame if the current weapon is rapid fire and the user is pressing down on the fire button
        if (firePressed.get() && weaponRackIsSet && weaponRack.currentWeaponIsRapidFire()) {
            TankGameWorld tankGameWorld = (TankGameWorld) gameWorld;
            if (tankGameWorld.isMouseAim()) fireWeapon(tankGameWorld.getCurrentMouseX(), tankGameWorld.getCurrentMouseY());
            else fireWeapon();
        }
    }

    @Override
    public void handleDeath(GameWorld gameWorld) {
        gameWorld.getSpriteManager().addSpritesToBeRemoved(this);
    }
}
