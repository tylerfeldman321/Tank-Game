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
        if (weaponRackIsSet) {
            if (weaponRack.currentWeaponIsRapidFire() && firePressed.get()) {
                fireWeapon(((TankGameWorld) gameWorld).currentMouseX, ((TankGameWorld) gameWorld).currentMouseY);
            }
        }

    }

    @Override
    public void handleDeath(GameWorld gameWorld) {
        gameWorld.getSpriteManager().addSpritesToBeRemoved(this);
        // On death, signal game end
    }
}
