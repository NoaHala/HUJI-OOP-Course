package bricker.gameobjects;

import bricker.main.Constants;
import bricker.brick_strategies.CameraStrategy;
import danogl.GameObject;
import danogl.util.Vector2;

/**
 * represent a special counter object in the 'Bricker' game.
 * used as a helper for the camera strategy - counts down the main
 * ball's collisions and turn off the behavior when it gets to 0.
 */
public class BallCollisionsCounterForCamera extends GameObject {

    // private fields
    private int counterAtTheBeginning;
    private Ball ball;
    private final CameraStrategy cameraStrategy;

    /**
     * Construct a new BallCollisionsCounterForCamera instance.
     *
     * @param ball - a ball instance to follow
     * @param cameraStrategy - the instance of camera strategy that created this object.
     */
    public BallCollisionsCounterForCamera(Ball ball, CameraStrategy cameraStrategy) {
        super(Vector2.ZERO, Vector2.ZERO, null);
        this.counterAtTheBeginning = ball.getCollisionCounter();
        this.ball =ball;
        this.cameraStrategy = cameraStrategy;
    }

    /**
     * adding for the general update - if the counter reached to 4, iy turns off the strategy
     * @param deltaTime The time elapsed, in seconds, since the last frame. Can
     *                  be used to determine a new position/velocity by multiplying
     *                  this delta with the velocity/acceleration respectively
     *                  and adding to the position/velocity:
     *                  velocity += deltaTime*acceleration
     *                  pos += deltaTime*velocity
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (this.ball.getCollisionCounter() >=
                counterAtTheBeginning + Constants.MAX_NUM_OF_COLLISIONS){
            cameraStrategy.resetCamera();
        }

    }
}
