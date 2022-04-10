import java.text.ParseException;

/**
 * Each instance of this class represents a named color based on its addative
 * red, green, and blue components of light.  In addition, each color object
 * maintains it's hue which is used to compare and order colors along a 
 * spectrum from red to blue.
 */
public class Color implements Comparable<Color> {
    private String name; // human readable name
    // red, green, and blue are specified from min:0 to max:255 intensities
    private int red; 
    private int green; 
    private int blue; 
    // hue is 0-360 degrees, and wraps beyond that range (like to angle measurements)
    private double hue; 

    /**
     * Constructs a new color object.
     * @param name human readable name
     * @param red light intensity ranges from min:0 to max:255
     * @param green light intensity ranges from min:0 to max:255
     * @param blue light intensity ranges from min:0 to max:255
     */
    public Color(String name, int red, int green, int blue) {
        this.name = name;
        this.red = red;
        this.green = green;
        this.blue = blue;

        // compute hue: 0 => 360 degrees (from red to green to blue)
        int min = Math.min(Math.min(red,green),blue);
        // for colors with no hue: black, gray, white, etc. map brightnesses to hues
        // (for lack of a better default) so that black = red, and white = blue
        // map brightness to hue, so that these colors are spread out (for lack of better default)
        if(red == green && green == blue) // No Hue: Black, Gray, White, etc.
            this.hue = 4*(red/255.0); // map colors without hue by intensity: Black=Red, White=Blue
        // compute hue for those colors which are clearly more red, green, or blue            
        else if(red >= green && red >= blue) // Red max
            this.hue = (double)(green-blue)/(red-min);
        else if( green >= red && green >= blue) // Green max
            this.hue = 2 + (double)(blue-red)/(green-min);
        else // Blue max
            this.hue = 4 + (double)(red-green)/(blue-min);
        // map hue from -1 => 5 to -60 => 300
        this.hue *= 60;
        // map -60 => 300 range to 0 => 360
        if(this.hue < 0) this.hue += 360;
    }
    /**
     * Constructs a new color object.
     * @param name human readable name
     * @param hexRGB hexidecimal red, green, and blue light intensities
     */
    public Color(String name, String hexRGB) {
        this(name,
            Integer.parseInt(hexRGB.substring(1,3),16),
            Integer.parseInt(hexRGB.substring(3,5),16),
            Integer.parseInt(hexRGB.substring(5,7),16));
    }
    
    /**
     * Creates a new color object based on the contents of a comma separated line
     * of text.  This text can be presented in one of two forms:
     *     first form: colorName, red, green, blue
     *     second form: colorName, #rrggbb
     * where red, green, and blue are 0-255 color intensities, and rr, gg, bb are
     * hexidecimal color intensities in those same ranges.
     * @param csvLine text representation of color object
     * @return the coresponding color object
     * @throws ParseException when problems are encountered parsing csvLine
     */
    public static Color parse(String csvLine) throws ParseException {
        try {
            // split comma separated line by commas
        	String[] parts = csvLine.split(",");
            // the first part of this line is always the color's name
            String name = parts[0].trim();
            // the rest of this line might take one of two forms:
            int red, green, blue;
            parts[1] = parts[1].trim();
            // it could be a single hex string of the form: #rrggbb
            if(parts[1].startsWith("#") && parts[1].length() == 7) { // read hex components
                red = Integer.parseInt(parts[1].substring(1,3),16);
                green = Integer.parseInt(parts[1].substring(3,5),16);
                blue = Integer.parseInt(parts[1].substring(5,7),16);
            // or it could be the intensity of red light, 
            // which should be followed by separate green and blue
            } else  {
                red = Integer.parseInt(parts[1]);
                green = Integer.parseInt(parts[2].trim());
                blue = Integer.parseInt(parts[3].trim());
                if( red < 0 || green < 0 || blue < 0 || 
                    red > 255 || green > 255 || blue > 255 )
                    throw new ParseException("Failed to read color components in the expected range 0-255 from line: "+csvLine, 0);
            }
            return new Color(name,red,green,blue);    
        } catch(IndexOutOfBoundsException e) {
            throw new ParseException("Failed to find the expect number of commas in the line: "+csvLine, 0);
        } catch(NumberFormatException e) {
            throw new ParseException("Failed to extract three numbers from the line: "+csvLine, 0);
        }
    }

    // simple accessor methods
    public String getName() { return name; }
    public int getRed() { return red; }
    public int getGreen() { return green; }
    public int getBlue() { return blue; }
    public double getHue() { return hue; }
    public String getRGBInts() { return "("+getRed()+","+getGreen()+","+getBlue()+")"; }
    public String getRGBHex() { return "#" + String.format("%02x",getRed()) 
        + String.format("%02x",getGreen()) + String.format("%02x",getBlue()); }

    /**
     * Colors are compared based on their hues, with red hues being earlier
     * in the natural ordering than blue hues.
     * @param other is what this Color is being compared to
     * @return a negative number when this color is earlier in the natural ordering
     * (more red) than the other, a positive number when this color is later in the
     * natural ordering (more blue) than the other, and 0 when the two colors share
     * the same hue.
     */
    public int compareTo(Color other) {
        // hue is 0.0-360.0, so make int comparison between 0-1,440,000,000
        return (int)((this.getHue() - other.getHue())*4000000);
    }

    /**
     * Two colors objects are considered equal when they have the same hue.  Colors
     * objects should not be considered equal to non-color objects.
     * @param other is what this Color is being compared to
     * @return true when this and the other color are equal, and false otherwise
     */
    public boolean equals(Object other) {
        if(!(other instanceof Color)) return false;
        return compareTo((Color)other) == 0;
    }

    /**
     * @return a String composing the name, red-green-blue compoenets, and hue
     * of this color object.
     */
    public String toString() {
        return getName() + " has HUE:"+getHue() + " and (RED,GREEN,BLUE): " + getRGBInts();
    }
}
