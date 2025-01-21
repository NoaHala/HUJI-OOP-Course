package pepse.world;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import static pepse.Constants.BLOCK_SIZE;

/**
 * represents the ground block game object
 */
public class Block extends GameObject {

    /**
     * Constructor - creates ground block
     * @param topLeftCorner - position
     * @param renderable - the block renderable
     */
    public Block(Vector2 topLeftCorner, Renderable renderable) {

        // Creates a fixed size object
        super(topLeftCorner, Vector2.ONES.mult(BLOCK_SIZE), renderable);

        // prevent Intersections
        physics().preventIntersectionsFromDirection(Vector2.ZERO);

        // prevent movement in case of collision
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
    }
}
