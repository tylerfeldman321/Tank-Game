package game;

import javafx.application.*;
import engine.GameWorld;
import javafx.stage.Stage;

public class TankGameLoop extends Application {

    GameWorld gameWorld = new TankGameWorld(60, "Tank Game by TyFel");
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // setup title, scene, stats, controls, and actors.
        gameWorld.initialize(primaryStage);

        // kick off the game loop
        gameWorld.beginGameLoop();

        // display window
        primaryStage.show();
    }

}
