// --== CS400 File Header Information ==--
// Name: Akshay Joshi
// Email: akjoshi2@wisc.edu
// Team: BG Red
// Role: Front End Developer
// TA: Brianna Cochran
// Lecturer: Florian Heimerl
// Notes to Grader: N/A

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.zip.DataFormatException;

/**
 * BackEnd class that makes use of graph data structure to use shortest path algorithm
 * @author Akshay Joshi
 *
 */
public class BackEnd implements BackEndInterface {
  CS400Graph<String> distanceGraph = new CS400Graph<String>();
  CS400Graph<String> costGraph = new CS400Graph<String>();
  /**
   * Constructor, reads the data file and adds vertices and edges to the graph that correspond with the data
   * @param fileReader The filereader
   * @throws FileNotFoundException If file is not found
   * @throws IOException If there is an IOException
   * @throws DataFormatException If there is a DataFormatException
   */
  public BackEnd(Reader fileReader) throws FileNotFoundException, IOException, DataFormatException
  {
    CityReader cr = new CityReader();
    List<CityInterface> cities = cr.getListOfCities(fileReader);
    for (CityInterface c : cities)
    {
      addCity(c.getCityName());
    }
    for (CityInterface c: cities)                               
    {
       HashMap<String, Integer> pawc = c.getListofPathsWithWeights();
       HashMap<String, Integer> pawd = c.getListofPathsWithWeightsDistance();
       for (CityInterface d: cities)        
       {
         System.out.println(pawc.get(c.getCityName()));
         if (pawc.get(d.getCityName()) != null && pawd.get(d.getCityName()) != null)            //if there is a path from c to d, add it to the graph
         {
           addNewFlight(c.getCityName(), d.getCityName(), pawc.get(d.getCityName()), pawd.get(d.getCityName()));
         }
       }
    }
  }
  /**
   * Adds a flight to the cost graph and to the distance graph, FrontEnd will not allow for negative costs and distances, 
   * will catch bad user input in the frontend
   * Additionally, if a flight already exists with the same cost or same distance, will not be added
   * If the cost is same but distance is different or vice versa, the changed cost/distance will be changed
   * @param origin The origin point of the edge
   * @param destination The target point of the edge
   * @param cost The cost of the flight
   * @param distance The distance of the flight
   * @return True if successfully added or if one value changed, false otherwise
   */
  @Override
  public boolean addNewFlight(String origin, String destination, int cost, int distance) {
    if (cost < 0 || distance < 0) //checks if cost and distance are valid
    {
      return false;
    }
    try
    {
    boolean b1 = costGraph.insertEdge(origin, destination, cost);
    boolean b2 = distanceGraph.insertEdge(origin, destination, distance);
    return (b1 || b2);
    }
    catch (NullPointerException n) //checks if origin and destination are on graph, if not nullpointer will be caught
    {
      return false;
    }
  }
  /**
   * Removes a flight from both graphs
   * @param origin The city of origin
   * @param destination The city of destination
   * @return True if successfully removed, false otherwise
   */
  @Override
  public boolean removeFlight(String origin, String destination)
  {
    try
    {
    costGraph.removeEdge(origin, destination);
    return distanceGraph.removeEdge(origin, destination);
    }
    catch (IllegalArgumentException i) //if the origin and destination are not part of graph, return false
    {
      return false;
    }
  }
  /**
   * Adds city to the cost graph and to the distance graph
   * @param city The city name, data of the vertex
   * @return True if vertex successfully added, false otherwise
   */
  public boolean addCity(String city)
  {
    costGraph.insertVertex(city);
    return distanceGraph.insertVertex(city);
  }
  /**
   * Removes city from both graphs, removes all edges involving the removed city
   * @param city The city name, data of the vertex
   * @return True if vertex successfully removed, false otherwise
   */
  public boolean removeCity(String city) {
    try
    {
    List<String> vertexListCostGraph = costGraph.getAllVertexData();
    for (String s: vertexListCostGraph)             //when removing a city, you have to first remove all edges connected to that city
    {
      if (costGraph.containsEdge(s, city))
      {
        removeFlight(s, city);
      }
      if (costGraph.containsEdge(city, s))
      {
        removeFlight(s, city);
      }
    }
    costGraph.removeVertex(city);
    return distanceGraph.removeVertex(city);
    }
    catch (NullPointerException n)   //if there is a nullpointerException, return false, not successfully added
    {
      return false;
    }
  }
  /**
   * Finds the shortest path distance wise between two cities
   * @param origin The city of origin
   * @param destination The destination
   * @return A list of all the vertices (cities) traveled to, with the first one being the origin and the last one being the destination
   */
  @Override
  public List<String> shortestPath(String origin, String destination) {
    try
    {
    return distanceGraph.shortestPath(origin, destination);
    }
    catch (NoSuchElementException e)  //If there is no path between two vertices, return null so Frontend can correctly handle case
    {
      return null;
    }
  }
  /**
   * Finds the cheapest path between two cities
   * @param origin The starting city
   * @param destination The ending city
   * @return A list of all vertices (cities) traveled to
   */
  @Override
  public List<String> cheapestPath(String origin, String destination) {
    try
    {
    return costGraph.shortestPath(origin, destination);
    }
    catch (NoSuchElementException e)   //if there is no path between two vertices, return null so FrontEnd can correctly handle case
    {
      return null;
    }
  }
  /**
   * Finds the distance of the shortest path between two cities
   * @param origin The starting city
   * @param destination The ending city
   * @return The integer distance of the shortest path between the two cities
   */
  @Override
  public int getShortestPathDistance(String origin, String destination) {
    return distanceGraph.getPathCost(origin, destination);
  }
  /**
   * Finds the cost of the cheapest path between two cities
   * @param origin The starting city
   * @param destination The ending city
   * @return The integer cost of the cheapest path between the two cities
   */
  @Override
  public int getCheapestPathCost(String origin, String destination) {
    return costGraph.getPathCost(origin, destination);
  }
  /**
   * Gets all paths between two cities
   * @param origin The city of origin
   * @param destination The city of destination
   * @return A list of path sequence lists
   * 
   */
  @Override
  public List<List<String>> getAllPaths(String origin, String destination) {
    return distanceGraph.getAllPaths(origin, destination); //distanceGraph and costGraph are same for all purposes except edge weights, so either works here
  }
  

}