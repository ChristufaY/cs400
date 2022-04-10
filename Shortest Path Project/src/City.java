// --== CS400 File Header Information ==--
// Name: Karsey Renfert
// Email: krenfert@wisc.edu
// Team: BG red
// Role: Data Wrangler
// TA: Bri Cochran
// Lecturer: Florian
// Notes to Grader: n/a

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class City implements CityInterface{

	String name;
	String description;
	HashMap<String, Integer> paths;
	HashMap<String, Integer> pathsDistance;
	
	public City() {
		name = "MSN";
		description = "default";
		paths = new HashMap<String, Integer>();
		pathsDistance = new HashMap<String, Integer>();
	}
	
	public City(String name, String description) {
		this.name = name;
		this.description = description;
		paths = new HashMap<String, Integer>();
		pathsDistance = new HashMap<String, Integer>();
	}

	@Override
	public boolean addPath(String dest, int cost) {
		if(paths.containsKey(dest))
			return false;
		
		paths.put(dest,  cost);
		return true;
	}

	@Override
	public boolean addPathDistance(String dest, int cost) {
		if(pathsDistance.containsKey(dest))
			return false;
		
		pathsDistance.put(dest,  cost);
		return true;
	}

	@Override
	public String getCityName() {
		return name;
	}

	@Override
	public String getCityDesc() {
		return description;
	}

	@Override
	public HashMap<String, Integer> getListofPathsWithWeights() {
		return paths;
	}

	@Override
	public HashMap<String, Integer> getListofPathsWithWeightsDistance() {
		return pathsDistance;
	}
	
	@Override
	public String toString() {
		AtomicReference<String> rtn = new AtomicReference<>();
		rtn.set(name + " [" + description + "]\n");
		
		paths.forEach((k, v) -> {
			pathsDistance.forEach((kk, vv) -> {
				if(k.equals(kk))
					rtn.set(rtn.get() + "    To " + k + ": " + vv + "mi ($" + v + ")\n");
			});
		});
		
		return rtn.get();
	}
	
	@Override
	public int compareTo(CityInterface otherCity) {
		return otherCity.getCityName().equals(name) ? 0 : 1;
	}

}