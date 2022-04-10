import java.util.NoSuchElementException;

// --== CS400 File Header Information ==--
// Name: Christopher Yang
// Email: cyang397@wisc.edu
// Team: Red
// Group: BG
// TA: Brianna Cochran
// Lecturer: Gary Dahl
// Notes to Grader: None
public class TestHashTable {

	public static void main(String[] args) {
	    System.out.println("testPut(): " + testPut());
		System.out.println("testSize(): " + testSize());
		System.out.println("testRemove(): " + testRemove());
		System.out.println("testClear(): " + testClear());
		System.out.println("testContainsKey(): " + testContainsKey());

	}
	/**
	 * this test checks whether the put and get method of the HashTableMap class works.
	 * this test puts three keys and strings into a test HashTableMap and calls
	 * the get method to check if the strings are there.
	 * @return true if the test passed all cases, false otherwise
	 */
	public static boolean testPut() { 
		HashTableMap<Integer, String> test = new HashTableMap<Integer, String>();
		test.put(1, "Hello");
		test.put(11, "Bye");
		test.put(2, "Hi");
		test.put(null, "Good Morning.");
		test.put(11, "Bye");
		String hello = test.get(1);
		String bye = test.get(11);
		String hi = test.get(2);
		try {
			// null key check
			test.get(null);
			return false;
		} catch (NoSuchElementException e) {
			// System.out.println(e.getMessage());
			;
			
		}
		try {
			// element is not in the list
			test.get(4);
			return false;
		} catch (NoSuchElementException e) {
			// System.out.println(e.getMessage());
			;
		}
		if(!hello.equals("Hello"))
			return false;
		if(!bye.equals("Bye"))
			return false;
		if(!hi.equals("Hi"))
			return false;
		// System.out.println(test.get(1));
		// System.out.println(test.get(11));
		// System.out.println(test.get(2));
		return true;
	}
	/**
	 * this test method checks whether the size method works.
	 * the test puts in three strings and checks the size every time a string is inserted
	 * @return true if all cases pass, false otherwise
	 */
	public static boolean testSize() { 
		HashTableMap<Integer, String> test = new HashTableMap<Integer, String>(2);
		test.put(1, "Hello");
		if(test.size() != 1)
			return false;
		test.put(11, "Bye");
		if(test.size() != 2)
			return false;
		test.put(2, "Hi");
		if(test.size() != 3)
			return false;
		// null check
		test.put(null, "Good Morning.");
		if(test.size() != 3)
			return false;
		// duplicate key
		test.put(11, "Bye");
		if(test.size() != 3)
			return false;
		test.remove(1);
		if(test.size() != 2)
			return false;
		return true;
	}
	/**
	 * this test method checks whether the clear method works.
	 * this test inserts three strings into the test HashTableMap and then calls the clear method
	 * @return true if clear removes all elements and size becomes zero, false otherwise
	 */
	public static boolean testClear() { 
		HashTableMap<Integer, String> test = new HashTableMap<Integer, String>();
		test.put(1, "Hello");
		test.put(11, "Bye");
		test.put(2, "Hi");
		test.remove(1);
		test.clear();
		//System.out.println(test.size());
		if(test.size() != 0)
			return false;
		return true;
	}
	/**
	 * this test method checks whether the containsKey methods works.
	 * this method will put in three strings and call for a valid and invalid key
	 * @return true if all cases pass, false otherwise
	 */
	public static boolean testContainsKey() { 
		HashTableMap<Integer, String> test = new HashTableMap<Integer, String>();
		test.put(1, "Hello");
		test.put(11, "Bye");
		test.put(2, "Hi");
		if(!test.containsKey(1))
			return false;
		// null check
		if(test.containsKey(null))
			return false;
		// not found
		if(test.containsKey(5))
			return false;
		return true;
	}
	/**
	 * this test method checks whether the remove method works.
	 * this test inserts three and then removes three values. It also checks
	 * removing null keys and null strings
	 * @return true if all cases pass, false otherwise
	 */
	public static boolean testRemove() {
		HashTableMap<Integer, String> test = new HashTableMap<Integer, String>();
		test.put(1, "Hello");
		test.put(11, "Bye");
		test.put(2, "Hi");
		//remove null key
		String emptyNK = test.remove(null);
		//System.out.println(emptyNK);
		if(emptyNK != null)
			return false;
		// remove random null string
		String random = test.remove(4);
		if(random != null)
			return false;
		String hello = test.remove(1);
		//System.out.println(hello);
		if(test.size() != 2)
			return false;
		String bye = test.remove(11);
		//System.out.println(bye);
		if(test.size() != 1)
			return false;
		String hi = test.remove(2);
		if(test.size() != 0)
			return false;
		return true;
	}

}
