package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import bricker.gameobjects.Ball;
import bricker.gameobjects.MockPaddle;
import bricker.gameobjects.Paddle;

/**
 * Class for one of the brick's collision strategies.
 * in this strategy - after a collision, the brick disappears and a new
 * temporary mock-paddle appears in the middle of the screen. after the mock
 * paddle hits the ball or the pucks 4 times, it disappears.
 */
public class AnotherPaddleStrategy implements CollisionStrategyDecorator{

    // private variables
    private final BasicCollisionStrategy basicCollisionStrategy;
    private final BrickerGameManager brickerGameManager;
    private final ImageReader imageReader;
    private final UserInputListener inputListener;

    /**
     * Constructor.
     *
     * @param brickerGameManager - a reference to this Bricker game manager.
     * @param imageReader - helper object to process images
     * @param inputListener - helper object to get keyboard inputs
     */
    public AnotherPaddleStrategy(BrickerGameManager brickerGameManager, ImageReader imageReader,
                                 UserInputListener inputListener) {
        this.basicCollisionStrategy = new BasicCollisionStrategy(brickerGameManager);
        this.brickerGameManager = brickerGameManager;
        this.imageReader = imageReader;
        this.inputListener = inputListener;
    }

    /**
     * defines this game behavior whenever a ball objects collide with a certain
     * brick - adding a temporary mock paddle to the game.
     * Further explanations in this class documentation.
     *
     * @param brickObject - a reference for a brick object
     * @param ballObject - a reference for a ball object
     */
    @Override
    public void onCollision(GameObject brickObject, GameObject ballObject) {
        // remove the brick (basic collision behavior)
        basicCollisionStrategy.onCollision(brickObject, ballObject);

        // if it doesn't exist already - adding a mock paddle
        if (!MockPaddle.isExistsAlready){
            this.createAnotherPaddle();
        }
    }

    /*
    sub-method for onCollision.
    creates another mock-paddle and adds it to the game's objects.
     */
    private void createAnotherPaddle(){
        Renderable paddleImg = imageReader.readImage("assets/paddle.png",true);
        MockPaddle mockPaddle = new MockPaddle(Vector2.ZERO, Constants.DIM_OF_PADDLE,
                paddleImg,inputListener, this.brickerGameManager);
        mockPaddle.setCenter(new Vector2(brickerGameManager.getWindowDimensions().x()/2,
                brickerGameManager.getWindowDimensions().y()/2));
        this.brickerGameManager.addObjectToThisGame(mockPaddle);
    }

}
