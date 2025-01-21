package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.util.Vector2;
import bricker.gameobjects.Brick;
import bricker.gameobjects.MockPaddle;

import java.util.Random;

/**
 * Class for one of the brick's collision strategies.
 * in this strategy - after a collision, the brick disappears and 2 new
 * special strategies executed. this strategy can be used recursively
 * with the limit of 3 strategies for a single brick.
 */
public class DoubleStrategy implements CollisionStrategyDecorator{

    //private variables
    private final BrickerGameManager brickerGameManager;
    private final CollisionStrategy[] strategies = {null, null, null};


    /**
     * Constructor.
     *
     * @param brickerGameManager - a reference to this Bricker game manager.
     * @param strategyFactory - the factory that chooses and creates collision strategies
     */
    public DoubleStrategy(BrickerGameManager brickerGameManager, StrategyFactory strategyFactory) {
        this.brickerGameManager = brickerGameManager;

        for (int i = 0; i < howManyStrategies(); i++) {
            this.strategies[i] = strategyFactory.getStrategy(StrategyOptions.SPECIAL_NO_DOUBLE);
        }
    }

    /*
    sub-method for constructor - calculates the probability for each amount of strategies for
     this brick
     */
    private int howManyStrategies(){
        Random rand = new Random();
        if (rand.nextInt(Constants.SPECIAL_STRATEGIES_NUM) == 0 ||
                rand.nextInt(Constants.SPECIAL_STRATEGIES_NUM) == 0) {
            return Constants.MAX_STRATEGIES_FOR_BRICK;
        }
        else return Constants.TWO_STRATEGIES;
    }

    /**
     * defines this game behavior whenever a ball objects collide with a certain
     * brick - chooses 2 more special behaviors.
     * Further explanations in this class documentation.
     *
     * @param brickObject - a reference for a brick object
     * @param ballObject - a reference for a ball object
     */
    @Override
    public void onCollision(GameObject brickObject, GameObject ballObject) {
        for (int i = 1; i < Constants.MAX_STRATEGIES_FOR_BRICK; i++) {
            if (strategies[i] != null){
                Brick helperBrick = new Brick(brickObject.getTopLeftCorner(),brickObject.getDimensions(),
                        null, null);
                this.brickerGameManager.addObjectToThisGame(helperBrick);
                strategies[i].onCollision(helperBrick,ballObject);
            }
        }
        strategies[0].onCollision(brickObject,ballObject);
    }
}
