package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.ScheduledTask;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Avatar;

import java.awt.*;
import java.util.HashSet;
import java.util.Random;

import static pepse.Constants.*;

/**
 * represents a fruit game object
 */
public class Fruit extends AvatarObserver {

    //constants
    private static final float FRUIT_SIZE = 20;
    private static final Vector2 FRUIT_DIMS = new Vector2(FRUIT_SIZE,FRUIT_SIZE);
    private static final int LEAVES_IN_ROW_COL = 7;
    private static final int BONUS_POINTS = 10;

    //fields
    private final float cycleLegnth;
    private boolean fruitIsRed = true;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     */
    public Fruit(Vector2 topLeftCorner, float cycleLength) {
        super(
                topLeftCorner,
                FRUIT_DIMS,
                new OvalRenderable(ColorSupplier.approximateColor(Color.RED)));

        this.cycleLegnth = cycleLength;
        this.setTag(FRUIT_TAG);
    }

    /**
     * Create fruits for a tree
     *
     * @param treeHead - tree head position
     * @param cycleLength - the game's cycle lenght
     * @return hash set of fruits
     */
    public static HashSet<Fruit> createFruitsForSingleTree(Vector2 treeHead, float cycleLength){
        HashSet<Fruit> fruitSet = new HashSet<Fruit>();

        //calc first leaf location
        float firstFruitXPos = treeHead.x() - FRUIT_SIZE*3;
        float firstFruitYPos = treeHead.y() - FRUIT_SIZE*4;

        //loop on all the leaves locations
        for (int row = 0; row < LEAVES_IN_ROW_COL; row++) {
            for (int col = 0; col < LEAVES_IN_ROW_COL; col++) {
                if (new Random().nextInt(15) == 0) {
                    //create leaf in this place and add it to the leaves set
                    Vector2 curFruitPos = new Vector2(
                            firstFruitXPos + col * FRUIT_SIZE,
                            firstFruitYPos + row * FRUIT_SIZE);
                    fruitSet.add(new Fruit(curFruitPos, cycleLength));
                }
            }
        }
        return fruitSet;
    }

    /**
     * addition to the basic method - check for collision specific with the avatar.
     *
     * @param other     The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other.getTag().equals(AVATAR_TAG)){
            this.renderer().fadeOut(0);
            returnFruitToGame();
            ((Avatar)other).addEnergy(BONUS_POINTS);
        }
    }

    /*
    returns fruit to game after game cycle
     */
    private void returnFruitToGame() {
        new ScheduledTask(
                this,
                cycleLegnth,
                false,
                () -> this.renderer().fadeIn(0));
    }

    /**
     * the update that the fruit should apply when the avatar jumps -
     * changes the fruit color
     */
    @Override
    public void jumpUpdate() {
        if (fruitIsRed){
            this.renderer().setRenderable(new OvalRenderable(ColorSupplier.approximateColor(Color.ORANGE)));
            fruitIsRed = false;
        }
        else {
            this.renderer().setRenderable(new OvalRenderable(ColorSupplier.approximateColor(Color.RED)));
            fruitIsRed = true;
        }
    }

}
