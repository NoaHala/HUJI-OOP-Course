package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * represent a special paddle (mock paddle) object in the 'Bricker' game.
 * used as a helper for the another-paddle-strategy.
 */
public class MockPaddle extends Paddle{

    // private fields
    private final BrickerGameManager brickerGameManager;
    private int collisionsCounter = 0;

    /**
     * field to check if a mock paddle is already exists
     */
    public static boolean isExistsAlready = false;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner      Position of the object, in window coordinates (pixels).
     *                           Note that (0,0) is the top-left corner of the window.
     * @param dimensions         Width and height in window coordinates.
     * @param renderable         The renderable representing the object. Can be null, in which case
     *                           the GameObject will not be rendered.
     * @param inputListener - a helper object to process keyboard inputs
     * @param brickerGameManager - a reference for this game manager
     */
    public MockPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                      UserInputListener inputListener, BrickerGameManager brickerGameManager) {
        super(topLeftCorner, dimensions, renderable, inputListener, brickerGameManager.getWindowDimensions());
        this.brickerGameManager = brickerGameManager;
        isExistsAlready = true;
    }

    /**
     * action that should be executed each time this object collide with another
     *
     * @param other The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionsCounter++;
        if (collisionsCounter >= Constants.MAX_NUM_OF_COLLISIONS){
            brickerGameManager.removeObjectFromThisGame(this);
            isExistsAlready = false;
        }
    }

    /**
     * a getter for that object name tag
     * @return the string "MockPaddle"
     */
    @Override
    public String getTag() {
        return "MockPaddle";
    }
}
