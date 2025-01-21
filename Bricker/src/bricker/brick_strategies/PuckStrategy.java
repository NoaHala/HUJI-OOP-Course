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
import bricker.gameobjects.Puck;

import java.util.Random;

/**
 * Class for one of the brick's collision strategies.
 * in this strategy - after a collision, the brick disappears and 2 new
 * pucks (mock balls) appear in its place.
 */
public class PuckStrategy implements CollisionStrategyDecorator{

    // private variables
    private final BrickerGameManager brickerGameManager;
    private final BasicCollisionStrategy basicCollisionStrategy;
    private final ImageReader imageReader;
    private final SoundReader soundReader;
    private final Puck[] twoPucks = new Puck[2];

    /**
     * Constructor.
     *
     * @param brickerGameManager - a reference to this Bricker game manager.
     * @param imageReader - helper object to process images
     * @param soundReader - helper object to process sounds
     */
    public PuckStrategy(BrickerGameManager brickerGameManager, ImageReader imageReader,
                        SoundReader soundReader) {
        this.basicCollisionStrategy = new BasicCollisionStrategy(brickerGameManager);
        this.brickerGameManager = brickerGameManager;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
    }


    /**
     * defines this game behavior whenever a ball objects collide with a certain
     * brick - creates 2 pucks.
     * Further explanations in this class documentation.
     *
     * @param brickObject - a reference for a brick object
     * @param ballObject - a reference for a ball object
     */
    @Override
    public void onCollision(GameObject brickObject, GameObject ballObject) {
        // remove the brick (basic collision behavior)
        basicCollisionStrategy.onCollision(brickObject, ballObject);

        // create the puck objects and adds them to the game
        createPucks(brickObject);
    }

    /*
    sub-method for onCollision that creates and adds 2 pucks to the game's objects.
     */
    private void createPucks(GameObject brickObject){
        Renderable puckImage = imageReader.readImage("assets/mockBall.png",true);
        Sound collisionSound = soundReader.readSound("assets/blop_cut_silenced.wav");
        for (int i = 0; i < 2; i++) {
            twoPucks[i] = new Puck(Vector2.ZERO, Constants.DIM_OF_PUCK_BALL, puckImage,collisionSound);
            resetPuck(twoPucks[i], brickObject);
            this.brickerGameManager.addObjectToThisGame(twoPucks[i]);
        }
    }

    /*
    sub-method for createPucks that sets their placement and velocity.
     */
    private void resetPuck(Puck puck,GameObject brickObject) {
        puck.setCenter(brickObject.getCenter());
        puck.resetVelocity();
    }
}
