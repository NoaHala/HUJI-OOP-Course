package pepse.world.daynight;

import danogl.GameObject;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

import static pepse.Constants.*;

/**
 * represents the creation of the sun's halo effect
 */
public class SunHalo {

    private static final Color YELLOW_HALO = new Color(225, 225, 0, 20);
    private static final float HALO_SIZE_FACTOR = 1.5f;

    /**
     * @param sun - the sun game object to decorate
     * @return a halo game object
     */
    public static GameObject create(GameObject sun){

        //creates the game object
        OvalRenderable haloOval = new OvalRenderable(YELLOW_HALO);
        final Vector2 HALO_SIZE = sun.getDimensions().mult(HALO_SIZE_FACTOR);
        GameObject halo = new GameObject(Vector2.ZERO,HALO_SIZE,haloOval);
        halo.setCenter(sun.getCenter());

        // add few settings to the object
        halo.setTag(HALO_TAG);

        //create the halo movement
        halo.addComponent(deltaTime -> halo.setCenter(sun.getCenter()));

        return halo;
    }

}
