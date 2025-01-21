package pepse.world.trees;

import danogl.GameObject;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;

import java.awt.*;
import java.util.HashSet;
import java.util.Random;

import static pepse.Constants.*;

/**
 * represents a leaf game object
 */
public class Leaf extends AvatarObserver {

    //constants
    private static final Color GREEN_LEAVES =  new Color(50, 200, 30);
    private static final float LEAF_SIZE = 20;
    private static final Vector2 LEAF_DIMS = new Vector2(LEAF_SIZE,LEAF_SIZE);
    private static final int LEAVES_IN_ROW_COL = 7;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     */
    public Leaf(Vector2 topLeftCorner) {
        super(
                topLeftCorner,
                LEAF_DIMS,
                new RectangleRenderable(ColorSupplier.approximateColor(GREEN_LEAVES)));

        // add few settings to the object
        this.setTag(LEAF_TAG);

        //create the leaf movement
        activateScheduledTask();
    }

    /*
    sub-method - activates the leaf movement
     */
    private void activateScheduledTask() {
        Random random = new Random();

        new ScheduledTask(
                this,
                random.nextInt(10),
                false,
                this::changeAngles);

        new ScheduledTask(
                this,
                random.nextInt(10),
                false,
                this::changeWidth);
    }

    /*
    the leaf's width change transition
     */
    private void changeWidth() {
        new Transition<Vector2>(
                this,
                this::setDimensions,
                LEAF_DIMS.multX(0.9f),
                LEAF_DIMS.multX(1.1f),
                Transition.LINEAR_INTERPOLATOR_VECTOR,
                5,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null
        );
    }

    /*
    the leaf's angle change transition
     */
    private void changeAngles() {
        new Transition<>(
                this,
                this.renderer()::setRenderableAngle,
                this.renderer().getRenderableAngle(),
                50f,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                10,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null
        );
    }

    /**
     * Create leaves for a tree
     *
     * @param treeHead - tree head position
     * @return hash set of leaves
     */
    public static HashSet<Leaf> createLeavesForSingleTree(Vector2 treeHead){
        HashSet<Leaf> leavesSet = new HashSet<Leaf>();

        //calc first leaf location
        float firstLeafXPos = treeHead.x() - LEAF_SIZE*3;
        float firstLeafYPos = treeHead.y() - LEAF_SIZE*4;

        //loop on all the leaves locations
        for (int row = 0; row < LEAVES_IN_ROW_COL; row++) {
            for (int col = 0; col < LEAVES_IN_ROW_COL; col++) {
                if (new Random().nextInt(3) != 0) {
                    //create leaf in this place and add it to the leaves set
                    Vector2 curLeafPos = new Vector2(
                            firstLeafXPos + col * LEAF_SIZE,
                            firstLeafYPos + row * LEAF_SIZE);
                    Leaf leaf = new Leaf(curLeafPos);
                    leavesSet.add(leaf);
                }
            }
        }
        return leavesSet;
    }

    /**
     * the update that the leaf should apply when the avatar jumps -
     * changes the leaf angle
     */
    @Override
    public void jumpUpdate() {
        float curAngle = this.renderer().getRenderableAngle();
        new Transition<>(
                this,
                this.renderer()::setRenderableAngle,
                curAngle,
                curAngle+ 90f,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                3,
                Transition.TransitionType.TRANSITION_ONCE,
                null
        );
    }


    /**
     * override for the basic method - make sure a leaf never collides with other objects
     * @param other The other GameObject.
     * @return false always
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return false;
    }
}
