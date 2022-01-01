package game;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Input {
    private Stage stage;
    private TankGameWorld tankGameWorld;
    private boolean mouseAim;
    private Player player;

    public Input(TankGameWorld tankGameWorld, Player player, Stage stage) {
        this.stage = stage;
        this.tankGameWorld = tankGameWorld;
        this.mouseAim = tankGameWorld.isMouseAim();
        this.player = player;
    }

    public void setup() {
        setupMouseInput();
        setupKeyInput();
    }

    private void setupMouseInput() {
        if (mouseAim) setupMouseAimControls();
    }

    private void setupMouseAimControls() {
        EventHandler<MouseEvent> fire = event -> {
            if (event.getButton() == MouseButton.PRIMARY && player.isAlive()) {
                player.fireWeapon(event.getX(), event.getY());
            }
            player.firePressed.set(true);
            setCurrentMouseXAndY(event);
        };

        EventHandler<MouseEvent> releaseMouse = event -> {
            player.firePressed.set(false);
        };

        EventHandler<MouseEvent> dragMouse = event -> {
            setCurrentMouseXAndY(event);
        };

        stage.getScene().setOnMousePressed(fire);
        stage.getScene().setOnMouseReleased(releaseMouse);
        stage.getScene().setOnMouseDragged(dragMouse);
    }

    private void setCurrentMouseXAndY(MouseEvent mouseEvent) {
        tankGameWorld.setCurrentMouseX(mouseEvent.getX());
        tankGameWorld.setCurrentMouseY(mouseEvent.getY());
    }

    private void setupKeyInput() {
        EventHandler<KeyEvent> keyPressed = keyEvent -> {
            movementControls(keyEvent, true);
            weaponSwapControls(keyEvent);
            mineControls(keyEvent, true);
            if (!mouseAim) nonMouseAimControls(keyEvent, true);
        };

        EventHandler<KeyEvent> keyReleased = keyEvent -> {
            movementControls(keyEvent, false);
            mineControls(keyEvent, false);
            if (!mouseAim) nonMouseAimControls(keyEvent, false);
        };

        stage.getScene().setOnKeyPressed(keyPressed);
        stage.getScene().setOnKeyReleased(keyReleased);
    }

    private void movementControls(KeyEvent keyEvent, boolean pressed) {
        if (keyEvent.getCode() == KeyCode.A) {
            player.leftPressed.set(pressed);
        } else if (keyEvent.getCode() == KeyCode.D) {
            player.rightPressed.set(pressed);
        } else if (keyEvent.getCode() == KeyCode.W) {
            player.upPressed.set(pressed);
        } else if (keyEvent.getCode() == KeyCode.S) {
            player.downPressed.set(pressed);
        }
    }

    private void weaponSwapControls(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.DIGIT1) {
            player.swapWeapon(1);
        } else if (keyEvent.getCode() == KeyCode.DIGIT2) {
            player.swapWeapon(2);
        } else if (keyEvent.getCode() == KeyCode.DIGIT3) {
            player.swapWeapon(3);
        } else if (keyEvent.getCode() == KeyCode.DIGIT4) {
            player.swapWeapon(4);
        } else if (keyEvent.getCode() == KeyCode.DIGIT5) {
            player.swapWeapon(5);
        } else if (keyEvent.getCode() == KeyCode.DIGIT6) {
            player.swapWeapon(6);
        } else if (keyEvent.getCode() == KeyCode.DIGIT7) {
            player.swapWeapon(7);
        } else if (keyEvent.getCode() == KeyCode.DIGIT8) {
            player.swapWeapon(8);
        }
    }

    private void nonMouseAimControls(KeyEvent keyEvent, boolean pressed) {
        if (keyEvent.getCode() == KeyCode.SPACE && player.isAlive()) {
            if (pressed && !player.firePressed.get()) player.fireWeapon();
            player.firePressed.set(pressed);
        }
    }

    private void mineControls(KeyEvent keyEvent, boolean pressed) {
        if (keyEvent.getCode() == KeyCode.SHIFT && player.isAlive()) {
            if (pressed && !player.minePressed.get()) player.placeMine();
            player.minePressed.set(pressed);
        }
    }
}
