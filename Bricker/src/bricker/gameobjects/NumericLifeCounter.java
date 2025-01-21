package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * represent a 'numeric life counter' object in the 'Bricker' game
 */
public class NumericLifeCounter extends GameObject implements LifeCounters {

    // private variables
    private final Vector2 windowsDimensions;
    private int gameLives;
    private TextRenderable textRenderable;
    private final BrickerGameManager brickerGameManager;
    private GameObject visualNumericCounter;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param textRenderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param brickerGameManager a reference for this game manager
     */
    public NumericLifeCounter(Vector2 topLeftCorner, Vector2 dimensions, TextRenderable textRenderable,
                              BrickerGameManager brickerGameManager) {
        super(topLeftCorner, dimensions, textRenderable);
        this.gameLives = 0;
        this.brickerGameManager = brickerGameManager;
        this.windowsDimensions = brickerGameManager.getWindowDimensions();
        this.textRenderable = textRenderable;
    }

    /**
     * increase the life counter in one
     */
    @Override
    public void addLife() {
        gameLives++;
        changeNumericCounter(gameLives);
    }

    /**
     * increase the life counter in one
     */
    @Override
    public void removeLife(){
        gameLives--;
        changeNumericCounter(gameLives);
    }

    /*
    sub-method for the increase / decrease the life in the counter
     */
    private void changeNumericCounter(int newVal){
        if(this.visualNumericCounter != null){
            brickerGameManager.removeObjectFromThisGame(this.visualNumericCounter, Layer.UI);
        }
        this.textRenderable = new TextRenderable(Integer.toString(newVal));
        switch (newVal){
            case (1):
                this.textRenderable.setColor(Color.RED);
                break;
            case (2):
                this.textRenderable.setColor(Color.YELLOW);
                break;
            default:
                this.textRenderable.setColor(Color.GREEN);
        }

        this.visualNumericCounter = new GameObject(
                Vector2.ZERO, Constants.DIM_OF_LIVES_NUM, this.textRenderable);
        this.visualNumericCounter.setCenter(
                new Vector2(Constants.BORDER_WIDTH + Constants.SPACE_FROM_THE_BORDERS +
                        Constants.DIM_OF_LIVES_NUM.x()/2,
                        this.windowsDimensions.y()-Constants.LIVES_SPACE_FROM_BOTTOM));

        brickerGameManager.addObjectToThisGame(this.visualNumericCounter, Layer.UI);
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
