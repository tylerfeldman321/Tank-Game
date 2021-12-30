package game;

import engine.GameWorld;
import javafx.scene.paint.Color;

public class ExplodingBullet extends Projectile {

    private double childVelocity = 1;

    public ExplodingBullet(double centerX, double centerY, double vX, double vY) {
        super(5, centerX, centerY, vX, vY, 10, 0, false, 2, true,
                false, Color.GREENYELLOW);
    }

    @Override
    public void handleDeath(GameWorld gameWorld) {
        gameWorld.getSpriteManager().addSpritesToBeRemoved(this);
        // Create four new bullets in all four directions
        Projectile leftBullet = new BasicBullet(this.node.getTranslateX()-10, this.node.getTranslateY(), -childVelocity+this.vX, this.vY);
        Projectile rightBullet = new BasicBullet(this.node.getTranslateX()+10, this.node.getTranslateY(), childVelocity+this.vX, this.vY);
        Projectile upBullet = new BasicBullet(this.node.getTranslateX(), this.node.getTranslateY()-10, this.vX, -childVelocity+this.vY);
        Projectile downBullet = new BasicBullet(this.node.getTranslateX(), this.node.getTranslateY()+10, this.vX, childVelocity+this.vY);
        gameWorld.getSpriteManager().addSpritesToBeAdded(leftBullet, rightBullet, upBullet, downBullet);
    }
}
