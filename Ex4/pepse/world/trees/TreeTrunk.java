package pepse.world.trees;

import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;

import java.awt.*;

import static pepse.Constants.*;

/**
 * represents a tree trunk game object
 */
public class TreeTrunk extends AvatarObserver {

    //constants
    private static final Color BROWN =  new Color(100, 50, 20);

    /**
     * Construct a new TreeTrunk instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param height the required trunk height
     */
    public TreeTrunk(Vector2 topLeftCorner, float height) {

        // Creates a fixed size object
        super(
                topLeftCorner,
                new Vector2(BLOCK_SIZE, height),
                new RectangleRenderable(ColorSupplier.approximateColor(BROWN)));

        // prevent Intersections
        physics().preventIntersectionsFromDirection(Vector2.ZERO);

        // prevent movement in case of collision
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);

        // add few settings to the object
        this.setTag(TRUNK_TAG);
    }

    /**
     * the update that the trunk should apply when the avatar jumps -
     * changes the trunk color
     */
    @Override
    public void jumpUpdate() {
        this.renderer().setRenderable(new RectangleRenderable(ColorSupplier.approximateColor(BROWN)));
    }
}
