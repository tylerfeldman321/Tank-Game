package game;

import engine.GameWorld;
import engine.Sprite;

import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.event.*;
import javafx.scene.input.*;

public class TankGameWorld extends GameWorld {

    private final double wallWidth = 5;
    private final boolean mouseAim = false;
    public Player myPlayer;

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

        myPlayer = new Player(300, 300);
        addSprites(myPlayer);
    }

    private void setupInput(Stage primaryStage) {
        EventHandler<MouseEvent> fire = null;
        if (mouseAim) {
            fire = event -> {
                if (event.getButton() == MouseButton.PRIMARY && myPlayer.isAlive()) {
                    Bullet bullet = myPlayer.fire(event.getX(), event.getY());
                    addSprites(bullet);
                }
            };
        } else {
            fire = event -> {
                if (event.getButton() == MouseButton.PRIMARY && myPlayer.isAlive()) {
                    Bullet bullet = myPlayer.fire();
                    addSprites(bullet);
                }
            };
        }
        primaryStage.getScene().setOnMousePressed(fire);

        EventHandler<KeyEvent> move = keyEvent -> {
            if (keyEvent.getCode() == KeyCode.W) {
                // Move forward
                myPlayer.move(3);
            }
            if (keyEvent.getCode() == KeyCode.S) {
                // Move backward
                myPlayer.move(-3);
            }
            if (keyEvent.getCode() == KeyCode.A) {
                // Rotate CCW
                myPlayer.turn(-5);
            }
            if (keyEvent.getCode() == KeyCode.D) {
                // Rotate CW
                myPlayer.turn(5);
            }
        };
        primaryStage.getScene().setOnKeyPressed(move);
    }

    @Override
    protected void handleUpdate(Sprite sprite) {
        sprite.update();
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
