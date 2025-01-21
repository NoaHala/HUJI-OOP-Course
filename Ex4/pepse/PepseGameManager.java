package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;
import pepse.world.*;
import pepse.world.daynight.*;
import pepse.world.trees.AvatarObserver;
import pepse.world.trees.Flora;
import static pepse.Constants.*;

import java.util.HashSet;


/**
 * represents pepse game manager
 */
public class PepseGameManager extends GameManager {

    //constants
    private static final int FIRST_X_VAL = 0;
    private static final int CYCLE_LENGTH = 30;


    // as I'm not doing the bonus part, I don't need consistent randomness,
    // and this constant is only for filling the exercise's API requirements
    private static final int IRRELEVANT_SEED = 2024;

    /**
     * @param imageReader      Contains a single method: readImage, which reads an image from disk.
     *                         See its documentation for help.
     * @param soundReader      Contains a single method: readSound, which reads a wav file from
     *                         disk. See its documentation for help.
     * @param inputListener    Contains a single method: isKeyPressed, which returns whether
     *                         a given key is currently pressed by the user or not. See its
     *                         documentation.
     * @param windowController Contains an array of helpful, self explanatory methods
     *                         concerning the window.
     */
    @Override
    public void initializeGame(
            ImageReader imageReader,
            SoundReader soundReader,
            UserInputListener inputListener,
            WindowController windowController) {

        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        Vector2 windowDimensions = windowController.getWindowDimensions();

        //add the sky object
        gameObjects().addGameObject(Sky.create(windowDimensions), Layer.BACKGROUND);

        //add sun
        GameObject sun = Sun.create(windowDimensions, CYCLE_LENGTH);
        gameObjects().addGameObject(sun, Layer.BACKGROUND);

        //add halo
        gameObjects().addGameObject(SunHalo.create(sun), Layer.BACKGROUND);

        //add night effect
        gameObjects().addGameObject(Night.create(windowDimensions,CYCLE_LENGTH), Layer.FOREGROUND);

        //add ground
        Terrain terrain = new Terrain(windowDimensions, IRRELEVANT_SEED);
        for (Block block:terrain.createInRange(FIRST_X_VAL,(int)windowDimensions.x())) {
            gameObjects().addGameObject(block, Layer.STATIC_OBJECTS);
        }

        //add avater
        Vector2 avatarPlacement = Vector2.ZERO;
        HashSet<AvatarObserver> avatarObservers = new HashSet<>();
        Avatar avatar = new Avatar(avatarPlacement, inputListener, imageReader, avatarObservers);
        gameObjects().addGameObject(avatar, Layer.DEFAULT);

        //add energy counter
        gameObjects().addGameObject(EnergyCounter.create(avatar));

        //add flora
        Flora flora = new Flora(terrain::groundHeightAt, CYCLE_LENGTH);
        HashSet<GameObject> floraObjects =
                flora.createInRange(FIRST_X_VAL+ BLOCK_SIZE,(int)windowDimensions.x());
        addFlora(floraObjects, avatarObservers);
    }

    /*
    sub method - add the flora to the game objects and the avatar observers
     */
    private void addFlora(HashSet<GameObject> floraObjects, HashSet<AvatarObserver> avatarObservers) {
        for (GameObject floraObject:floraObjects) {
            if(floraObject.getTag().equals(TRUNK_TAG)){
                avatarObservers.add((AvatarObserver) floraObject);
                gameObjects().addGameObject(floraObject, Layer.STATIC_OBJECTS);
            }
            else if(floraObject.getTag().equals(LEAF_TAG)){
                avatarObservers.add((AvatarObserver) floraObject);
                gameObjects().addGameObject(floraObject, Layer.DEFAULT - 1);
            }
            else if(floraObject.getTag().equals(FRUIT_TAG)){
                avatarObservers.add((AvatarObserver) floraObject);
                gameObjects().addGameObject(floraObject, Layer.DEFAULT);
            }
        }
    }


    /**
     * Main method
     * runs the pepse program
     */
    public static void main(String[] args) {
        new PepseGameManager().run();
    }

}
