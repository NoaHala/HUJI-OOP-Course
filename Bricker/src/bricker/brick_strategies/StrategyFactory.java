package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.util.Vector2;

import java.util.Random;
//import static Bricker.StrategyOptions.*;

/**
 * Factory for choosing collision strategy for a brick.
 * also used to regenerate strategies at the double-strategy case.
 */
public class StrategyFactory {

    // private variables
    private final BrickerGameManager brickerGameManager;
    private final ImageReader imageReader;
    private final SoundReader soundReader;
    private final UserInputListener inputListener;


    /**
     * Constructor.
     * @param brickerGameManager - a reference to this Bricker game manager.
     * @param imageReader - helper object to process images
     * @param soundReader - helper object to process sounds
     * @param inputListener - helper object to get keyboard inputs
     */
    public StrategyFactory(BrickerGameManager brickerGameManager, ImageReader imageReader,
                           SoundReader soundReader, UserInputListener inputListener) {
        this.brickerGameManager = brickerGameManager;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
    }

    /**
     * a getter for random collision strategy object (according to the requested probabilities).
     * @param strategyOptions - enum that tells what will be the options for the chosen strategy.
     * @return new CollisionStrategy object.
     */
    public CollisionStrategy getStrategy(StrategyOptions strategyOptions){
        Random rand = new Random();
        int randomInt = switch (strategyOptions) {
            case ALL -> rand.nextInt(Constants.SPECIAL_STRATEGIES_NUM * 2);
            case SPECIAL -> rand.nextInt(Constants.SPECIAL_STRATEGIES_NUM);
            case SPECIAL_NO_DOUBLE -> rand.nextInt(Constants.SPECIAL_STRATEGIES_NUM - 1);
        };

        return switch (randomInt){
            case (0) -> new PuckStrategy(this.brickerGameManager,imageReader,soundReader);
            case (1) -> new AnotherPaddleStrategy(this.brickerGameManager,imageReader,inputListener);
            case (2) -> new CameraStrategy(this.brickerGameManager);
            case (3) -> new LifeBonusStrategy(this.brickerGameManager,imageReader);
            case (Constants.SPECIAL_STRATEGIES_NUM-1) ->
                    new DoubleStrategy(this.brickerGameManager, this);
            default -> new BasicCollisionStrategy(this.brickerGameManager);
        };
    }


}
