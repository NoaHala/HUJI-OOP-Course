package image;

import java.awt.*;

/**
 * collection of static methods that required for different purposes at the process of
 * the ascii art creation
 */
public class ImageProcessHelperMethods {

    //fields
    private static final int MAX_RGB_VAL = 255;

    /**
     * the method adds vertical/horizontal white boarder lines if needed, to adapt the
     * image proportions to sizes that can be used int the art process
     * @param originalImage - the image before padding
     * @return image after padding
     */
    public static Image addWhiteFrame(Image originalImage){
        // checks current proportions and decide what the new proportions should be
        int newHeight = (int)Math.pow(2,
                (int)(Math.ceil(Math.log(originalImage.getHeight()) / Math.log(2))));
        int newWidth = (int)Math.pow(2,
                (int)(Math.ceil(Math.log(originalImage.getWidth()) / Math.log(2))));

        //creates the new pixel array in the relevant size
        Color[][] newPixelArray = new Color[newHeight][newWidth];

        //add frames
        addUpperFrame(originalImage, newPixelArray, newHeight,newWidth);
        addSidesFrame(originalImage, newPixelArray, newHeight,newWidth);
        addLowerFrame(originalImage, newPixelArray, newHeight,newWidth);

        //return new image with frame
        return new Image(newPixelArray, newWidth,newHeight);
    }

    /*
    sub-method adds the lower-vertical white boarder line.
     */
    private static void addLowerFrame(Image originalImage, Color[][] newPixelArray,
                                      int newHeight, int newWidth) {
        int rowsForVerticalFrame = (newHeight- originalImage.getHeight()) / 2;
        for (int whiteRow = newHeight; whiteRow > newHeight-rowsForVerticalFrame; whiteRow--) {
            for (int col = 0; col < newWidth; col++) {
                newPixelArray[whiteRow-1][col] = Color.WHITE;
            }
        }
    }

    /*
    sub-method adds the horizontal white boarder lines.
     */
    private static void addSidesFrame(Image originalImage, Color[][] newPixelArray,
                                      int newHeight, int newWidth) {

        int rowsForVerticalFrame = (newHeight- originalImage.getHeight()) / 2;
        int rowsForHorizontalFrame = (newWidth- originalImage.getWidth()) / 2;

        for (int row = rowsForVerticalFrame; row < originalImage.getHeight()+ rowsForVerticalFrame; row++) {
            for (int col = 0; col < newWidth; col++) {
                if (col < rowsForHorizontalFrame || col >= (newWidth-rowsForHorizontalFrame)){
                    newPixelArray[row][col] = Color.WHITE;
                }
                else {
                    newPixelArray[row][col] = originalImage.getPixel(
                            row-rowsForVerticalFrame,col - rowsForHorizontalFrame);
                }
            }
        }

    }

    /*
    sub-method adds the upper-vertical white boarder line.
     */
    private static void addUpperFrame(Image originalImage, Color[][] newPixelArray,
                                      int newHeight, int newWidth) {

        int rowsForVerticalFrame = (newHeight- originalImage.getHeight()) / 2;
        for (int whiteRow = 0; whiteRow < rowsForVerticalFrame; whiteRow++) {
            for (int col = 0; col < newWidth; col++) {
                newPixelArray[whiteRow][col] = Color.WHITE;
            }
        }
    }

    /**
     * splitting the image into an array of sub-images
     * @param res - required resolution to split to
     * @param img - the image to split
     * @return an array of sub-images according to the given resolution
     *
     * assumes valid arguments
     */
    public static Image[] splitToSubImgs(int res, Image img){
        int subImgSize = img.getWidth()/res;
        int subImgRows = img.getHeight()/ subImgSize;
        Image[] subImgsArray = new Image[res*subImgRows];
        int subImgCounter = 0;

        //loop through all the sub images
        for (int subImgRow = 0; subImgRow < subImgRows; subImgRow++) {
            for (int subImgCol = 0; subImgCol < res; subImgCol++) {
                //for each sub image - create new sub image, duplicate the pixels, return
                subImgsArray[subImgCounter] = createSubImg(img,subImgSize,subImgRow,subImgCol);
                subImgCounter++;
            }
        }

        return subImgsArray;
    }

    /*
    creates sub-image with the wanted coordinates
     */
    private static Image createSubImg(Image img, int subImgSize,int subImgRow, int subImgCol) {
        Color[][] subPixels = new Color[subImgSize][subImgSize];
        for (int row = 0; row < subImgSize; row++) {
            for (int col = 0; col < subImgSize; col++) {
                subPixels[row][col] =
                        img.getPixel(subImgRow*subImgSize+row, subImgCol*subImgSize+col);
            }
        }
        return new Image(subPixels,subImgSize, subImgSize);
    }


    /**
     * calculates the brightness value of a given image
     * @param img - given image
     * @return brightness value (double) between 0-255
     */
    public static double brightnessOfImg(Image img){
        double sumOfGrey = 0;
        for (int row = 0; row < img.getHeight(); row++) {
            for (int col = 0; col < img.getWidth(); col++) {
                double greyPixel = calcGreyPixel(img.getPixel(row,col));
                sumOfGrey += greyPixel;
            }
        }

        int amountOfPixels = img.getWidth()*img.getHeight();
        return (sumOfGrey/amountOfPixels/MAX_RGB_VAL);
    }

    /*
    converts the color values of a pixel to gray with matching brightness value
     */
    private static double calcGreyPixel(Color pixel) {
        return (pixel.getRed() * 0.2126 +
                pixel.getGreen() * 0.7152 +
                pixel.getBlue() * 0.0722);
    }

}
