package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;
import static pepse.Constants.*;

/**
 * represents the creation of the day-night effect
 */
public class Night {

    //constants
    private static final Float NOON_OPACITY = 0f;
    private static final Float MIDNIGHT_OPACITY = 0.5f;


    /**
     * creates the night effect.
     *
     * @param windowDimensions - the game's window dimensions
     * @param cycleLength - the game's cycle length
     * @return night effect game object
     */
    public static GameObject create(Vector2 windowDimensions, float cycleLength){

        //creates the game object
        RectangleRenderable blackRectangle = new RectangleRenderable(Color.BLACK);
        GameObject night = new GameObject(Vector2.ZERO,windowDimensions, blackRectangle);

        // add few settings to the object
        night.setTag(NIGHT_TAG);

        // add day-night effect
        new Transition<Float>(
                night,
                night.renderer()::setOpaqueness,
                NOON_OPACITY,
                MIDNIGHT_OPACITY,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                cycleLength,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null
        );

        return night;
    }
}
