package ascii_art;

import image.Image;
import image.ImageProcessHelperMethods;
import image_char_matching.SubImgCharMatcher;

import java.util.HashMap;


/**
 * AsciiArtAlgorithm class - represents the algorithm being used to create the ascii art
 */
public class AsciiArtAlgorithm {

    // fields
    private final Image imageWithFrame;
    private final int res;
    private final SubImgCharMatcher subImgCharMatcher;
    private Image[] arrayOfSubImgs;
    private final HashMap<Image,Double> subImageAndBrightnessVal = new HashMap<>();

    /**
     * Constructor - sets all the ascii art fields with the default values
     * @param imageWithFrame - Image object for the ascii art, after padding*
     * @param res - the wanted resolution for the art
     * @param subImgCharMatcher - an object that manages the entire charSet data structure and matches
     *                          chars by brightness value
     * <p>
     * *padding - adding white boarder lines if needed to adapt the image proportions
     */
    public AsciiArtAlgorithm(Image imageWithFrame, int res, SubImgCharMatcher subImgCharMatcher) {
        this.imageWithFrame = imageWithFrame;
        this.res = res;
        this.subImgCharMatcher = subImgCharMatcher;
        this.arrayOfSubImgs = ImageProcessHelperMethods.splitToSubImgs(res,imageWithFrame);
        for (Image subImg: arrayOfSubImgs) {
            double brightnessOfSubImg = ImageProcessHelperMethods.brightnessOfImg(subImg);
            this.subImageAndBrightnessVal.put(subImg,brightnessOfSubImg);
        }
    }

    /**
     * runs the ascii art algorithm with the given arguments
     * @return 2-dimensions array of chars that represents the ascii image
     */
    public char[][] run(){
        int subImgCounter = 0;
        int howManyRowsOfChar =
                imageWithFrame.getHeight()/ (imageWithFrame.getWidth()/ res);
        char[][] finalAsciiImg = new char[howManyRowsOfChar][res];

        for (Image subImg :arrayOfSubImgs) {
            double brightnessOfSubImg = subImageAndBrightnessVal.get(subImg);
            char closestChar = subImgCharMatcher.getCharByImageBrightness(brightnessOfSubImg);
            finalAsciiImg[subImgCounter/res][subImgCounter%res] = closestChar;
            subImgCounter++;
        }

        return finalAsciiImg;
    }
}
