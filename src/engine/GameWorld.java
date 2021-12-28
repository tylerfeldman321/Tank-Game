package engine;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
// import javafx.animation.TimelineBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;

/**
 * This application demonstrates a JavaFX 2.x Game Loop.
 * Shown below are the methods which comprise of the fundamentals to a
 * simple game loop in JavaFX:
 * <pre>
 *  <b>initialize()</b> - Initialize the game world.
 *  <b>beginGameLoop()</b> - Creates a JavaFX Timeline object containing the game life cycle.
 *  <b>updateSprites()</b> - Updates the sprite objects each period (per frame)
 *  <b>checkCollisions()</b> - Method will determine objects that collide with each other.
 *  <b>cleanupSprites()</b> - Any sprite objects needing to be removed from play.
 * </pre>
 *
 * @author cdea
 */
public abstract class GameWorld {

    /**
     * The JavaFX Scene as the game surface
     */
    private Scene gameSurface;
    /**
     * All nodes to be displayed in the game window.
     */
    private Group sceneNodes;
    /**
     * The game loop using JavaFX's <code>Timeline</code> API.
     */
    private static Timeline gameLoop;

    /**
     * Number of frames per second.
     */
    private final int framesPerSecond;

    /**
     * Title in the application window.
     */
    private final String windowTitle;

    /**
     * The sprite manager.
     */
    private final SpriteManager spriteManager = new SpriteManager();

    private final SoundManager soundManager = new SoundManager(3);

    /**
     * Seconds elapsed according to the number of frames ran
     */
    private double secondsElapsed = 0;

    /**
     * Constructor that is called by the derived class. This will
     * set the frames per second, title, and setup the game loop.
     *
     * @param fps   - Frames per second.
     * @param title - Title of the application window.
     */
    public GameWorld(final int fps, final String title) {
        framesPerSecond = fps;
        windowTitle = title;
        // create and set timeline for the game loop
        buildAndSetGameLoop();
    }

    /**
     * Builds and sets the game loop ready to be started.
     */
    protected final void buildAndSetGameLoop() {

        final Duration oneFrameAmt = Duration.millis(1000 / (float) getFramesPerSecond());
        final KeyFrame oneFrame = new KeyFrame(oneFrameAmt,
                new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(javafx.event.ActionEvent event) {

                        // update actors
                        updateSprites();

                        // check for collision
                        checkCollisions();

                        // removed dead things
                        cleanupSprites();

                        secondsElapsed+=(1/(double)framesPerSecond);
                    }
                }); // oneFrame

        final Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.getKeyFrames().add(oneFrame);
        setGameLoop(timeline);
    }

    /**
     * Initialize the game world by update the JavaFX Stage.
     *
     * @param primaryStage The main window containing the JavaFX Scene.
     */
    public abstract void initialize(final Stage primaryStage);

    /**
     * Kicks off (plays) the Timeline objects containing one key frame
     * that simply runs indefinitely with each frame invoking a method
     * to update sprite objects, check for collisions, and cleanup sprite
     * objects.
     */
    public void beginGameLoop() {
        getGameLoop().play();
    }

    /**
     * Updates each game sprite in the game world. This method will
     * loop through each sprite and passing it to the handleUpdate()
     * method. The derived class should override handleUpdate() method.
     */
    protected void updateSprites() {
        for (Sprite sprite : spriteManager.getAllSprites()) {
            handleUpdate(sprite);
        }
    }

    /**
     * Updates the sprite object's information to position on the game surface.
     *
     * @param sprite - The sprite to update.
     */
    protected void handleUpdate(Sprite sprite) {
        sprite.update(this);
    }

    /**
     * Checks each game sprite in the game world to determine a collision
     * occurred. The method will loop through each sprite and
     * passing it to the handleCollision()
     * method. The derived class should override handleCollision() method.
     */
    protected void checkCollisions() {
        // check other sprite's collisions
        spriteManager.resetCollisionsToCheck();
        // check each sprite against other sprite objects.

        // both lists here are the same since we called resetCollisionsToCheck
        Sprite spriteA;
        Sprite spriteB;
        for (int i = 0; i < spriteManager.getCollisionsToCheck().size(); i++) {
            spriteA = spriteManager.getCollisionsToCheck().get(i);
            for (int j = i + 1; j < spriteManager.getAllSprites().size(); j++) {
                spriteB = spriteManager.getAllSprites().get(j);
                if (handleCollision(spriteA, spriteB)) {
                    break;
                }
            }
        }
    }

    /**
     * When two objects collide this method can handle the passed in sprite
     * objects. By default it returns false, meaning the objects do not
     * collide.
     *
     * @param spriteA - called from checkCollision() method to be compared.
     * @param spriteB - called from checkCollision() method to be compared.
     * @return boolean True if the objects collided, otherwise false.
     */
    protected boolean handleCollision(Sprite spriteA, Sprite spriteB) {
        return false;
    }

    /**
     * Sprites to be cleaned up.
     */
    protected void cleanupSprites() {
        // Remove the sprite from the scene so that it isn't displayed
        for (Sprite sprite: spriteManager.getSpritesToBeRemoved()) {
            getSceneNodes().getChildren().remove(sprite.node);
        }
        // Remove the sprite from the sprite manager, meaning it won't check collisions or update it
        spriteManager.cleanupSprites();
    }

    /**
     * Returns the frames per second.
     *
     * @return int The frames per second.
     */
    protected int getFramesPerSecond() {
        return framesPerSecond;
    }

    /**
     * Returns the game's window title.
     *
     * @return String The game's window title.
     */
    public String getWindowTitle() {
        return windowTitle;
    }

    /**
     * The game loop (Timeline) which is used to update, check collisions, and
     * cleanup sprite objects at every interval (fps).
     *
     * @return Timeline An animation running indefinitely representing the game
     *         loop.
     */
    protected static Timeline getGameLoop() {
        return gameLoop;
    }

    /**
     * The sets the current game loop for this game world.
     *
     * @param gameLoop Timeline object of an animation running indefinitely
     *                 representing the game loop.
     */
    protected static void setGameLoop(Timeline gameLoop) {
        GameWorld.gameLoop = gameLoop;
    }

    /**
     * Returns the sprite manager containing the sprite objects to
     * manipulate in the game.
     *
     * @return SpriteManager The sprite manager.
     */
    public SpriteManager getSpriteManager() {
        return spriteManager;
    }

    /**
     * Returns the JavaFX Scene. This is called the game surface to
     * allow the developer to add JavaFX Node objects onto the Scene.
     *
     * @return Scene The JavaFX scene graph.
     */
    public Scene getGameSurface() {
        return gameSurface;
    }

    /**
     * Sets the JavaFX Scene. This is called the game surface to
     * allow the developer to add JavaFX Node objects onto the Scene.
     *
     * @param gameSurface The main game surface (JavaFX Scene).
     */
    protected void setGameSurface(Scene gameSurface) {
        this.gameSurface = gameSurface;
    }

    /**
     * All JavaFX nodes which are rendered onto the game surface(Scene) is
     * a JavaFX Group object.
     *
     * @return Group The root containing many child nodes to be displayed into
     *         the Scene area.
     */
    public Group getSceneNodes() {
        return sceneNodes;
    }

    /**
     * Sets the JavaFX Group that will hold all JavaFX nodes which are rendered
     * onto the game surface(Scene) is a JavaFX Group object.
     *
     * @param sceneNodes The root container having many children nodes
     *                   to be displayed into the Scene area.
     */
    protected void setSceneNodes(Group sceneNodes) {
        this.sceneNodes = sceneNodes;
    }

    protected SoundManager getSoundManager() {
        return soundManager;
    }

    /**
     * Stop threads and stop media from playing.
     */
    public void shutdown() {
        getGameLoop().stop();
        getSoundManager().shutdown();
    }

    public double getSecondsElapsed() {
        return secondsElapsed;
    }

    /**
     * Adds sprites to the sprite manager so that it's updated and checked for collisions and adds
     * the sprites to the scene nodes so that they are displayed in the scene
     *
     * @param sprites Variable number of sprites that will be added
     */
    protected void addSprites(Sprite... sprites) {
        getSpriteManager().addSprites(sprites);
        for (Sprite sprite : Arrays.asList(sprites)) {
            getSceneNodes().getChildren().add(0, sprite.node);
        }
    }
}