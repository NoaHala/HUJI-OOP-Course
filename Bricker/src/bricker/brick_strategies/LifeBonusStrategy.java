package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import bricker.gameobjects.Ball;
import bricker.gameobjects.Heart;

/**
 * Class for one of the brick's collision strategies.
 * in this strategy - after a collision, the brick disappears and a life-heart
 * falls from its place. by catching this heart with the paddle, the player
 * can get extra life for the game, with the limit of 4 lives max.
 */
public class LifeBonusStrategy implements CollisionStrategyDecorator{

    // private variables
    private final BrickerGameManager brickerGameManager;
    private final ImageReader imageReader;
    private final BasicCollisionStrategy basicCollisionStrategy;

    /**
     * Constructor.
     *
     * @param brickerGameManager - a reference to this Bricker game manager.
     * @param imageReader - helper object to process images
     */
    public LifeBonusStrategy(BrickerGameManager brickerGameManager, ImageReader imageReader) {
        this.basicCollisionStrategy = new BasicCollisionStrategy(brickerGameManager);
        this.brickerGameManager = brickerGameManager;
        this.imageReader = imageReader;
    }

    /**
     * defines this game behavior whenever a ball objects collide with a certain
     * brick - creates life bonus object to catch.
     * Further explanations in this class documentation.
     *
     * @param brickObject - a reference for a brick object
     * @param ballObject - a reference for a ball object
     */
    @Override
    public void onCollision(GameObject brickObject, GameObject ballObject) {
        // remove the brick (basic collision behavior)
        basicCollisionStrategy.onCollision(brickObject, ballObject);

        // create the special heart object and adds it to the game
        createHeart(brickObject);
    }

    /*
    sub-method for onCollision to create the heart object
     */
    private void createHeart(GameObject brickObject) {
        Renderable heartImg = imageReader.readImage("assets/heart.png",true);
        Heart heart = new Heart(Vector2.ZERO, Constants.DIM_OF_HEART, heartImg, brickerGameManager);
        heart.setCenter(brickObject.getCenter());
        heart.setVelocity(new Vector2(0,Constants.SPEED_OF_LIFE_BONUS));

        brickerGameManager.addObjectToThisGame(heart);
    }
}
