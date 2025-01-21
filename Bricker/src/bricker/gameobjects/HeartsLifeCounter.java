package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * represent a 'heart life counter' object in the 'Bricker' game
 */
public class HeartsLifeCounter extends GameObject implements LifeCounters {

    // private variables
    private int gameLives;
    private final Renderable heartImg;
    private final BrickerGameManager brickerGameManager;
    private final Vector2 windowsDimensions;
    private static GameObject[] hearts = new GameObject[Constants.MAX_NUM_OF_LIFE];

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param brickerGameManager a reference for this game manager
     */
    public HeartsLifeCounter(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                             BrickerGameManager brickerGameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.gameLives = 0;
        this.heartImg = renderable;
        this.brickerGameManager = brickerGameManager;
        this.windowsDimensions = brickerGameManager.getWindowDimensions();
    }

    /**
     * increase the life counter in one
     */
    @Override
    public void addLife(){
        gameLives++;
        GameObject heart = new GameObject(Vector2.ZERO, Constants.DIM_OF_HEART, heartImg);
        heart.setCenter(new Vector2(Constants.BORDER_WIDTH + Constants.SPACE_FROM_THE_BORDERS
                + Constants.DIM_OF_LIVES_NUM.x() + Constants.SPACE_FOR_LIVES + Constants.DIM_OF_HEART.x()/2
                +(gameLives-1)*(Constants.DIM_OF_HEART.x()+Constants.SPACE_FOR_LIVES),
                windowsDimensions.y()-Constants.LIVES_SPACE_FROM_BOTTOM));
        this.hearts[gameLives-1] = heart;
        brickerGameManager.addObjectToThisGame(heart, Layer.UI);
    }

    /**
     * decrease the life counter in one
     */
    @Override
    public void removeLife(){
        gameLives--;
        brickerGameManager.removeObjectFromThisGame(this.hearts[gameLives], Layer.UI);
        this.hearts[gameLives] = null;
    }

    /**
     * a getter for that object name tag
     * @return the string "LifeCounter"
     */
    @Override
    public String getTag() {
        return "LifeCounter";
    }
}
