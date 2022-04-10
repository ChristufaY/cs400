import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.LinkedList;

/**
 * The first four test methods below are designed to help you find and fix bugs
 * within the provided Tree234 implementation.  Fixing these bugs should help
 * each of these tests change from failing to passing tests.  The fifth test
 * is there as an additional test that should pass after fixing the previous four
 * bugs, and to help demonstrate that those fixes generalize to other situations.
 */
public class Tree234Tests {

    /**
     * This test should pass after a bug within the Tree234 class is fixed.
     */
    @Test
    public void testInsertIntoEmptyTree() {
        Tree234<String,String> tree = new Tree234<>();
        tree.insert("A","1");
        assertEquals("[{A:1}]",tree.toString(),"Failed to find single value inserted into an empty Tree234.");
    }
    
    /**
     * This test should pass after a bug within the Tree234.LeafNode class is fixed.
     */
    @Test
    public void testFullRootNode() {
        Tree234<Integer,Integer> tree = new Tree234<>();
        tree.insert(0,1);
        tree.insert(2,3);
        tree.insert(4,5);
        assertEquals("[{0:1, 2:3, 4:5}]",tree.toString(),"Failed to find all three values inserted into an empty Tree234.");
    }

    /**
     * This test should pass after a bug within the Tree234.LeafNode class is fixed.
     */
    @Test
    public void testGetFromLeafNode() {
        Tree234<Boolean,String> tree = new Tree234<>();
        tree.insert(true, "one");
        tree.insert(false, "two");
        tree.insert(true, "three");
        LinkedList<String> list = tree.get(true);
        assertEquals(2,list.size(),"The get method failed to return expected number of values when called on a leaf node.");
    }


    /**
     * This test should pass after a bug within the Tree234.InteriorNode class is fixed.
     */
    @Test
    public void testSplitInteriorNode() {
        Tree234<Double,String> tree = new Tree234<>();
        tree.insert(1.1,"");
        tree.insert(2.2,"");
        tree.insert(3.3,"");
        tree.insert(4.4,"");
        tree.insert(5.5,"");
        tree.insert(6.6,"");
        tree.insert(7.7,"");
        tree.insert(8.8,""); 
        tree.insert(9.9,""); 

        assertEquals("[{4.4:}, {2.2:}, {6.6:}, {1.1:}, {3.3:}, {5.5:}, {7.7:, 8.8:, 9.9:}]",tree.toString(),"Failed to correctly split an interior node.");
    }

    /**
     * This test should pass after correctly fixing all five of the above tests.
     */
    @Test
    public void testLargeTree() {
        Integer[] array = new Integer[] { 6, 15, 7, 14, 8, 13, 9, 12, 10, 11, 5, 16, 4, 17, 3, 18, 2, 19, 1, 20 };
        Tree234<Integer,String> tree = new Tree234<>();
        for(Integer i : array) tree.insert(i,"");
        assertEquals("[{9:, 14:}, {3:, 5:, 7:}, {12:}, {16:, 18:}, {1:, 2:}, {4:}, {6:}, {8:}, {10:, 11:}, {13:}, {15:}, {17:}, {19:, 20:}]",tree.toString(),"Failed to correctly populate a size twenty 2-3-4 tree.");
    }
    
}
