import java.util.Iterator;

/**
 * This interface maps data keys to multiple values. The keys are ordered and the interface supports range queries.
 */
public interface SortedMultiMap<KeyType extends Comparable<KeyType>, ValueType> {

    /**
     * This methods adds a key, value mapping. If the key already exists in
     * the dataset, an additional mapping from the key to the new value is added.
     * @param key the key to map to value
     * @param value the value the key should map to
     * @return true if this is the first insertion of a value for key, false if not
     */
    public boolean put(KeyType key, ValueType value);

    /**
     * This method returns the smallest key in the mapping.
     * @return return the smallest key in the mapping, or null if the mapping is empty
     */
    public KeyType getSmallestKey();

    /**
     * This method returns the largest key in the mapping.
     * @return return the largest key in the mapping, or null if the mapping is empty
     */
    public KeyType getLargestKey();

    /**
     * Returns the first (or any, depending on the concrete implementation) value mapped to key,
     * or null if no such mapping exists
     * @param key the key for which to return a value
     * @return the first value found that key maps to
     */
    public ValueType getFirst(KeyType key);

    /**
     * Returns an iterator over all values mapped to the key.
     * @param key the key for which values are returned
     * @return an iterator over all values key maps to
     */
    public Iterator<ValueType> getAll(KeyType key);

    /**
     * Returns all values that are mapped to keys between the start key (including values mapped to the start key)
     * and the end key (including values mapped to the end key).
     * @param startKey the starting key for the range (inclusive)
     * @param endKey the end key for the range (inclusive)
     * @return an iterator over all values mapped to keys from the start key to the end key
     */
    public Iterator<ValueType> getRange(KeyType startKey, KeyType endKey);

    /**
     * Return true only if key is in the mapping.
     * @param key the key to search for
     * @return true if key is in the mapping, false otherwise
     */
    public boolean containsKey(KeyType key);

    /**
     * Returns the number of key, value pairs in the mapping.
     * @return the number of key, value pairs in the mapping
     */
    public int size();
    
}
