import java.util.HashMap;
import java.util.List;

/**
 * This class represents a data instance by mapping string attributes to values of strings that can be
 * converted to other data types if necessary.
 */
public class DataInstance {

    // hash table that stores attribute name, value mappings for a data instance
    protected HashMap<String,String> attributeNamesToValuesMap = new HashMap<String,String>();
   
    /**
     * Constructor that creates a new data instance from lists of attribute names and values.
     * @param attributeNames list of attribute names
     * @param attributeValues list of attribute values that map to the attribute names
     */
    public DataInstance(List<String> attributeNames, List<String> attributeValues) {
        if (attributeNames.size() != attributeValues.size()) {
            System.out.println(attributeNames);
            System.out.println(attributeValues);
            throw new IllegalArgumentException("attributeNames list and attributeValues list must have the same size");
        }
        for (int i = 0; i < attributeNames.size(); i++) {
            attributeNamesToValuesMap.put(attributeNames.get(i), attributeValues.get(i));
        }
    }

    /**
     * Getter method for an attribute that returns the value mapped to it.
     * @param attributeName the attribute name
     * @return the value mapped to it for this data instance as a string
     */
    public String getAttributeValue(String attributeName) {
        return attributeNamesToValuesMap.get(attributeName);
    }

    /**
     * Getter method for an attribute that returns the value mapped to it converted to a Boolean.
     * This method will throw an exception if the attribute's string value cannot be converted to a Boolean.
     * @param attributeName the attribute name
     * @return the value mapped to it for this data instance as a Boolean
     */
    public Boolean getAttributeAsBoolean(String attributeName) {
        return Boolean.valueOf(this.getAttributeValue(attributeName));
    }

    /**
     * Getter method for an attribute that returns the value mapped to it converted to an Integer.
     * This method will throw an exception if the attribute's string value cannot be converted to an Integer.
     * @param attributeName the attribute name
     * @return the value mapped to it for this data instance as an Integer
     */
    public Integer getAttributeAsInteger(String attributeName) {
        return Integer.valueOf(this.getAttributeValue(attributeName));
    }

    /**
     * Getter method for an attribute that returns the value mapped to it converted to a Long.
     * This method will throw an exception if the attribute's string value cannot be converted to a Long.
     * @param attributeName the attribute name
     * @return the value mapped to it for this data instance as a Long
     */
    public Long getAttributeAsLong(String attributeName) {
        return Long.valueOf(this.getAttributeValue(attributeName));
    }

    /**
     * Getter method for an attribute that returns the value mapped to it converted to a Float.
     * This method will throw an exception if the attribute's string value cannot be converted to a Float.
     * @param attributeName the attribute name
     * @return the value mapped to it for this data instance as a Float
     */
    public Float getAttributeAsFloat(String attributeName) {
        return Float.valueOf(this.getAttributeValue(attributeName));
    }

    /**
     * Getter method for an attribute that returns the value mapped to it converted to a Double.
     * This method will throw an exception if the attribute's string value cannot be converted to a Double.
     * @param attributeName the attribute name
     * @return the value mapped to it for this data instance as a Double
     */
    public Double getAttributeAsDouble(String attributeName) {
        return Double.valueOf(this.getAttributeValue(attributeName));
    }

}
