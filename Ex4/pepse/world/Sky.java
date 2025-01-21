package pepse.world;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;

import static pepse.Constants.SKY_TAG;

/**
 * represents the creation of the sky
 */
public class Sky {

    //fields
    private static final Color BASIC_SKY_COLOR = Color.decode("#80C6E5");

    /**
     * creates the sun object.
     *
     * @param windowDimensions - the game's window dimensions
     * @return sky game object
     */
    public static GameObject create(Vector2 windowDimensions){

        //creating the sky object (blue rectangle)
        GameObject sky = new GameObject(
                Vector2.ZERO,
                windowDimensions,
                new RectangleRenderable(BASIC_SKY_COLOR));

        //make sure the camera set on the background
        sky.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);

        //adding tag for convenience
        sky.setTag(SKY_TAG);

        return sky;
    }

}
