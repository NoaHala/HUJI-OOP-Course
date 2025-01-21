package bricker.main;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;
import bricker.gameobjects.HeartsLifeCounter;
import bricker.gameobjects.NumericLifeCounter;

/**
 * class of all the constants values used in this project
 */
public class Constants {

    /**
     * Constructor.
     */
    public Constants() {
    }

    /***********************************
     ****      BALL CONSTANTS       ****
     ***********************************/

    /**
     * BALL_SPEED constant
     */
    public static final float BALL_SPEED = 200;

    /**
     * DIM_OF_BALL constant
     */
    public static final Vector2 DIM_OF_BALL = new Vector2(20, 20);

    /**
     * DIM_OF_PUCK_BALL constant
     */
    public static final Vector2 DIM_OF_PUCK_BALL = new Vector2(DIM_OF_BALL.x()*0.75f, DIM_OF_BALL.y()*0.75f);

    /**
     * MAX_NUM_OF_COLLISIONS constant
     */
    public static final int MAX_NUM_OF_COLLISIONS = 4;


    /***********************************
     ****     PADDLE CONSTANTS      ****
     ***********************************/

    /**
     * DIM_OF_PADDLE constant
     */
    public static final Vector2 DIM_OF_PADDLE = new Vector2(100, 15);

    /**
     * PADDLE_SPACE_FROM_BOTTOM constant
     */
    public static final int PADDLE_SPACE_FROM_BOTTOM = 40;


    /***********************************
     ****      BRICK CONSTANTS      ****
     ***********************************/

    /**
     * BRICK_HEIGHT constant
     */
    public static final int BRICK_HEIGHT = 15;

    /**
     * SPACE_BETWEEN_BRICKS constant
     */
    public static final int SPACE_BETWEEN_BRICKS = 3;

    /**
     * DEFAULT_NUM_OF_BRICKS_IN_LINE constant
     */
    public static final int DEFAULT_NUM_OF_BRICKS_IN_LINE = 8;

    /**
     * DEFAULT_NUM_OF_BRICKS_LINES constant
     */
    public static final int DEFAULT_NUM_OF_BRICKS_LINES = 7;

    /**
     * MAX_STRATEGIES_FOR_BRICK constant
     */
    public static final int MAX_STRATEGIES_FOR_BRICK = 3;


    /***********************************
     ****     BOARDER CONSTANTS     ****
     ***********************************/

    /**
     * BORDER_WIDTH constant
     */
    public static final int BORDER_WIDTH = 5;

    /**
     * SPACE_FROM_THE_BORDERS constant
     */
    public static final int SPACE_FROM_THE_BORDERS = 3;


    /***********************************
     ****      LIFE CONSTANTS       ****
     ***********************************/

    /**
     * DEFAULT_GAME_LIVES constant
     */
    public static final int DEFAULT_GAME_LIVES = 3;

    /**
     * DIM_OF_HEART constant
     */
    public static final Vector2 DIM_OF_HEART = new Vector2(20, 20);

    /**
     * DIM_OF_LIVES_NUM constant
     */
    public static final Vector2 DIM_OF_LIVES_NUM = new Vector2(20, 20);

    /**
     * SPACE_FOR_LIVES constant
     */
    public static final int SPACE_FOR_LIVES = 3;

    /**
     * LIVES_SPACE_FROM_BOTTOM constant
     */
    public static final int LIVES_SPACE_FROM_BOTTOM = 15;

    /**
     * MAX_NUM_OF_LIFE constant
     */
    public static final int MAX_NUM_OF_LIFE = 4;

    /**
     * SPEED_OF_LIFE_BONUS constant
     */
    public static final int SPEED_OF_LIFE_BONUS = 100;



    /***********************************
     ****   STRATEGIES CONSTANTS    ****
     ***********************************/

    /**
     * SPECIAL_STRATEGIES_NUM constant
     */
    public static final int SPECIAL_STRATEGIES_NUM = 5;

    /**
     * TWO_STRATEGIES constant
     */
    public static final int TWO_STRATEGIES = 2;
}
