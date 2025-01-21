package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;
import bricker.gameobjects.Ball;
import bricker.gameobjects.BallCollisionsCounterForCamera;

/**
 * Class for one of the brick's collision strategies.
 * in this strategy - after a collision, the brick disappears and if
 * it got hit by the main ball, the camera focus sets on it for its
 * next 4 collisions.
 */
public class CameraStrategy implements CollisionStrategyDecorator{

    //private variables
    private final BasicCollisionStrategy basicCollisionStrategy;
    private final BrickerGameManager brickerGameManager;
    private BallCollisionsCounterForCamera helperCounter;

    /**
     * Constructor.
     *
     * @param brickerGameManager - a reference to this Bricker game manager.
     */
    public CameraStrategy(BrickerGameManager brickerGameManager) {
        this.basicCollisionStrategy = new BasicCollisionStrategy(brickerGameManager);
        this.brickerGameManager = brickerGameManager;
    }

    /**
     * defines this game behavior whenever a ball objects collide with a certain
     * brick - temporarily changing the camera focus to the main ball.
     * Further explanations in this class documentation.
     *
     * @param brickObject - a reference for a brick object
     * @param ballObject - a reference for a ball object
     */
    @Override
    public void onCollision(GameObject brickObject, GameObject ballObject) {
        // remove the brick (basic collision behavior)
        basicCollisionStrategy.onCollision(brickObject, ballObject);

        // if hit by the main ball and the strategy isn't applied already
        if(brickerGameManager.camera() == null && ballObject.getTag().equals("Ball")){

            // sets the camera focus to the ball
            brickerGameManager.setCamera(
                    new Camera(
                            ballObject,
                            Vector2.ZERO,
                            brickerGameManager.getWindowDimensions().mult(1.2f),
                            brickerGameManager.getWindowDimensions())
            );

            /* sets special counter object that will turn off the behavior after
            requested number of ball collisions (can be set at the constants class) */
           this.helperCounter = new BallCollisionsCounterForCamera((Ball) ballObject, this);
           brickerGameManager.addObjectToThisGame(this.helperCounter);
        }
    }

    /**
     * resets the camera focus back to normal and remove the helper counter from the game objects.
     * used from the special collision counter object - 'BallCollisionsCounterForCamera'
     */
    public void resetCamera() {
        brickerGameManager.setCamera(null);
        brickerGameManager.removeObjectFromThisGame(this.helperCounter);
    }
}
