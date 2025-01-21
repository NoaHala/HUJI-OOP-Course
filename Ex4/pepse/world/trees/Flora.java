package pepse.world.trees;

import danogl.GameObject;
import danogl.util.Vector2;

import java.util.HashSet;
import java.util.Random;
import java.util.function.Function;

import static pepse.Constants.*;

/**
 * represents the creation of the Flora
 */
public class Flora {

    //fields
    private final Function<Float,Float> groundHeightAtX;
    private final float cycleLength;


    /**
     * Constructor.
     *
     * @param groundHeightAtX - callback, function that gets x val and return the y val
     *                       of the ground height in that x
     * @param cycleLength - the game's cycle length
     */
    public Flora(Function<Float,Float> groundHeightAtX, float cycleLength){
        this.groundHeightAtX = groundHeightAtX;
        this.cycleLength = cycleLength;
    }

    /**
     * creates the flora at given x coordinates at the game.
     *
     * @param minX - x coordinate to begin with
     * @param maxX - x coordinate to end with
     * @return - list of flora's game objects
     */
    public HashSet<GameObject> createInRange(int minX, int maxX){

        //create the hash set
        HashSet<GameObject> flora = new HashSet<>();

        //adapt the range to the size of one block and calc the required number of columns
        int firstColX = (minX/BLOCK_SIZE) * BLOCK_SIZE;
        int lastColX = (maxX/BLOCK_SIZE) * BLOCK_SIZE;
        int colNum = ((lastColX - firstColX) /BLOCK_SIZE) + 1;

        // loop on all the columns
        for (int col = 0; col < colNum; col++) {
            if (new Random().nextInt(10) == 0){
                // calc tree head location
                float trunkHeight = new Random().nextInt(5, 9) * BLOCK_SIZE;
                float curX = firstColX + (col * BLOCK_SIZE);
                float curY = groundHeightAtX.apply(curX) - trunkHeight;
                Vector2 treeHead = new Vector2(curX, curY);

                //crate and register all the tree
                createTree(flora, treeHead,trunkHeight,cycleLength);
            }
        }
        return flora;
    }

    /*
    sub-method - creates all the tree parts
     */
    private void createTree(
            HashSet<GameObject> flora,
            Vector2 treeHead,
            float trunkHeight,
            float cycleLength) {

        // create and register tree trunk
        TreeTrunk treeTrunk = new TreeTrunk(treeHead, trunkHeight);
        flora.add(treeTrunk);

        // create and register the leaves
        flora.addAll(Leaf.createLeavesForSingleTree(treeHead));

        // create and register the fruits
        flora.addAll(Fruit.createFruitsForSingleTree(treeHead, cycleLength));
    }
}
