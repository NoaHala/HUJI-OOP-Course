package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * represent a brick object in the 'Bricker' game
 */
public class Brick extends GameObject {

    // private fields
    private final CollisionStrategy collisionStrategy;

    /**
     * Construct a new Brick instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param collisionStrategy an object that defines the game behavior when a ball hits this brick
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 CollisionStrategy collisionStrategy) {
        super(topLeftCorner, dimensions, renderable);

        this.collisionStrategy = collisionStrategy;
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
        this.collisionStrategy.onCollision(this,other);
    }

    /**
     * a getter for that object name tag
     * @return the string "Brick"
     */
    @Override
    public String getTag() {
        return "Brick";
    }
}
