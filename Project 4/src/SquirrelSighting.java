import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * The class represents a data instance for the nyc squirrel sightings data set. It subclasses DataInstance for this.
 */
public class SquirrelSighting extends DataInstance {

    // the date and time format to parse date time instances form the data set
    protected static DateFormat dateFormat = new SimpleDateFormat("aaMMddyyyy", new Locale("en", "US"));
    // the date and time associated with this squirrel sighting
    protected Date datetime = null;

    public SquirrelSighting(List<String> attributeNames, List<String> attributeValues) {
        super(attributeNames, attributeValues);
        // we remove the shift and date attribute from the data map
        // and transform it into a Date object that we can use as keys
        // in the multi map skip list
        String stringAMorPM = this.attributeNamesToValuesMap.get("shift");
        String stringDate = this.attributeNamesToValuesMap.get("date");
        String stringTime = stringAMorPM + stringDate;
        try {
            this.datetime = dateFormat.parse(stringTime);
        } catch (ParseException e) {
            throw new IllegalArgumentException("cannot parse date string \"" + stringTime + "\"", e);
        }
    }

    /**
     * Returns the date and time for this squirrel sighting instance.
     * @return the date and time as a java.util.Date instance
     */
    public Date getDateTime() {
        return datetime;
    }
    
}
