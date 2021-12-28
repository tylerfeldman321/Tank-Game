package game;

import engine.GameWorld;

public class Player extends Tank {
    double playerHealth = 10;

    public Player(double centerX, double centerY) {
        super(centerX, centerY);
        initalizeHealth(playerHealth);
    }

    @Override
    public void handleDeath(GameWorld gameWorld) {
        gameWorld.getSpriteManager().addSpritesToBeRemoved(this);
    }
}
