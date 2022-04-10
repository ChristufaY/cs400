import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SkipListTest {


    /**
     * This test inserts three values for five integer keys. It passes if put returns
     * true for the first insert, and false for all others.
     */
    @Test
    public void testPut() {
        SkipList<Integer,Integer> skipList = new SkipList<Integer,Integer>();
        for (int i = 0; i < 5; i++) {
            assertEquals(true, skipList.put(i,i));
            assertEquals(false, skipList.put(i,i+10));
            assertEquals(false, skipList.put(i,i+20));
        } 
    }

    /**
     * This test inserts two key, value pairs into the skip list and checks if
     * getSmallestKey returns the smaller of the two.
     */
    @Test
    public void testSmallestKey() {
        SkipList<Integer,String> skipList = new SkipList<Integer,String>();
        skipList.put(10, "ten");
        skipList.put(20, "twenty");
        //skipList.put(5, "five");
        assertEquals(10, skipList.getSmallestKey());
    }

    /**
     * This test inserts two key, value pairs into the skip list and checks if
     * getLargestKey returns the larger of the two.
     */
    @Test
    public void testLargestKey() {
        SkipList<Integer,String> skipList = new SkipList<Integer,String>();
        skipList.put(-10, "minus_ten");
        skipList.put(-20, "minus_twenty");
        //skipList.put(-30, "minus_thirty");
        //System.out.println(skipList);
        assertEquals(-10, skipList.getLargestKey());
    }

    /**
     * This test inserts five integer keys with a varying number of string values.
     * It then checks if containsKey returns true for the existing keys.
     */
    @Test
    public void testContainsKey() {
        SkipList<Integer,String> skipList = new SkipList<Integer,String>();
        for (int i = 1; i < 6; i++) {
            int valueInt = 100*i;
            for (int j = 0; j < i; j++) {
                skipList.put(i,String.valueOf(valueInt*j));
            }
        }
        for (int i = 1; i < 6; i++) {
            assertEquals(true, skipList.containsKey(i));
        }
    }

    /**
     * This test inserts 10 integer key, value pairs (half with duplicate keys) and
     * tests size for every insertion.
     */
    @Test
    public void testSize() {
        SkipList<Integer,Integer> skipList = new SkipList<Integer,Integer>();
        for (int i = 0; i < 10; i++) {
            assertEquals(i, skipList.size());
            skipList.put(i/2, i);
        }
    }

    /**
     * This test inserts 10 keys with 5 values each into the map and then checks
     * whether getFirst returns one of the 5 values.
     */
    @Test
    public void testGetFirst() {
        SkipList<Integer,String> skipList = new SkipList<Integer,String>(); 
        for (int i = 0; i < 10; i++) {
            for (int j = 1; j < 6; j++) {
                skipList.put(i, String.valueOf(j));
            }
        }
        Set<String> valueSet = new HashSet<String>();
        for (int j = 1; j < 6; j++) {
            valueSet.add(String.valueOf(j));
        }
        for (int i = 0; i < 10; i++) {
            assertTrue(valueSet.contains(skipList.getFirst(i)));
        }
    }

    /**
     * This test inserts 10 keys with 5 values each into the map and then checks
     * whether getAll returns the expeccted set of 5 values.
     */
    @Test
    public void testGetAll() {
        SkipList<Integer,String> skipList = new SkipList<Integer,String>(); 
        for (int i = 0; i < 10; i++) {
            for (int j = 1; j < 6; j++) {
                skipList.put(i, String.valueOf(j));
            }
        }
        Set<String> expectedSet = new HashSet<String>();
        for (int j = 1; j < 6; j++) {
            expectedSet.add(String.valueOf(j));
        }
        for (int i = 0; i < 10; i++) {
            Iterator<String> iter = skipList.getAll(i);
            Set<String> returnedSet = new HashSet<String>();
            while (iter.hasNext()) returnedSet.add(iter.next());
            assertEquals(expectedSet, returnedSet);
        }
    }

    /**
     * This test inserts 10 keys (0 to 9) with 1 to 10 values each into the map and then checks
     * whether getRange(5, 7) returns the expected set of values. Since we are not expecting
     * value that have the same key to be ordered in any way, we compare the returned values
     * in two hashsets (effectively ignoring their order altogether).
     */
    @Test
    public void testGetRange() {
        SkipList<Integer,Integer> skipList = new SkipList<Integer,Integer>(); 
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j <= i; j++) {
                skipList.put(i, j+(100*i));
            }
        }
        Set<Integer> expectedSet = new HashSet<Integer>();
        for (int i = 5; i <= 7; i++) {
            for (int j = 0; j <= i; j++) {
                expectedSet.add(j+(100*i));
            }
        }
        Set<Integer> returnedSet = new HashSet<Integer>();
        Iterator<Integer> iter = skipList.getRange(5, 7);
        while (iter.hasNext()) {
            returnedSet.add(iter.next());
        }
        assertEquals(expectedSet, returnedSet);
    }


    /**
     * This test inserts 5 keys (5 to 10) with 2 values each into the map and then checks
     * whether getRange(2, 5) returns the expected set of values. Since we are not expecting
     * value that have the same key to be ordered in any way, we compare the returned values
     * in two hashsets (effectively ignoring their order altogether).
     */
    @Test
    public void testGetRangeStartBeforeSmallestKey() {
        SkipList<Integer,Integer> skipList = new SkipList<Integer,Integer>(); 
        for (int i = 5; i <= 10; i++) {
            skipList.put(i, (100*i)+1);
            skipList.put(i, (100*i)+2);
        }
        Set<Integer> expectedSet = new HashSet<Integer>();
        expectedSet.add(501);
        expectedSet.add(502);

        Set<Integer> returnedSet = new HashSet<Integer>();
        Iterator<Integer> iter = skipList.getRange(2, 5);
        while (iter.hasNext()) {
            returnedSet.add(iter.next());
        }
        System.out.println(returnedSet);
        assertEquals(expectedSet, returnedSet);
    }

    /**
     * This test inserts 5 keys (5 to 10) with 2 values each into the map and then checks
     * whether getRange(9, 15) returns the expected set of values. Since we are not expecting
     * value that have the same key to be ordered in any way, we compare the returned values
     * in two hashsets (effectively ignoring their order altogether).
     */
    @Test
    public void testGetRangeStartAfterHighestKey() {
        SkipList<Integer,Integer> skipList = new SkipList<Integer,Integer>(); 
        for (int i = 5; i <= 10; i++) {
            skipList.put(i, (100*i)+1);
            skipList.put(i, (100*i)+2);
        }
        Set<Integer> expectedSet = new HashSet<Integer>();
        expectedSet.add(901);
        expectedSet.add(902);
        expectedSet.add(1001);
        expectedSet.add(1002);

        Set<Integer> returnedSet = new HashSet<Integer>();
        Iterator<Integer> iter = skipList.getRange(9, 15);
        while (iter.hasNext()) {
            returnedSet.add(iter.next());
        }
        assertEquals(expectedSet, returnedSet);
    }

    /**
     * This test inserts 5 keys (5 to 10) with 2 values each into the map and then checks
     * whether getRange(-4, 22) returns all of the values. Since we are not expecting
     * value that have the same key to be ordered in any way, we compare the returned values
     * in two hashsets (effectively ignoring their order altogether).
     */
    @Test
    public void testGetRangeStartAfterBeforeSmallestAndAfterHighestKey() {
        Set<Integer> expectedSet = new HashSet<Integer>();
        SkipList<Integer,Integer> skipList = new SkipList<Integer,Integer>(); 
        for (int i = 5; i <= 10; i++) {
            skipList.put(i, (100*i)+1);
            skipList.put(i, (100*i)+2);
            expectedSet.add((100*i)+1);
            expectedSet.add((100*i)+2);
        }

        Set<Integer> returnedSet = new HashSet<Integer>();
        Iterator<Integer> iter = skipList.getRange(-4, 22);
        while (iter.hasNext()) {
            returnedSet.add(iter.next());
        }
        assertEquals(expectedSet, returnedSet);
    }
    
}
