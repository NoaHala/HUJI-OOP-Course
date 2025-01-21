package bricker.gameobjects;

import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.Random;

/**
 * represent a ball object in the 'Bricker' game
 */
public class Ball extends GameObject {

    // private fields
    private final Sound collisionSound;
    private final Counter collisionCounter = new Counter(0);

    /**
     * Construct a new ball instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param collisionSound helper object for processing sounds
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
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
        collisionCounter.increaseBy(1);
        Vector2 newVel = getVelocity().flipped(collision.getNormal());
        setVelocity(newVel);
        collisionSound.play();
    }

    /**
     * a Getter for private field
     * @return the int value of this.collisionCounter
     */
    public int getCollisionCounter() {
        return collisionCounter.value();
    }

    /**
     * a getter for that object name tag
     * @return the string "Ball"
     */
    @Override
    public String getTag() {
        return "Ball";
    }

    /**
     * reset the ball's velocity to random diagonal line (4 options)
     */
    public void resetVelocity() {
        float ballVelX = Constants.BALL_SPEED;
        float ballVelY = Constants.BALL_SPEED;
        Random rand = new Random();
        if (rand.nextBoolean()){
            ballVelX *= -1;
        }
        if (rand.nextBoolean()){
            ballVelY *= -1;
        }
        this.setVelocity(new Vector2(ballVelX, ballVelY));
    }
}
