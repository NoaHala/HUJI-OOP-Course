package bricker.main;

import bricker.brick_strategies.BasicCollisionStrategy;
import bricker.brick_strategies.StrategyFactory;
import bricker.brick_strategies.StrategyOptions;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import bricker.gameobjects.*;
import bricker.brick_strategies.*;
//import Bricker.gameobjects.Ball;
//import Bricker.gameobjects.Brick;
//import Bricker.gameobjects.HeartsLifeCounter;
//import Bricker.gameobjects.NumericLifeCounter;
//import Bricker.gameobjects.Paddle;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * represents the game-manager for a 'Bricker' game.
 * used for executing the game.
 */
public class BrickerGameManager extends GameManager{

    // ball field
    private Ball ball;

    // brick field
    private final int numberOfBricksInLine;
    private final int numberOfLines;
    private static final danogl.util.Counter remainingBrickCounter = new danogl.util.Counter(0);

    // window constants and field
    private Vector2 windowDimensions;
    private WindowController windowController;
    private UserInputListener inputListener;
    private ImageReader imageReader;
    private SoundReader soundReader;

    // life field
    private int gameLives;
    private NumericLifeCounter numericLifeCounter;
    private HeartsLifeCounter heartsLifeCounter;

    /**
     * Constructor.
     *
     * @param windowTitle - window title
     * @param windowDimensions - vector of the window dimensions
     * @param numberOfBricksInLine - number of bricks on each line in the game
     * @param numberOfLines - number of bricks lines in the game
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions,
                              int numberOfBricksInLine, int numberOfLines) {
        super(windowTitle, windowDimensions);
        this.numberOfBricksInLine = numberOfBricksInLine;
        this.numberOfLines = numberOfLines;
    }

    /**
     * The method will be called once when a GameGUIComponent is created, and again after
     * every invocation of windowController.resetGame().
     *
     * @param imageReader Contains a single method: readImage, which reads an image from disk.
     *                 See its documentation for help.
     * @param soundReader Contains a single method: readSound, which reads a wav file from
     *                    disk. See its documentation for help.
     * @param inputListener Contains a single method: isKeyPressed, which returns whether
     *                      a given key is currently pressed by the user or not. See its
     *                      documentation.
     * @param windowController Contains an array of helpful, self-explanatory methods
     *                         concerning the window.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.windowController = windowController;
        this.windowDimensions = windowController.getWindowDimensions();
        gameLives = Constants.DEFAULT_GAME_LIVES;
        remainingBrickCounter.reset();

        //creating background
        createBackground();

        //creating ball
        createBall();

        //creating paddle
        createPaddle();

        //creating walls
        createWalls();

        //creating the bricks
        createBricksWall();

        //creating lives counters
        createLivesCounter();
    }

    /**
     * adding for the general update - checks fow winning conditions or objects that got out of the screen
     * @param deltaTime The time, in seconds, that passed since the last invocation
     *                  of this method (i.e., since the last frame). This is useful
     *                  for either accumulating the total time that passed since some
     *                  event, or for physics integration (i.e., multiply this by
     *                  the acceleration to get an estimate of the added velocity or
     *                  by the velocity to get an estimate of the difference in position).
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkForGameEnd();
        removeObjectsOutOfTheWindow();
    }

    /**
     * method to remove objects from a specific game.
     * make sure all adding / removing objects are being called throw the game manager.
     * @param gameObject - object to remove
     */
    public void removeObjectFromThisGame(GameObject gameObject){
        if(gameObject.getTag().equals("Brick")){
            this.gameObjects().removeGameObject(gameObject, Layer.STATIC_OBJECTS);
        }
        else if (gameObject.getTag().equals("LifeCounters")){
            this.gameObjects().removeGameObject(gameObject, Layer.UI);
        }
        else {
            this.gameObjects().removeGameObject(gameObject);
        }
    }

    /**
     * method to remove objects (in a specific layer) from a specific game.
     * make sure all adding / removing objects are being called throw the game manager.
     * @param gameObject - object to remove
     */
    public void removeObjectFromThisGame(GameObject gameObject, int layer){
        this.gameObjects().removeGameObject(gameObject, layer);
    }

    /**
     * method to add objects from a specific game.
     * make sure all adding / removing objects are being called throw the game manager.
     * @param gameObject - object to add
     */
    public void addObjectToThisGame(GameObject gameObject){
        if(gameObject.getTag().equals("Brick")){
            this.gameObjects().addGameObject(gameObject, Layer.STATIC_OBJECTS);
        }
        else if (gameObject.getTag().equals("LifeCounters")){
            this.gameObjects().addGameObject(gameObject, Layer.UI);
        }
        else {
            this.gameObjects().addGameObject(gameObject);
        }
    }

    /**
     * method to add objects (from a specific layer) from a specific game.
     * make sure all adding / removing objects are being called throw the game manager.
     * @param gameObject - object to add
     */
    public void addObjectToThisGame(GameObject gameObject, int layer){
        this.gameObjects().addGameObject(gameObject, layer);
    }

    /**
     * method to increase the player life in one
     * make sure all life-counters changes are being called throw the game manager.
     */
    public void increaseLives(){
        gameLives++;
        this.heartsLifeCounter.addLife();
        this.numericLifeCounter.addLife();
    }


    /***********************************
     ****          GETTERS          ****
     ***********************************/

    /**
     * a Getter for private field
     * @return this.remainingBrickCounter
     */
    public Counter getRemainingBrickCounter() {
        return remainingBrickCounter;
    }

    /**
     * a Getter for private field
     * @return this.gameLives
     */
    public int getGameLives() {
        return gameLives;
    }

    /**
     * a Getter for private field
     * @return this.windowDimensions
     */
    public Vector2 getWindowDimensions() {
        return windowDimensions;
    }


    /***********************************
     ****    GAME OBJECTS CREATORS  ****
     ***********************************/

   /*
   sub-method of 'initializeGame' - creates the ball object.
    */
    private void createBall(){
        Renderable ballImage = imageReader.readImage("assets/ball.png",true);
        Sound collisionSound = soundReader.readSound("assets/blop_cut_silenced.wav");
        Ball ball = new Ball(Vector2.ZERO, Constants.DIM_OF_BALL, ballImage,collisionSound);
        this.ball = ball;

        //setting the direction and speed that the ball start with
        resetBall();

        gameObjects().addGameObject(ball);
    }

    /*
   sub-method of 'createBall' - reset its place and velocity
    */
    private void resetBall() {
        ball.setCenter(windowDimensions.mult(0.5f));
        ball.resetVelocity();
    }

    /*
   sub-method of 'initializeGame' - creates the paddle object.
    */
    private void createPaddle(){
        Renderable paddleImage = imageReader.readImage(
                "assets/paddle.png", true);
        GameObject paddle = new Paddle(Vector2.ZERO, Constants.DIM_OF_PADDLE, paddleImage,
                inputListener, windowDimensions);
        paddle.setCenter(new Vector2(windowDimensions.x()/2,
                (int)windowDimensions.y()-Constants.PADDLE_SPACE_FROM_BOTTOM));
        gameObjects().addGameObject(paddle);
    }

    /*
   sub-method of 'initializeGame' - creates the background object.
    */
    private void createBackground(){
        Renderable backgroundImg = imageReader.readImage(
                "assets/DARK_BG2_small.jpeg",false);
        GameObject background = new GameObject(Vector2.ZERO, new Vector2(windowDimensions.x(),
                windowDimensions.y()), backgroundImg);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    /*
   sub-method of 'initializeGame' - creates the wall objects.
    */
    private void createWalls(){
        Renderable boarderRectangle = new RectangleRenderable(Color.BLACK);

        // left wall
        GameObject leftWall =  new GameObject(
                Vector2.ZERO,
                new Vector2(Constants.BORDER_WIDTH, windowDimensions.y()),
                boarderRectangle);
        gameObjects().addGameObject(leftWall);

        // right wall
        GameObject rightWall =  new GameObject(
                new Vector2(windowDimensions.x() - Constants.BORDER_WIDTH, 0),
                new Vector2(Constants.BORDER_WIDTH, windowDimensions.y()),
                boarderRectangle);
        gameObjects().addGameObject(rightWall);

        // ceiling
        GameObject ceiling =  new GameObject(
                Vector2.ZERO,
                new Vector2(windowDimensions.x(), Constants.BORDER_WIDTH),
                boarderRectangle);
        gameObjects().addGameObject(ceiling);
    }

    /*
   sub-method of 'initializeGame' - creates the brick objects.
    */
    private void createBricksWall(){

        // calculates the brick width by:
        // (all frame width - the borders width - the space from the borders - the space between the bricks)/
        // (number of required bricks in each row)
        float brickWidth = (windowDimensions.x()
                - 2*Constants.BORDER_WIDTH
                - 2*Constants.SPACE_FROM_THE_BORDERS
                -(numberOfBricksInLine-1)*Constants.SPACE_BETWEEN_BRICKS)
                / numberOfBricksInLine;

        Vector2 brickDimensions = new Vector2(brickWidth, Constants.BRICK_HEIGHT);
        Renderable brickImg = imageReader.readImage("assets/brick.png",false);
        StrategyFactory strategyFactory = new StrategyFactory(
                this, imageReader,soundReader, inputListener);

        for (int line = 0; line < numberOfLines; line++) {
            float currentBrickXCoordinate = Constants.BORDER_WIDTH + Constants.SPACE_FROM_THE_BORDERS;
            float currentBrickYCoordinate = Constants.BORDER_WIDTH + Constants.SPACE_FROM_THE_BORDERS
                    + (Constants.BRICK_HEIGHT+ Constants.SPACE_BETWEEN_BRICKS)*line ;
            for (int currentBrick = 0; currentBrick < numberOfBricksInLine; currentBrick++) {
                GameObject singleBrick = new Brick(
                        new Vector2(currentBrickXCoordinate, currentBrickYCoordinate),
                        brickDimensions, brickImg,
                        strategyFactory.getStrategy(StrategyOptions.ALL));
                gameObjects().addGameObject(singleBrick, Layer.STATIC_OBJECTS);
                remainingBrickCounter.increaseBy(1);
                currentBrickXCoordinate += brickWidth + Constants.SPACE_BETWEEN_BRICKS;
            }

        }
    }

    /*
   sub-method of 'initializeGame' - creates the life-counter objects.
    */
    private void createLivesCounter() {
        createHeartLifeCounter();
        createNumericLifeCounter();
    }

    /*
   sub-method of 'createLivesCounter' - creates the numeric life counter object.
    */
    private void createNumericLifeCounter() {
        TextRenderable numericLivesRenderable = new TextRenderable(Integer.toString(gameLives));
        this.numericLifeCounter = new NumericLifeCounter(Vector2.ZERO, Constants.DIM_OF_LIVES_NUM,
                numericLivesRenderable, this);
        for (int i = 1; i <= gameLives; i++) {
            this.numericLifeCounter.addLife();
        }
    }

    /*
   sub-method of 'createLivesCounter' - creates the heart life counter object.
    */
    private void createHeartLifeCounter() {
        Renderable HeartImg = imageReader.readImage("assets/heart.png", true);
        this.heartsLifeCounter = new HeartsLifeCounter(Vector2.ZERO, Constants.DIM_OF_HEART,HeartImg,this);
        for (int i = 1; i <= gameLives; i++) {
            this.heartsLifeCounter.addLife();
        }
    }


    /***********************************
     ****   OTHER PRIVATE METHODS   ****
     ***********************************/

    /*
    sub-method for checking winning conditions
     */
    private void decreaseLives(){
        gameLives--;
        this.heartsLifeCounter.removeLife();
        this.numericLifeCounter.removeLife();
    }

    /*
    sub-method for checking winning conditions
     */
    private void checkForGameEnd() {
        String prompt = "";

        if (remainingBrickCounter.value() <= 0 || inputListener.isKeyPressed(KeyEvent.VK_W)){
            prompt =  "You win! Play again?";
        }

        double ballHeight = ball.getCenter().y();
        if (ballHeight > windowDimensions.y()){
            if (gameLives > 1){
                decreaseLives();
                resetBall();
            }
            else{
                prompt = "You lose! Play again?";
            }
        }
        if(!prompt.isEmpty()){
            if(windowController.openYesNoDialog(prompt)){
                windowController.resetGame();
            }
            else {
                windowController.closeWindow();
            }
        }
    }

    /*
    sub-method for cleaning objects that "left" the screen
     */
    private void removeObjectsOutOfTheWindow(){
        for (GameObject obj:this.gameObjects()) {
            if (obj.getTopLeftCorner().y() > windowDimensions.y()){
                this.removeObjectFromThisGame(obj);
            }
        }
    }


    /***********************************
     ****           MAIN            ****
     ***********************************/

    /**
     * the main method.
     * excuting the 'Bricker' game.
     * @param args - parameters from the CLI
     */
    public static void main(String[] args){
        Vector2 windowDimensions = new Vector2(700, 500);
        int numberOfBricksInLine = Constants.DEFAULT_NUM_OF_BRICKS_IN_LINE;
        int numberOfLines = Constants.DEFAULT_NUM_OF_BRICKS_LINES;

        // checks if it gets 2 args and set their values
        if(args.length == 2){
            numberOfBricksInLine = Integer.parseInt(args[0]);
            numberOfLines = Integer.parseInt(args[1]);
        }

        //creating the game
        GameManager gameManager = new BrickerGameManager("Bricker", windowDimensions,
                numberOfBricksInLine, numberOfLines);
        gameManager.run();
    }
}

