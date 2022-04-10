import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * An implementation of the SortedMultiMap interface that is based on a skip list data structure that stures key, value
 * pairs and supports multiple mapping to the same key.
 * @param <KeyType> the key data type used in the skip list
 * @param <ValueType> the value data type used in the skip list
 */
public class SkipList<KeyType extends Comparable<KeyType>, ValueType> implements SortedMultiMap<KeyType, ValueType> {

    /**
     * A node of the skip list data structure. A node stores a key, value, and an array of next
     * references for each of the lanes that the node is part on. Those next references point to
     * the successor node on the lane, or 'null', if the node is the last element of the lane.
     */
    protected class SkipNode {

        // next references are stored as an object array which will store SkipNode
        // objects to get around the java restrictions for generic arrays
        public Object[] next;
        public KeyType key;
        public ValueType value;

        /**
         * Constructor that instantiates a new skip node object
         * @param key the key to store in the node
         * @param value the value that the node maps the key to
         * @param level the maximum level that the node is on (with 0 being the base lane of the skip list)
         */
        public SkipNode(KeyType key, ValueType value, int level) {
            this.next = new Object[level+1];
            this.key = key;
            this.value = value;
        }

        /**
         * This is a helper / convenience method that returns a skip node reference from
         * the next array and casts it from Object to SkipNode.
         */
        @SuppressWarnings("unchecked")
        public SkipNode getNext(int level) {
            return (SkipNode)this.next[level];
        }

    }

    /**
     * HeadNode is a subclass of SkipNode that represents the first node on each of the lanes.
     * The HeadNode doess not store data itself (its key and value reference point to null), but
     * it stores the reference to each of the first SkipNodes on every lane (or null if a lane is
     * empty).
     */
    protected class HeadNode extends SkipNode {
       
        /**
         * Constructor creates a new HeadNode.
         * @param level the maximum level that starts with the 
         */
        public HeadNode(int level) {
            super(null, null, level);
        }

        /**
         * Constructor creates a new HeadNode by extending the number of lanes form a previous
         * HeadNode and copying over its lane references.
         * @param level
         * @param oldHead
         */
        public HeadNode(int level, HeadNode oldHead) {
            this(level);
            for (int i = 0; i < oldHead.next.length; i++) {
                this.next[i] = oldHead.next[i];
            }
        }

    }

    // keep track of the number of key dublicates
    protected int numberOfKeyDuplicates = 0;
    // keep track of the number of unique keys
    // (we will base the number of lanes on this
    // number to keep the skip list efficient)
    protected int numberOfUniqueKeys = 0;
    // reference to the head node for the skip list
    protected HeadNode head;
    // store the log_10 of 2 for number of lane computation
    protected double logBase10Of2 = Math.log10(2);
    // random number generator for flipping coins
    protected Random rand = new Random();

    /**
     * Contructor to create a new skip list.
     */
    public SkipList() {
        // initial head node only references one lane
        this.head = new HeadNode(0);
    }

    /**
     * Constructor to create a new skip list and set a specific seed for the random number generator.
     * @param randomSeed
     */
    public SkipList(long randomSeed) {
        this.rand.setSeed(randomSeed);
    }

    /**
     * Returns true if key is in the skip list at least once.
     */
    @Override
    public boolean containsKey(KeyType key) {
        return this.getInternal(key) != null;
    }

    /**
     * Internal helper method to find a key in the skip list.
     * Uses the findInternalClosest method. In case findInternalClosest
     * returns a node with the actual key, we will return this node.
     * If the key in the node returned by getInternalClosest is smaller
     * than the key we are looking for, we know that our key is not in
     * the data structure, and we will return null instead.
     * @param key the key to look for
     * @return (first) skip node that contains the key
     */
    protected SkipNode getInternal(KeyType key) {
        // use the getInternalClosest method, which returns
        // the node with the key we ar looking for or a key
        // that is smaller than it
        SkipNode nodeFound = this.getInternalClosest(key);
        if (nodeFound == null // we did not find a node
                || nodeFound.key == null // we only found a head node
                || !nodeFound.key.equals(key) // we found a node with a different key
           )
            return null;
        return nodeFound;
    }

    /**
     * Internal helper method that returns the SkipNode with the key that's
     * smaller bust closest to the key we are looking for. We find this key
     * by using the skip list lookup method and move closer to the key we
     * look for by following the references in the skip list structure.
     * Once we have either found the key we are looking for, or the next key
     * we find is larger than the one we are looking for, we stop the search.
     * In case we have found our key, we return the (first) skip node with
     * that key, otherwise we will return a skip node that contains a key that
     * is smaller than the key we were looking for.
     * @key key to search for
     * @return the skip node with closest key that smaller or equal to key
     */
    protected SkipNode getInternalClosest(KeyType key) {
        int currentLevel = this.head.next.length - 1;
        SkipNode currentNode = this.head;
        while (currentLevel >= 0) {
            SkipNode nextNode = currentNode.getNext(currentLevel);
            if (nextNode == null || nextNode.key.compareTo(key) > 0) {
                // there is no next node, or the next node has a key that's larger
                // we step one level down
                currentLevel--;
            } else {
                // there is a next node and it has a key that's not larger to ours
                if (nextNode.key.compareTo(key) == 0) {
                    // we've found it
                    return nextNode;
                } else {
                    // next key is smaller then the one in new node, so we take a step
                    // forward on the current lane
                    currentNode = nextNode;
                }
            }
        }
        // if we haven't found it until now, we don't have it, so return the next smaller element
        return currentNode;
    }

    /**
     * Internal helper method to insert a new skip node into the data structure. The key cannot be
     * in the skip list already.
     * @param newNode the new node to insert
     * @return true if the node has been inserted, false if the key is already in the skip list
     */
    protected boolean putInternal(SkipNode newNode) {
        int currentLevel = this.head.next.length - 1;
        SkipNode currentNode = this.head;
        while (currentLevel >= 0) {
            SkipNode nextNode = currentNode.getNext(currentLevel);
            if (nextNode == null || nextNode.key.compareTo(newNode.key) > 0) {
                // there is no next node, or the next node has a key that's larger
                // we insert after current node if the new node is on the current lane
                if (currentLevel < newNode.next.length) {
                    newNode.next[currentLevel] = nextNode;
                    currentNode.next[currentLevel] = newNode;
                }
                // we step one level down
                currentLevel--;
            } else {
                // there is a next node and it has a key that's not larger to ours
                if (nextNode.key.compareTo(newNode.key) == 0) {
                    // key is already in the list, so we won't insert
                    return false;
                } else {
                    // next key is smaller then the one in new node, so we take a step
                    // forward on the current lane
                    currentNode = nextNode;
                }
            }
        }
        this.numberOfUniqueKeys++;
        return true;
    }

    /**
     * Method to insert a new key, value mapping into the skip list.
     * @param key the key to insert
     * @param value the value to map the key to
     * @return true if the inserted key does not exist in the skip list yet, false if it does
     */
    @Override
    public boolean put(KeyType key, ValueType value) {
        // first we check if the key is already in the skip list
        SkipNode firstNodeForKey = this.getInternal(key);
        if (firstNodeForKey == null) {
            // this is the first node for the key, do a regular skip list insertion
            int maxLevels = (int)Math.max(0, Math.log10(this.numberOfUniqueKeys + 1) / this.logBase10Of2);
            int levelOfNewNode = 0;
            while (rand.nextBoolean() && levelOfNewNode <= maxLevels) {
                levelOfNewNode++;
            }
            // if we are on a lane that does not exist yet, extend the head node with additional lanes
            if (levelOfNewNode >= this.head.next.length)
                this.head = new HeadNode(levelOfNewNode, this.head);
            // call putInternal to insert the new node into the skip list
            return this.putInternal(new SkipNode(key, value, levelOfNewNode));
        } else {
            // we already have the key in the mapping, so we insert after the first node for the key
            // only on level 0 so that we keep the skip list structure efficient for lookups
            SkipNode newNode = new SkipNode(key, value, 0);
            newNode.next[0] = firstNodeForKey.next[0];
            firstNodeForKey.next[0] = newNode;
            this.numberOfKeyDuplicates++;
            return false;
        }
    }

    /**
     * Return the smallest key in the skip list.
     * @return the smallest key in the skip list, or null if the skip list is empty
     */
    @Override
    public KeyType getSmallestKey() {
    	if(head == null)
    		return null;
    	else 
    		return head.getNext(0).key;
        //throw new UnsupportedOperationException("This method is not implemented yet");
    }

    /**
     * Return the largest key in the skip list.
     * @return the largest key in the skip list, or null of the skip list is empty
     */
    @Override
    public KeyType getLargestKey() {
//    	KeyType lastKey = null;
//    	SkipNode temp = head.getNext(0);
//    	if(head == null)
//    		return null;
//    	else {
//    		while(temp.getNext(0) != null) {
//    			temp = temp.getNext(0);
//    			lastKey = temp.key;
//    			System.out.println(lastKey);
//    		}
//    		return lastKey;
//    	}
//throw new UnsupportedOperationException("This method is not implemented yet");
    	// start at top level
    	int currentLevel = this.head.next.length - 1;
        SkipNode currentNode = this.head;
        while (currentLevel >= 0) {
            SkipNode nextNode = currentNode.getNext(currentLevel);
            // if no next node, go down a level
            if (nextNode == null) {
                currentLevel--;
            } else {
                    currentNode = nextNode;
            }
        }
        return currentNode.key;
    }

    /**
     * Returns the first (inserted) value in the skip list that is mapped to the
     * provided key.
     * @param key key to search for
     * @return the first value mapped to the key
     */
    @Override
    public ValueType getFirst(KeyType key) {
        SkipNode internalNode = this.getInternal(key);
        if (internalNode == null)
            return null;
        return internalNode.value;
    }

    /**
     * Returns a java.util.Iterator of all values mapped to the key.
     * @param the key to search for
     * @return an iterator over all values mapped to a key
     */
    @Override
    public Iterator<ValueType> getAll(KeyType key) {
        return getRange(key, key);
    	//throw new UnsupportedOperationException("This method is not implemented yet");
    }

    /**
     * Returns a java.util.Iterator for all values mapped to keys that are
     * equal or larger than the start key, and smaller or equal to the end key.
     * @param the start key
     * @param the end keu
     * @return an iterator over all values mapped to keys between start and end
     */
    public Iterator<ValueType> getRange(KeyType start, KeyType end) {
        //throw new UnsupportedOperationException("This method is not implemented yet");
    	
    	final SkipNode begin = getInternalClosest(start);
    	final SkipNode last = getInternalClosest(end);
    	
    	return new Iterator<ValueType>() {
    		SkipNode next = begin;
    		@Override
    		public boolean hasNext() {
    			//System.out.println("hasNext");
    			if(next == head)
    				next = next.getNext(0);
    			if(next == null)
    				return false;
    			return next.key.compareTo(last.key) <= 0;
    		}
    		@Override
    		public ValueType next() {
    			//System.out.println("next");
    			ValueType temp = next.value;
    			next = next.getNext(0);
    			return temp;
    		}
    	};
    }

    /**
     * Return the number of key, value mappings in the skip list.
     * @return number of key, value mappings
     */
    @Override
    public int size() {
        return this.numberOfUniqueKeys + this.numberOfKeyDuplicates;
    }

    /**
     * Returns a string representations of the skip list showing all lanes and keys
     * on those lanes.
     * @return string representation of the skip list
     */
    public String toString() {
        StringBuffer[] outputLines = new StringBuffer[head.next.length];
        for (int i = 0; i < outputLines.length; i++) {
            outputLines[i] = new StringBuffer();
        }

        // iterate over the base lane
        SkipNode currentNode = this.head;
        while (currentNode != null) {
            // iterate through ALL levels
            for (int level = 0; level < head.next.length; level++) {
                // add a dash between keys
                outputLines[level].append("-");
                // if node is on current level
                if (level < currentNode.next.length) {
                    // current node is on current level, so add string
                    if (currentNode.key == null) {
                        // this is a head node
                        outputLines[level].append("h");
                    } else {
                        // this is not a head node
                        outputLines[level].append(currentNode.key.toString());
                    }
                } else {
                    // string length for position in lowest level
                    int lengthAtZero = currentNode.key == null ? 1 : currentNode.key.toString().length();
                    for (int i = 0; i < lengthAtZero; i++)
                        outputLines[level].append("-");
                }
                // append dash at the end also
                outputLines[level].append("-");
            }
            currentNode = currentNode.getNext(0);
        }

        // now combine all lanes into one string representation
        String outputString = "";
        for (int i = outputLines.length - 1; i > 0; i--) {
            outputLines[i].append("\n");
            outputString += outputLines[i].toString();
        }
        outputString += outputLines[0].toString();
        return outputString;
    }

}
