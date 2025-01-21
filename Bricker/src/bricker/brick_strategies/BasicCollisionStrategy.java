package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Layer;

/**
 * Class for the basic brick's collision strategies.
 * in this strategy - after a collision, the brick disappears.
 */
public class BasicCollisionStrategy implements CollisionStrategy{

    // private variables
    private final BrickerGameManager brickerGameManager;

    /**
     * Constructor.
     *
     * @param brickerGameManager - a reference to this Bricker game manager.
     */
    public BasicCollisionStrategy(BrickerGameManager brickerGameManager) {
        this.brickerGameManager = brickerGameManager;
    }

    /**
     * defines this game behavior whenever a ball objects collide with a certain
     * brick - removes the brick.
     *
     * @param brickObject - a reference for a brick object
     * @param ballObject - a reference for a ball object
     */
    @Override
    public void onCollision(GameObject brickObject, GameObject ballObject) {
        brickerGameManager.removeObjectFromThisGame(brickObject, Layer.STATIC_OBJECTS);
        brickerGameManager.getRemainingBrickCounter().decrement();
        //System.out.println("collision with brick detected");
    }
}
