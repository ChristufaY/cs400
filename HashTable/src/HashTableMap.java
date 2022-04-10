// --== CS400 File Header Information ==--
// Name: Christopher Yang
// Email: cyang397@wisc.edu
// Team: Red
// Group: BG
// TA: Brianna Cochran
// Lecturer: Gary Dahl
// Notes to Grader: None
import java.util.NoSuchElementException;
import java.util.LinkedList;

class HashNode<KeyType, ValueType> {
	KeyType key;
	ValueType value;
	public HashNode(KeyType key, ValueType value) {
		this.key = key;
		this.value = value;
	}
	public KeyType getKey() {
		return this.key;
	}
	public ValueType getValue() {
		return this.value;
	}

}
public class HashTableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType>{
	int capacity;
	private LinkedList<HashNode>[] hash;
	int loadFactor;

	public HashTableMap(int capacity) {
		this.capacity = capacity;
		hash = new LinkedList[capacity];
		for(int i = 0; i < capacity; i++) {
			hash[i] = new LinkedList<HashNode>();
		}
	}
	public HashTableMap() {
		this.capacity = 10;
		hash = new LinkedList[capacity];
		for(int i = 0; i < capacity; i++) {
			hash[i] = new LinkedList<HashNode>();
		}
	}
	@Override
	public boolean put(KeyType key, ValueType value) {
		// if key is null
		if(key == null)
			return false;
		int modKey = (Math.abs(key.hashCode())) % capacity;
		// if key/value already exists
		for(int i = 0; i < hash[modKey].size(); i++) {
			// takes the string and compares it to value passed. 
			if(hash[modKey].get(i).getKey().equals(key)) {
				return false;
			}
		}
		HashNode newHash = new HashNode(key, value);
		hash[modKey].add(newHash);
		loadFactor++;
		if((double)loadFactor/capacity >= .85) {
			capacity *= 2;
			LinkedList<HashNode>[] temp = new LinkedList[capacity];
			for(int i = 0; i < capacity; i++) {
				temp[i] = new LinkedList<HashNode>();
			}
			HashNode tempNode;
			Integer tempKey;
			for(int i = 0; i < hash.length; i++) {
				for(int j = 0; j < hash[i].size(); j++) {
					// hold the node in a temp
					tempNode = hash[i].get(j);
					// to rehash and hold the new hash key, hold newly hashed key in tempKey
					tempKey = Math.abs(tempNode.getKey().hashCode()) % capacity;
					// then add new node to the tempKey index because that is what rehashing is
					temp[tempKey].add(tempNode);
				}	
			}
			hash = temp;
		}
		return true;
	}

	@Override
	public ValueType get(KeyType key) throws NoSuchElementException {
		// key is null
		if(key == null)
			throw new NoSuchElementException("Key is null.");
		int modKey = (Math.abs(key.hashCode())) % capacity;
		// element is in the linkedlist
		for(int i = 0; i < hash[modKey].size(); i++) {
			if(hash[modKey].get(i).getKey().equals(key))
				return (ValueType)hash[modKey].get(i).getValue();
		}
		// element is not in list
		throw new NoSuchElementException("No Such Element.");
	}
	@Override
	public int size() {
		return this.loadFactor;
	}
	@Override
	public boolean containsKey(KeyType key) {
		// key is null
		if(key == null)
			return false;

		int modKey = (Math.abs(key.hashCode())) % capacity;
		for(int i = 0; i < hash[modKey].size(); i++) {
			if(hash[modKey].get(i).getKey().equals(key)) {
				// found
				return true;
			}
		}
		// not found
		return false;
	}
	@Override
	public ValueType remove(KeyType key) {
		// if key is null
		if(key == null)
			return null;
		// if it exists
		int modKey = (Math.abs(key.hashCode())) % capacity;
		ValueType temp;
		for(int i = 0; i < hash[modKey].size(); i++) {
			if(hash[modKey].get(i).getKey().equals(key)) {
				temp = (ValueType)hash[modKey].get(i).getValue();
				hash[modKey].remove(i);	
				loadFactor--;
				return temp;
			}
		}
		// key doesn't exist
		return null;
			
	}
	@Override
	public void clear() {
		for(int i = 0; i < capacity; i++) {
			for(int j = 0; j < hash[i].size(); j++) {
				hash[i].remove(j);
				loadFactor--;
			}
		}
	}

}
