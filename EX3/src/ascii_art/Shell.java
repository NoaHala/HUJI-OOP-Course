package ascii_art;

import exceptions_extention.CharSetIsEmptyException;
import exceptions_extention.OpenImageException;
import exceptions_extention.ResOutOfBoundException;
import ascii_output.AsciiOutput;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.Image;
import image.ImageProcessHelperMethods;
import image_char_matching.SubImgCharMatcher;
import java.io.IOException;
import static java.lang.Math.max;

/**
 * Class Shell - facade for creating ascii art.
 */
public class Shell {

    //constants
    private static final String DEFAULT_IMG_PATH = "cat.jpeg";
    private static final int DEFAULT_RES = 128;
    private static final char[] DEFAULT_CHAR_SET = "0123456789".toCharArray();
    private static final char FIRST_ASCII_CHAR = (char) 32;
    private static final char LAST_ASCII_CHAR = (char) 126;
    private static final String CHARS_COMMAND = "chars";
    private static final String ADD_COMMAND = "add";
    private static final String REMOVE_COMMAND = "remove";
    private static final String RES_COMMAND = "res";
    private static final String IMAGE_COMMAND = "image";
    private static final String OUTPUT_COMMAND = "output";
    private static final String ASCII_ART_COMMAND = "asciiArt";
    private static final String CONSOLE_OUTPUT_COMMAND = "console";
    private static final String HTML_OUTPUT_COMMAND = "html";

    // fields
    private final SubImgCharMatcher subImgCharMatcher;
    private int resolution;
    private Image img;
    private AsciiOutput asciiOutput;
    private AsciiArtAlgorithm asciiArtAlgorithm;
    private boolean imageChanged = false;
    private boolean resChanged = false;


    /**
     * Constructor.
     * sets all the default values and uses them to create new "asciiArtAlgorithm" object
     */
    Shell() {
        this.resolution = DEFAULT_RES;
        this.asciiOutput = new ConsoleAsciiOutput();
        this.subImgCharMatcher = new SubImgCharMatcher(DEFAULT_CHAR_SET);
        try {
            this.img = new Image(DEFAULT_IMG_PATH);
        }
        catch (IOException imgIOException){
            System.out.println("there is a problem with the default image, closing program");
            return;
        }
        this.asciiArtAlgorithm = new AsciiArtAlgorithm(img,resolution,subImgCharMatcher);
    }


    /**
     * activates the full process of creating ascii art.
     * gets different commands from the user for:
     * 1. changing or printing the ascii char set for the art
     * 2. changing the image or the resolution or the output format
     * 3. create the wanted ascii art
     * <p>
     * assumes (as allowed in the exercise's instructions) that the commands given by the
     * user are always with the max length of 2 words.
     */
    public void run(){
        System.out.print(">>> ");
        String[] currentInput = KeyboardInput.readLine().split(" ");

        while (!currentInput[0].equals("exit")){
            //if there is only one command
            if (currentInput.length == 1){
                currentInput = new String[] {currentInput[0], ""};
            }

            //executes behavior according to the given command
            try{
                commandFromUser(currentInput);
            }
            catch (OpenImageException e){
                System.out.println("Did not execute due to problem with image file.");
            }
            catch (ResOutOfBoundException e){
                System.out.println("Did not change resolution due to exceeding boundaries.");
            }
            catch (IOException e){
                printErrorMsg(currentInput[0]);
            }
            catch (CharSetIsEmptyException e) {
                System.out.println("Did not execute. Charset is empty.");
            }


            //asks for the next command
            System.out.print(">>> ");
            currentInput = KeyboardInput.readLine().split(" ");
        }
    }

    /*
    prints the relevant error message.
    called in cases of commands at wrong format.
     */
    private void printErrorMsg(String command) {
        switch (command){
            case ADD_COMMAND:
                System.out.println("Did not add due to incorrect format.");
                break;
            case REMOVE_COMMAND:
                System.out.println("Did not remove due to incorrect format.");
                break;
            case RES_COMMAND:
                System.out.println("Did not change resolution due to incorrect format.");
                break;
            case OUTPUT_COMMAND:
                System.out.println("Did not change output method due to incorrect format.");
                break;
            case ASCII_ART_COMMAND:
                System.out.println("Did not execute. Charset is empty.");
                break;
            default:
                System.out.println("Did not execute due to incorrect command.");
        }
    }

    /*
    activates the wanted behavior according to the command given by the user.
    throws exception for any case of invalid command or execution problem.
     */
    private void commandFromUser(String[] command)
            throws IOException, ResOutOfBoundException, CharSetIsEmptyException, OpenImageException {
        switch (command[0]){
            case CHARS_COMMAND:
                printCharSet();
                break;
            case ADD_COMMAND:
                changeChars(command[1], ADD_COMMAND);
                break;
            case REMOVE_COMMAND:
                changeChars(command[1], REMOVE_COMMAND);
                break;
            case RES_COMMAND:
                changeResolution(command[1]);
                break;
            case IMAGE_COMMAND:
                changeImage(command[1]);
                break;
            case OUTPUT_COMMAND:
                changeOutput(command[1]);
                break;
            case ASCII_ART_COMMAND:
                runAsciiArt();
                break;
            default:
                throw new IOException();
        }
    }

    /*
    sub-method. runs the ascii art command with the current data.
    notice we're not defining special behavior if after 'asciiArt' appears another word
     */
    private void runAsciiArt() throws CharSetIsEmptyException {
        //checks if the current charSet is empty.
        if(this.subImgCharMatcher.isEmpty()){
            throw new CharSetIsEmptyException();
        }

        // checks if the image or resolution changed, and new brightness calculations are needed.
         if(imageChanged || resChanged){
             Image imageWithFrame = ImageProcessHelperMethods.addWhiteFrame(this.img);
             this.asciiArtAlgorithm = new AsciiArtAlgorithm(imageWithFrame, resolution,subImgCharMatcher);
         }

         // runs the ascii art algorithm, presents the results at the required output format.
         char[][] asciiImg = this.asciiArtAlgorithm.run();
         asciiOutput.out(asciiImg);
         resChanged = false;
         imageChanged = false;
    }

    /*
    sub-method. changes the output format.
     */
    private void changeOutput(String command) throws IOException {
         switch (command){
             case CONSOLE_OUTPUT_COMMAND:
                 if (this.asciiOutput instanceof HtmlAsciiOutput){
                     this.asciiOutput = new ConsoleAsciiOutput();
                 }
                 break;
             case HTML_OUTPUT_COMMAND:
                 if (this.asciiOutput instanceof ConsoleAsciiOutput){
                     this.asciiOutput = new HtmlAsciiOutput("out.html", "Courier New");
                 }
                 break;
             default:
                 throw new IOException();
         }
    }

    /*
    sub-method. changes the image.
     */
    private void changeImage(String path) throws OpenImageException {
        try {
            this.img = new Image(path);
        }
        catch (IOException ioException){
            throw new OpenImageException();
        }

        imageChanged = true;
    }

    /*
    sub-method. changes the resolution.
     */
    private void changeResolution(String command) throws ResOutOfBoundException, IOException {
        int maxRes = this.img.getWidth();
        int minRes = max(1, this.img.getWidth()/this.img.getHeight());
        switch (command){
            case "up":
                if((this.resolution * 2) > maxRes){
                    throw new ResOutOfBoundException();
                }
                this.resolution *= 2;
                break;
            case "down":
                if((this.resolution / 2) < minRes){
                    throw new ResOutOfBoundException();
                }
                this.resolution /= 2;
                break;
            default:
                throw new IOException();
        }

        System.out.println("Resolution set to " +  this.resolution);
        resChanged = true;
    }

    /*
    sub-method. changes the charSet (adds/removes).
     */
    private void changeChars(String currentInput,String command) throws IOException {
        String chars = currentInput;

        if (chars.length() == 1){
            changeSingleChar(chars.charAt(0), command);
            return;
        }

        if (chars.equals("space")){
            changeSingleChar(' ',command);
            return;
        }

        if ((chars.length() == 3) && (chars.charAt(1) == '-')){
            if (chars.charAt(0) > chars.charAt(2)){
                changeCharsInRange(chars.charAt(2), chars.charAt(0), command);
            }
            else {
                changeCharsInRange(chars.charAt(0), chars.charAt(2), command);
            }
            return;
        }

        if (chars.equals("all")){
            changeCharsInRange(FIRST_ASCII_CHAR,LAST_ASCII_CHAR, command);
            return;
        }

        throw new IOException();
    }

    /*
    sub-method. changes single character at the charSet (adds/removes).
     */
    private void changeSingleChar(char c, String command) {
        switch (command){
            case ADD_COMMAND:
                this.subImgCharMatcher.addChar(c);
                break;
            case REMOVE_COMMAND:
                try {
                    this.subImgCharMatcher.removeChar(c);
                }
                //if tries to remove a char that doesn't exist in the charSet
                catch (NullPointerException ignored){}
                break;
        }
    }

    /*
    sub-method. changes all the character at a given range in the charSet (adds/removes).
     */
    private void changeCharsInRange(char first, char last, String command) {
        for (char cur = first; cur <= last; cur++){
            changeSingleChar(cur, command);
        }
    }

    /*
    sub-method. prints all the characters in the charSet in ascending order (ascii value).
    notice we're not defining special behavior if after 'chars' appears another word.
     */
    private void printCharSet() {
         subImgCharMatcher.printAllChars();
    }

    /**
     * Main method - creates shell and executes it.
     * @param args - CLI
     */
    public static void main(String[] args) {
         Shell shell = new Shell();
         shell.run();
    }
}
