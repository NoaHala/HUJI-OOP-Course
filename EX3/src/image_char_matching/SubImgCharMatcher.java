package image_char_matching;

import java.util.*;


/**
 * SubImgCharMatcher class (part of the ascii-art algorithm) -
 * matches a character to a sub-image, according to their brightness level.
 * manages the charSet data structures for the 'AsciiArtAlgorithm'
 */
public class SubImgCharMatcher {

    //constants
    private static final double NUM_OF_PIXELS_IN_CHAR_IMG = 16*16;

    //fields
    private static boolean changesInCharSetSinceLastRun = false;
    private final TreeMap<Character, Double> charsMap;
    private final TreeMap<Double, Character> CharsAndOriginalValues;
    private final TreeMap<Double, Character> CharsAndNormalValues;

    /**
     * Constructor - gets the set of ASCII character for the program and
     * sets it in the class's field.
     *
     * @param charSet - ASCII-chars array for the program
     */
    public SubImgCharMatcher(char[] charSet) {
        //creating the new data structures
        this.charsMap = new TreeMap<>();
        this.CharsAndOriginalValues = new TreeMap<>();
        this.CharsAndNormalValues = new TreeMap<>();

        // adding all the characters to the private data structures
        for (char character : charSet){
            this.addChar(character);
        }
    }

    /*
    calculates and returns the brightness value of a given char.
    called at 'addChar'
     */
    private static double getBrightnessOfChar(char character){
        boolean[][] charImg = CharConverter.convertToBoolArray(character);
        int numOfWhitePixels = 0;

        //counts the white pixels
        for (boolean[] row: charImg){
            for (boolean pixel: row){
                if (pixel){
                    numOfWhitePixels++;
                }
            }
        }
        //returns th normalized brightness value
        return numOfWhitePixels/NUM_OF_PIXELS_IN_CHAR_IMG;
    }

    /**
     * Matches a character from the charSet to a sub-image according to its brightness level.
     *
     * @param brightness - a double that represents the brightness level of a sub-image
     * @return a character from the chosen charSet with the closest brightness level (normalized)
     */
    public char getCharByImageBrightness(double brightness){
        if(changesInCharSetSinceLastRun){
            calculateNormalVals();
            changesInCharSetSinceLastRun = false;
        }
        double floorKey = this.CharsAndNormalValues.floorKey(brightness);
        double ceilingKey = this.CharsAndNormalValues.ceilingKey(brightness);
        if (brightness-floorKey < ceilingKey-brightness){
            return this.CharsAndNormalValues.get(floorKey);
        }
        else {
            return this.CharsAndNormalValues.get(ceilingKey);
        }
    }

    /*
    calculates all the normalized values of brightness and put them on the relevant tree map
     */
    private void calculateNormalVals(){
        double maxBrightnessVal= this.CharsAndOriginalValues.lastKey();
        double minBrightnessVal = this.CharsAndOriginalValues.firstKey();

        for (Map.Entry<Double,Character> entry : this.CharsAndOriginalValues.entrySet()){
            double entryKey = entry.getKey();
            double newNormalKey = (entryKey - minBrightnessVal)/(maxBrightnessVal-minBrightnessVal);
            this.CharsAndNormalValues.put(newNormalKey, entry.getValue());
        }

    }


    /**
     * adds character to the current charSet.
     * @param c - character to add
     */
    public void addChar(char c){
        double charBrightness = getBrightnessOfChar(c);
        this.charsMap.put(c, charBrightness);
        this.CharsAndOriginalValues.put(charBrightness, c);
        changesInCharSetSinceLastRun = true;
    }

    /**
     * removes character from the current charSet.
     * @param c - character to remove
     *
     * if c isn't in the current charSet throw a NullPointerException (runtimeException)
     */
    public void removeChar(char c){
        this.CharsAndOriginalValues.remove(this.charsMap.get(c));
        this.charsMap.remove(c);
        changesInCharSetSinceLastRun = true;
    }

    /**
     * prints all the chars in the current charSet in ascending order (ascii value).
     */
    public void printAllChars(){
        for (Character character : charsMap.keySet()) {
            System.out.print(character + " ");
        }
    }

    /**
     * checks if the current charset is empty.
     * @return true if empty, false otherwise
     */
    public boolean isEmpty(){
        return charsMap.isEmpty();
    }
}
