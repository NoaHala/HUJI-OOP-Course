package pepse.world.daynight;

import danogl.GameObject;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import danogl.components.Transition;

import java.awt.*;

import static pepse.Constants.*;

/**
 * represents the creation of the sun
 */
public class Sun {

    //constants
    private static final Vector2 SUN_SIZE = new Vector2(100, 100);
    private static final float ROTATION_INITIAL_VALUE = 0;
    private static final float ROTATION_FINAL_VALUE = 360;

    /**
     * creates the sun.
     *
     * @param windowDimensions - the game's window dimensions
     * @param cycleLength - the game's cycle length
     * @return sun game object
     */
    public static GameObject create(Vector2 windowDimensions, float cycleLength){

        //creates the game object
        OvalRenderable yellowOval = new OvalRenderable(Color.YELLOW);
        GameObject sun = new GameObject(Vector2.ZERO,SUN_SIZE,yellowOval);

        //set the sun place
        float skyMiddleX = windowDimensions.x()/2;
        float groundY = windowDimensions.y()* GROUND_HEIGHT_AT_X_0;
        float skyMiddleY = groundY /2;
        Vector2 initialSunCenter = new Vector2(skyMiddleX, skyMiddleY);
        sun.setCenter(initialSunCenter);

        // add few settings to the object
        sun.setTag(SUN_TAG);

        // the sun movement
        Vector2 cycleCenter = new Vector2(skyMiddleX, groundY);
        new Transition<Float>(
                sun,
                (Float angle) -> sun.setCenter (
                        initialSunCenter.subtract(cycleCenter).rotated(angle).add(cycleCenter)),
                ROTATION_INITIAL_VALUE,
                ROTATION_FINAL_VALUE,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                cycleLength * 2,
                Transition.TransitionType.TRANSITION_LOOP,
                null
        );

        return sun;
    }
}
