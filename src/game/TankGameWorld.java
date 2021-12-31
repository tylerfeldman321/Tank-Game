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

        setupInput(primaryStage);

        myPlayer = new Player(this,300, 300);
        myPlayer.setWeaponRack(WeaponType.BASIC, WeaponType.SHOTGUN, WeaponType.EXPLOSIVE_LAUNCHER, WeaponType.MACHINE_GUN);
        addSprites(myPlayer);
    }

    private void setupInput(Stage primaryStage) {
        setupMouseInput(primaryStage);
        setupKeyInput(primaryStage);
    }

    private void setupMouseInput(Stage primaryStage) {
        if (mouseAim) setupMouseAimControls(primaryStage);
    }

    private void setupMouseAimControls(Stage primaryStage) {
        EventHandler<MouseEvent> fire = event -> {
            if (event.getButton() == MouseButton.PRIMARY && myPlayer.isAlive()) {
                myPlayer.fireWeapon(event.getX(), event.getY());
            }
            myPlayer.firePressed.set(true);
            currentMouseX = event.getX();
            currentMouseY = event.getY();
        };

        EventHandler<MouseEvent> releaseMouse = event -> {
            myPlayer.firePressed.set(false);
        };

        EventHandler<MouseEvent> dragMouse = event -> {
            currentMouseX = event.getX();
            currentMouseY = event.getY();
        };

        primaryStage.getScene().setOnMousePressed(fire);
        primaryStage.getScene().setOnMouseReleased(releaseMouse);
        primaryStage.getScene().setOnMouseDragged(dragMouse);
    }

    private void setupKeyInput(Stage primaryStage) {
        EventHandler<KeyEvent> keyPressed = keyEvent -> {
            movementControls(keyEvent, true);
            weaponSwapControls(keyEvent);
            if (!mouseAim) nonMouseAimControls(keyEvent, true);
        };

        EventHandler<KeyEvent> keyReleased = keyEvent -> {
            movementControls(keyEvent, false);
            if (!mouseAim) nonMouseAimControls(keyEvent, false);
        };

        primaryStage.getScene().setOnKeyPressed(keyPressed);
        primaryStage.getScene().setOnKeyReleased(keyReleased);
    }

    private void movementControls(KeyEvent keyEvent, boolean pressed) {
        if (keyEvent.getCode() == KeyCode.A) {
            myPlayer.leftPressed.set(pressed);
        } else if (keyEvent.getCode() == KeyCode.D) {
            myPlayer.rightPressed.set(pressed);
        } else if (keyEvent.getCode() == KeyCode.W) {
            myPlayer.upPressed.set(pressed);
        } else if (keyEvent.getCode() == KeyCode.S) {
            myPlayer.downPressed.set(pressed);
        }
    }

    private void weaponSwapControls(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.DIGIT1) {
            myPlayer.swapWeapon(1);
        } else if (keyEvent.getCode() == KeyCode.DIGIT2) {
            myPlayer.swapWeapon(2);
        } else if (keyEvent.getCode() == KeyCode.DIGIT3) {
            myPlayer.swapWeapon(3);
        } else if (keyEvent.getCode() == KeyCode.DIGIT4) {
            myPlayer.swapWeapon(4);
        } else if (keyEvent.getCode() == KeyCode.DIGIT5) {
            myPlayer.swapWeapon(5);
        } else if (keyEvent.getCode() == KeyCode.DIGIT6) {
            myPlayer.swapWeapon(6);
        } else if (keyEvent.getCode() == KeyCode.DIGIT7) {
            myPlayer.swapWeapon(7);
        } else if (keyEvent.getCode() == KeyCode.DIGIT8) {
            myPlayer.swapWeapon(8);
        }
    }

    private void nonMouseAimControls(KeyEvent keyEvent, boolean pressed) {
        if (keyEvent.getCode() == KeyCode.SPACE) {
            if (pressed && !myPlayer.firePressed.get()) myPlayer.fireWeapon();
            myPlayer.firePressed.set(pressed);
        }
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

    public boolean isMouseAim() {
        return mouseAim;
    }

    public double getCurrentMouseX() {
        return currentMouseX;
    }

    public double getCurrentMouseY() {
        return currentMouseY;
    }
}
