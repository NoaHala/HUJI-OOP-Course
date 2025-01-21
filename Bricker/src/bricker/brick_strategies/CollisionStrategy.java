package bricker.brick_strategies;

import danogl.GameObject;

/**
 *  interface for different brick's strategies to deal with collisions
 */
public interface CollisionStrategy {

    /**
     * defines the game behavior whenever a ball objects collide with a certain brick.
     * @param brickObject - a reference for a brick object
     * @param ballObject - a reference for a ball object
     */
    void onCollision(GameObject brickObject, GameObject ballObject);
}
