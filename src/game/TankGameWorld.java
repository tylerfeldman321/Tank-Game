package game;

import engine.GameWorld;
import engine.Sprite;

import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;

public class TankGameWorld extends GameWorld {

    private final double wallWidth = 5;
    private final boolean mouseAim = false;
    private Player myPlayer;
    private double currentMouseX = 0;
    private double currentMouseY = 0;

    public TankGameWorld(int fps, String title) {
        super(fps, title);
    }

    @Override
    public void initialize(final Stage primaryStage) {
        // Set window title
        primaryStage.setTitle(getWindowTitle());

        // Create the scene
        setSceneNodes(new Group());
        setGameSurface(new Scene(getSceneNodes(), 640, 580));
        primaryStage.setScene(getGameSurface());

        // Build the map
        buildMap();

        myPlayer = new Player(this,300, 300);
        myPlayer.setWeaponRack(WeaponType.BASIC, WeaponType.SHOTGUN, WeaponType.EXPLOSIVE_LAUNCHER, WeaponType.MACHINE_GUN);
        addSprites(myPlayer);
        setupInput(primaryStage);
    }

    public void setupInput(Stage primaryStage) {
        Input input = new Input(this, myPlayer, primaryStage);
        input.setup();
    }

    @Override
    protected void handleUpdate(Sprite sprite) {
        sprite.update(this);
    }

    @Override
    protected boolean handleCollision(Sprite spriteA, Sprite spriteB) {
        // Check that the sprites are not the same
        if (spriteA == spriteB || !spriteA.node.isVisible() || !spriteB.node.isVisible()) {
            return false;
        }
        // If both are walls, then skip
        if (spriteA instanceof Wall && spriteB instanceof Wall) return false;

        if (spriteA.collide(spriteB)) {
            spriteA.collision(spriteB, this);
            spriteB.collision(spriteA, this);
            return true;
        }
        return false;
    }

    private void buildMap() {
        buildMapBorder();
    }

    private void buildMapBorder() {
        Wall leftWall = new Wall(0, 0, 0, getGameSurface().getHeight(), wallWidth);
        Wall rightWall = new Wall(getGameSurface().getWidth()-wallWidth, 0, getGameSurface().getWidth()-wallWidth, getGameSurface().getHeight(), wallWidth);
        Wall topWall = new Wall(0, 0, getGameSurface().getWidth(), 0, wallWidth);
        Wall bottomWall = new Wall(0, getGameSurface().getHeight()-wallWidth, getGameSurface().getWidth(), getGameSurface().getHeight()-wallWidth, wallWidth);
        addSprites(leftWall, rightWall, topWall, bottomWall);
    }

    public boolean isMouseAim() {
        return mouseAim;
    }

    public double getCurrentMouseX() {
        return currentMouseX;
    }

    public void setCurrentMouseX(double currentMouseX) {
        this.currentMouseX = currentMouseX;
    }

    public double getCurrentMouseY() {
        return currentMouseY;
    }

    public void setCurrentMouseY(double currentMouseY) {
        this.currentMouseY = currentMouseY;
    }

    public Player getMyPlayer() {
        return myPlayer;
    }
}
