
import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * This class implements a console application that allows its user to search
 * through a collection of named colors by hue.  When exact matches are found,
 * all such matches are displayed for the user.  Otherwise, only the closest 
 * hue(s) will be displayed. 
 */
public class HueFinderConsole {

    /**
     * Application entry point.
     * @param args are ignored by this program
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        new HueFinderConsole().run(in);
    }

    /**
     * Runs this program once: searching the available named colors once for the
     * closes matches in hue.
     * @param in where the user specified color is read from System.in through
     */
    public void run(Scanner in) {
        Tree234<Color,Color> tree = readNamedColorsFromFolder( new File(".") );

        System.out.println("Enter the color that you would like to search for: ");
        Color search = readColorFromUser(in);

        System.out.println();
        displayClosestColors(search,tree);
    }

    /**
     * Reads the named colors from all .csv files found in the specified directory.
     * @param dir is the folder that is searched for csv files containing colors
     * @return a 2-3-4 tree containing all of the colors read out of csv files
     */
    public static Tree234<Color,Color> readNamedColorsFromFolder(File dir) {
        Tree234<Color,Color> tree = new Tree234<>(); // collection of colors read

        // read from all files ending with .csv in the specified directory
        Stream<File> fileStream = Arrays.stream(dir.listFiles());
        fileStream.filter(f->f.getName().endsWith(".csv")).forEach(f->{

            // for each file read:
            try(Scanner fin = new Scanner(f)) {
                fin.nextLine(); // skip first line of header information
                String nextLine="";
                while(fin.hasNextLine())

                    // for each line within that file:
                    try {
                        nextLine = fin.nextLine();
                        Color nextColor = Color.parse(nextLine);
                        tree.insert(nextColor,nextColor);
                    } catch(ParseException e) {
                        System.err.println("Unable to parse line: "+nextLine);
                        e.printStackTrace(); // prints to standard error
                    }

            } catch(FileNotFoundException e) {
                System.err.println("Unable to read from file: "+f.getName());
            }
        });
        if(tree.getSize() < 1) // only throw this exception when no colors are ready from any files
            throw new NoSuchElementException("Could not read any colors from directory: "+dir.getAbsolutePath());
        return tree;
    }

    /**
     * Prompts user for and then reads red, green, and blue components 
     * of search color from the user, and returns a corresponding color object. 
     * @param in the scanner reading user input through System.in
     * @return the color object corresponding to the color entered by the user
     */
    public static Color readColorFromUser(Scanner in) {
        System.out.print("Enter the RED component of color (1-255): ");
        int red = in.nextInt();
        System.out.print("Enter the GREEN component of color (1-255): ");
        int green = in.nextInt();
        System.out.print("Enter the BLUE component of color (1-255): ");
        int blue = in.nextInt();
        return new Color("noname: searching for similar...",red,green,blue);
    }

    /**
     * Search tree for named colors with matching hues, and display all that are found.
     * If no matches are found, then instead display the closest in order predecessors
     * and successors from that same tree.  These results are displayed to System.out.
     * @param search is the color being searched for
     * @param tree is the sorted collection of named colors that is being searched
     */
    public static void displayClosestColors(Color search, Tree234<Color,Color> tree) {
        System.out.println("Searching for color "+search.getRGBInts()+" with Hue: "+search.getHue());
        LinkedList<Color> found = tree.get(search);

        // display all colors found with matching hue
        if(found.size() > 0) {
            System.out.println("Found the following colors with matching hues: ");
            for(Color c : found)
                System.out.println("    * "+c.toString());

        // otherwise display the closes colors in the red and blue directions
        } else { // if(found.size() < 1) {
            System.out.println("Could NOT find color with matching Hue.");
            Color pred = tree.getPred(search);
            if(pred != null)
                System.out.println("Closest color in the blue-direction is: "+pred.toString());
            Color succ = tree.getSucc(search);
            if(succ != null)
                System.out.println("Closest color in the red-direction is: "+succ.toString());
        }
    }
}
