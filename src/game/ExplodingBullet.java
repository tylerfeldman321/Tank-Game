package game;

import engine.GameWorld;
import javafx.scene.paint.Color;

public class ExplodingBullet extends Projectile {

    private double childVelocity = 1;

    public ExplodingBullet(Weapon weapon, double centerX, double centerY, double vX, double vY) {
        super(weapon,10, centerX, centerY, vX, vY, 10, 0, false, 2, true,
                true, Color.GREENYELLOW);
    }

    @Override
    public void handleDeath(GameWorld gameWorld) {
        super.handleDeath(gameWorld);
        // Create four new bullets in all four directions
        Projectile leftBullet = new BasicBullet(null,this.node.getTranslateX()-10, this.node.getTranslateY(), -childVelocity+this.vX, this.vY);
        Projectile rightBullet = new BasicBullet(null,this.node.getTranslateX()+10, this.node.getTranslateY(), childVelocity+this.vX, this.vY);
        Projectile upBullet = new BasicBullet(null, this.node.getTranslateX(),
                this.node.getTranslateY()-10, this.vX, -childVelocity+this.vY);
        Projectile downBullet = new BasicBullet(null,
                this.node.getTranslateX(), this.node.getTranslateY()+10, this.vX, childVelocity+this.vY);
        gameWorld.getSpriteManager().addSpritesToBeAdded(leftBullet, rightBullet, upBullet, downBullet);
    }

    @Override
    public Projectile copy(Weapon weapon, double centerX, double centerY, double vX, double vY) {
        return new ExplodingBullet(weapon, centerX, centerY, vX, vY);
    }
}
