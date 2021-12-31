package game;

import engine.GameWorld;
import engine.Sprite;

import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.List;

public class TankGameWorld extends GameWorld {

    private final double wallWidth = 5;
    private final boolean mouseAim = false;
    private Player myPlayer;

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

        setupInput(primaryStage);

        myPlayer = new Player(this,300, 300);
        myPlayer.setWeaponRack(WeaponType.BASIC, WeaponType.SHOTGUN, WeaponType.EXPLOSIVE_LAUNCHER);
        addSprites(myPlayer);
    }

    private void setupInput(Stage primaryStage) {
        setupMouseInput(primaryStage);
        setupKeyInput(primaryStage);
    }

    private void setupMouseInput(Stage primaryStage) {
        EventHandler<MouseEvent> fire = null;
        if (mouseAim) {
            fire = event -> {
                if (event.getButton() == MouseButton.PRIMARY && myPlayer.isAlive()) {
                    myPlayer.fireWeapon(event.getX(), event.getY());
                }
            };
        } else {
            fire = event -> {
                if (event.getButton() == MouseButton.PRIMARY && myPlayer.isAlive()) {
                    myPlayer.fireWeapon();
                }
            };
        }
        primaryStage.getScene().setOnMousePressed(fire);
    }

    private void setupKeyInput(Stage primaryStage) {
        EventHandler<KeyEvent> keyPressed = keyEvent -> {
            if (keyEvent.getCode() == KeyCode.A) {
                myPlayer.leftPressed.set(true);
            } else if (keyEvent.getCode() == KeyCode.D) {
                myPlayer.rightPressed.set(true);
            } else if (keyEvent.getCode() == KeyCode.W) {
                myPlayer.upPressed.set(true);
            } else if (keyEvent.getCode() == KeyCode.S) {
                myPlayer.downPressed.set(true);
            }

            if (keyEvent.getCode() == KeyCode.DIGIT1) {
                myPlayer.swapWeapon(1);
            } else if (keyEvent.getCode() == KeyCode.DIGIT2) {
                myPlayer.swapWeapon(2);
            } else if (keyEvent.getCode() == KeyCode.DIGIT3) {
                myPlayer.swapWeapon(3);
            } else if (keyEvent.getCode() == KeyCode.DIGIT4) {
                myPlayer.swapWeapon(4);
            }
        };
        EventHandler<KeyEvent> keyReleased = keyEvent -> {
            if (keyEvent.getCode() == KeyCode.A) {
                myPlayer.leftPressed.set(false);
            } else if (keyEvent.getCode() == KeyCode.D) {
                myPlayer.rightPressed.set(false);
            } else if (keyEvent.getCode() == KeyCode.W) {
                myPlayer.upPressed.set(false);
            } else if (keyEvent.getCode() == KeyCode.S) {
                myPlayer.downPressed.set(false);
            }
        };

        primaryStage.getScene().setOnKeyPressed(keyPressed);
        primaryStage.getScene().setOnKeyReleased(keyReleased);
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
        // Build walls around the map
        buildMapBorder();
    }

    private void buildMapBorder() {
        Wall leftWall = new Wall(0, 0, 0, getGameSurface().getHeight(), wallWidth);
        Wall rightWall = new Wall(getGameSurface().getWidth()-wallWidth, 0, getGameSurface().getWidth()-wallWidth, getGameSurface().getHeight(), wallWidth);
        Wall topWall = new Wall(0, 0, getGameSurface().getWidth(), 0, wallWidth);
        Wall bottomWall = new Wall(0, getGameSurface().getHeight()-wallWidth, getGameSurface().getWidth(), getGameSurface().getHeight()-wallWidth, wallWidth);
        addSprites(leftWall, rightWall, topWall, bottomWall);
    }
}
