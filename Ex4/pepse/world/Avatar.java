package pepse.world;

import danogl.GameObject;
import danogl.gui.rendering.AnimationRenderable;
import danogl.util.Vector2;
import danogl.gui.UserInputListener;
import danogl.gui.ImageReader;
import pepse.world.trees.AvatarObserver;

import java.awt.event.KeyEvent;
import java.util.HashSet;

import static pepse.Constants.*;

/**
 * represents the avatar object
 */
public class Avatar extends GameObject {

    //constants
    private static final Vector2 AVATAR_SIZE = new Vector2(30,50);
    private static final float VELOCITY_X = 400;
    private static final float VELOCITY_Y = -650;
    private static final float GRAVITY = 600;
    private static final float MAX_ENERGY = 100;
    private static final double TIME_BETWEEN_CLIPS = 0.3;

    // images paths for avatar animation
    private static final String[] paths_idle = new String[]{
            "assets/idle_0.png",
            "assets/idle_1.png",
            "assets/idle_2.png",
            "assets/idle_3.png"};
    private static final String[] paths_run = new String[]{
            "assets/run_0.png",
            "assets/run_1.png",
            "assets/run_2.png",
            "assets/run_3.png",
            "assets/run_4.png",
            "assets/run_5.png"};
    private static final String[] paths_jump = new String[]{
            "assets/jump_0.png",
            "assets/jump_1.png",
            "assets/jump_2.png",
            "assets/jump_3.png"};


    // fields
    private final AnimationRenderable idle;
    private final AnimationRenderable run;
    private final AnimationRenderable jump;

    private final UserInputListener inputListener;
    private float energy = MAX_ENERGY;
    private AvatarState avatarState= AvatarState.IDLE;
    private final HashSet<AvatarObserver> observers;

    /**
     * Constructor - creates the avatar object
     *
     * @param pos - starting position
     * @param inputListener - input listener
     * @param imageReader - image reader
     * @param observers - hash set of observers
     */
    public Avatar(
            Vector2 pos,
            UserInputListener inputListener,
            ImageReader imageReader,
            HashSet<AvatarObserver> observers) {

        super(
                pos,
                AVATAR_SIZE,
                new AnimationRenderable(paths_idle,imageReader,true,TIME_BETWEEN_CLIPS));

        //animation renderables
        idle = new AnimationRenderable(paths_idle,imageReader,true,TIME_BETWEEN_CLIPS);
        run = new AnimationRenderable(paths_run,imageReader,true,TIME_BETWEEN_CLIPS);
        jump = new AnimationRenderable(paths_jump,imageReader,true,TIME_BETWEEN_CLIPS);

        //more object settings
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);
        this.setTag(AVATAR_TAG);

        this.inputListener = inputListener;
        this.observers = observers;
    }


    /**
     * addition to the basic update - move the avatar according to the keyboard input
     * @param deltaTime The time elapsed, in seconds, since the last frame. Can
     *                  be used to determine a new position/velocity by multiplying
     *                  this delta with the velocity/acceleration respectively
     *                  and adding to the position/velocity:
     *                  velocity += deltaTime*acceleration
     *                  pos += deltaTime*velocity
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (inputListener.isKeyPressed(KeyEvent.VK_SPACE) &&
                getVelocity().y() == 0 &&
                this.energy >= 10f){
            avatarJump();
        }
        else if(inputListener.isKeyPressed(KeyEvent.VK_LEFT) && this.energy >=0.5){
            avatarLeft();
        }
        else if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT)&& this.energy >=0.5){
            avatarRight();
        }
        else if(this.energy < MAX_ENERGY && getVelocity().y() == 0){
            avatarRest();
        }
    }

    /*
     * sub-method, handles the avatar while resting
     */
    private void avatarRest() {
        if(avatarState != AvatarState.IDLE){
            this.renderer().setRenderable(idle);
            avatarState = AvatarState.IDLE;
        }

        // add 1 energy point
        this.energy = Math.max(this.energy+ 1f, MAX_ENERGY);
    }

    /*
     * sub-method, handles the avatar while moving to the right
     */
    private void avatarRight() {
        float xVel = 0;
        if(avatarState != AvatarState.RUN){
            this.renderer().setRenderable(run);
            avatarState = AvatarState.RUN;
        }
        xVel += VELOCITY_X;
        this.renderer().setIsFlippedHorizontally(false);
        transform().setVelocityX(xVel);

        // remove 0.5 energy points
        this.energy -= 0.5f;
    }

    /*
     * sub-method, handles the avatar while moving to the left
     */
    private void avatarLeft() {
        float xVel = 0;
        if(avatarState != AvatarState.RUN){
            this.renderer().setRenderable(run);
            avatarState = AvatarState.RUN;
        }
        xVel -= VELOCITY_X;
        this.renderer().setIsFlippedHorizontally(true);
        transform().setVelocityX(xVel);

        // remove 0.5 energy points
        this.energy -= 0.5f;
    }

    /*
     * sub-method, handles the avatar while jumping
     */
    private void avatarJump() {
        if(avatarState != AvatarState.JUMP){
            this.renderer().setRenderable(jump);
            avatarState = AvatarState.JUMP;
        }
        transform().setVelocityY(VELOCITY_Y);
        notifyObserversJump();

        // remove 10 energy points
        this.energy -= 10f;
    }


    /**
     * a getter for the energy field.
     *
     * @return this avatar's energy
     */
    public int getEnergy() {
        return (int) this.energy;
    }

    /**
     * adding points to the avatar's energy.
     *
     * @param addition - amount to add
     */
    public void addEnergy(int addition){
        this.energy = Math.min(this.energy + addition, MAX_ENERGY);
    }

    /**
     * notify observers that the avatar jumped
     */
    public void notifyObserversJump(){
        for (AvatarObserver avatarObserver: observers) {
            avatarObserver.jumpUpdate();
        }
    }


}
