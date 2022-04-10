// --== CS400 File Header Information ==--
// Name: Christopher Yang
// Email: cyang397@wisc.edu
// Team: BG
// TA: Brianna Cochran
// Lecturer: Gary Dahl
// Notes to Grader: 
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the implementation of CS400Graph for the individual component of
 * Project Three: the implementation of Dijsktra's Shortest Path algorithm.
 */
public class GraphTest {

    private CS400Graph<String> graph;
    
    /**
     * Instantiate graph from last week's shortest path activity.
     */
    @BeforeEach
    public void createGraph() {
        graph = new CS400Graph<>();
        // insert vertices A-E
        graph.insertVertex("A");
        graph.insertVertex("B");
        graph.insertVertex("C");
        graph.insertVertex("D");
        graph.insertVertex("E");
        // insert edges from Week 09. Dijkstra's Activity
        graph.insertEdge("A","B",2);
        graph.insertEdge("A","D",4);
        graph.insertEdge("A","E",1);
        graph.insertEdge("B","C",5);
        graph.insertEdge("C","A",3);
        graph.insertEdge("D","B",3);
        graph.insertEdge("D","C",7);
        graph.insertEdge("D","E",1);
        graph.insertEdge("E","C",8);
    }

    /**
     * Checks the distance/total weight cost from the vertex labeled C to E
     * (should be 4), and from the vertex labeled A to C (should be 7).
     */
    @Test
    public void providedTestToCheckPathCosts() {
        assertTrue(graph.getPathCost("C", "E") == 4);    
        assertTrue(graph.getPathCost("A", "C") == 7);
    }

    /**
     * Checks the ordered sequence of data within vertices from the vertex 
     * labeled C to E, and from the vertex labeled A to C.
     */
    @Test
    public void providedTestToCheckPathContents() {
        assertTrue(graph.shortestPath("C", "E").toString().equals(
            "[C, A, E]"
        ));
        assertTrue(graph.shortestPath("A", "C").toString().equals(
            "[A, B, C]"
        ));
    }
    
    /** 
     * Checks the farthest distance of a pair of vertices labeled A to E. 
     */
    @Test
    public void testToCheckPathCostFromAToC() {
    	assertTrue(graph.getPathCost("A", "C") == 7);
    }
    
    /**
     * Checks the sequence of values along the path of from Vertex A to farthest Vertex C
     */
    @Test
    public void testToCheckPathSequenceFromAToC() {
    	//String actual = graph.shortestPath("A", "C").toString();
    	//System.out.println(actual);
    	assertTrue(graph.shortestPath("A", "C").toString().equals("[A, B, C]"));
    }
    
    /**
     * Checks the shortest distance of Vertex A and E
     */
    @Test 
    public void testToCheckPathCostFromAToE() {
    	assertTrue(graph.getPathCost("A", "E") == 1);
    }
    
    /**
     * Checks the path cost from Vertex A to Vertex B
     */
    @Test 
    public void testToCheckPathCostFromDToB() {
    	assertTrue(graph.getPathCost("D", "B") == 3);
    }
}