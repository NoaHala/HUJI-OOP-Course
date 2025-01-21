package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * represent a heart object in the 'Bricker' game.
 * used as a helper for the life bonus strategy.
 */
public class Heart extends GameObject {

    // private variables
    private final BrickerGameManager brickerGameManager;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param brickerGameManager a reference for this game manager
     */
    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 BrickerGameManager brickerGameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.brickerGameManager = brickerGameManager;
    }

    /**
     * redefine the heart to collide only with the main paddle
     * @param other The other GameObject.
     * @return true if other is the main paddle
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return other.getTag().equals("Paddle");
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
        if (shouldCollideWith(other)){
            if(brickerGameManager.getGameLives() < 4){
            brickerGameManager.increaseLives();
            }
            brickerGameManager.removeObjectFromThisGame(this);
        }
    }
}
