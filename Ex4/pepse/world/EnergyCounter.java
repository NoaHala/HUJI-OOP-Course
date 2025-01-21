package pepse.world;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import static pepse.Constants.*;

/**
 * represents the creation of the energy counter
 */
public class EnergyCounter {

    //fields
    private static final Vector2 ENERGY_COUNTER_SIZE = new Vector2(50, 20);

    /**
     * creates the energy counter.
     *
     * @param avatar - the game's avatar
     * @return energy counter game object
     */
    public static GameObject create(Avatar avatar){

        //creates the game object
        TextRenderable energyRenderable = new TextRenderable(avatar.getEnergy() + "%");
        GameObject energyCounter =  new GameObject(Vector2.ZERO,ENERGY_COUNTER_SIZE,energyRenderable);

        // add few settings to the object
        energyCounter.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        energyCounter.setTag(ENERGY_TAG);

        //create the counter change
        energyCounter.addComponent(deltaTime ->
                        energyCounter.renderer().setRenderable(
                                new TextRenderable(avatar.getEnergy() + "%")));

        return energyCounter;
    }
}
