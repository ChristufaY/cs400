// --== CS400 File Header Information ==--
// Name: Karsey Renfert
// Email: krenfert@wisc.edu
// Team: BG red
// Role: Data Wrangler
// TA: Bri Cochran
// Lecturer: Florian
// Notes to Grader: n/a

import java.util.HashMap;

public interface CityInterface extends Comparable<CityInterface> {
	boolean addPath(String dest, int cost);

	boolean addPathDistance(String dest, int cost);
	
	public String getCityName();

	public String getCityDesc();

	public HashMap<String, Integer> getListofPathsWithWeights();

	public HashMap<String, Integer> getListofPathsWithWeightsDistance();

	public String toString();

	public int compareTo(CityInterface otherCity);
}
