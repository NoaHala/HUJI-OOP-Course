package pepse.world;

import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.NoiseGenerator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static pepse.Constants.*;

/* *
 * represents the creation of the terrain
 */
public class Terrain {

    //constants
    private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
    private static final int TERRAIN_DEPTH = 20;

    // fields
    private final int seed;
    private final int groundHeightAtX0;


    /**
     * Constrctor.
     *
     * @param windowDimensions - the game dimensions
     * @param seed - seed for randomness
     */
    public Terrain(Vector2 windowDimensions, int seed){
        this.seed = seed;
        this.groundHeightAtX0 = (int) (windowDimensions.y() * GROUND_HEIGHT_AT_X_0);
    }

    /**
     * calculates the ground height in a given x coordinate
     *
     * @param x - x coordinate at the game
     * @return - the ground y coordinate at the given x
     */
    public float groundHeightAt(float x){
        NoiseGenerator noiseGenerator = new NoiseGenerator(seed,groundHeightAtX0);
         return groundHeightAtX0 + (float)noiseGenerator.noise(x,BLOCK_SIZE*5);
    }

    /**
     * creates the ground at given x coordinates at the game.
     *
     * @param minX - x coordinate to begin with
     * @param maxX - x coordinate to end with
     * @return - list of ground block
     */
    public List<Block> createInRange(int minX, int maxX){
        List<Block> blockList = new ArrayList<>();

        //adapt the ground range to the size of one block and calc the required number of columns
        int firstColX = (minX/BLOCK_SIZE) * BLOCK_SIZE;
        int lastColX = (maxX/BLOCK_SIZE) * BLOCK_SIZE;
        int colNum = ((lastColX - firstColX) /BLOCK_SIZE) + 1;

        // loop on all the columns
        for (int col = 0; col < colNum; col++) {
            int curX = firstColX + (col * BLOCK_SIZE);
            int highestBlockYCoordinate = (int)Math.floor(groundHeightAt(curX) /BLOCK_SIZE) * BLOCK_SIZE;

            //loop inside each col to create the blocks
            for (int row = 0; row < TERRAIN_DEPTH; row++) {
                int curY = highestBlockYCoordinate + (row * BLOCK_SIZE);

                //add block to the list
                blockList.add(createSingleBlock(curX,curY));
            }
        }
        return blockList;
    }

    /*
    sub-method creates a single block in a given coordinate
     */
    private Block createSingleBlock(int curX, int curY) {
        //create a block
        RectangleRenderable rectangleRenderable =
                new RectangleRenderable(ColorSupplier.approximateColor(BASE_GROUND_COLOR));
        Block block = new Block(new Vector2(curX,curY),rectangleRenderable);

        //adding tag for convenience
        block.setTag(GROUND_TAG);

        return block;
    }
}
