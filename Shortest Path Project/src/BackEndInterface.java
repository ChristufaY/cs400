// --== CS400 File Header Information ==--
// Name: Akshay Joshi
// Email: akjoshi2@wisc.edu
// Team: BG Red
// Role: Front End Developer
// TA: Brianna Cochran
// Lecturer: Florian Heimerl
// Notes to Grader: N/A

import java.util.List;
/**
 * Interface which the BackEnd class implements
 * @author Akshay Joshi
 *
 */
public interface BackEndInterface {
  boolean addNewFlight(String origin, String destination, int cost, int distance);
  boolean removeFlight(String origin, String destination);
  List<String> shortestPath(String origin, String destination);
  List<String> cheapestPath(String origin, String destination);
  int getShortestPathDistance(String origin, String destination);
  int getCheapestPathCost(String origin, String destination);
  List<List<String>> getAllPaths(String origin, String destination);
  
}