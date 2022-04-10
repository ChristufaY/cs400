// --== CS400 File Header Information ==--
// Name: Karsey Renfert
// Email: krenfert@wisc.edu
// Team: BG red
// Role: Data Wrangler
// TA: Bri Cochran
// Lecturer: Florian
// Notes to Grader: n/a

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.DataFormatException;

public class CityReader implements CityReaderInterface{

	@Override
	public List<CityInterface> getListOfCities(Reader fileReader)
			throws IOException, FileNotFoundException, DataFormatException {
		
		BufferedReader br = new BufferedReader(fileReader);
		HashMap<String, CityInterface> changeableMap = new HashMap<String, CityInterface>();
		
		String line = br.readLine();
		String[] parts;
		
		// SRC,DST,C$T,DIST,DESC
		//  0   1   2   3    4
		while(line != null) {
			parts = line.split(",", 5);
			if(parts.length != 5)
				throw new DataFormatException("Invalid file format (csv contains an incorrect amount of values)");
			
			City c = new City(parts[0], parts[4]);
			
			try {
				if(changeableMap.containsKey(parts[0])) {
					// add to the existing object
					changeableMap.get(parts[0]).addPath(parts[1], Integer.parseInt(parts[2]));
					changeableMap.get(parts[0]).addPathDistance(parts[1], Integer.parseInt(parts[3]));
				} else {
					c.addPath(parts[1], Integer.parseInt(parts[2]));
					c.addPathDistance(parts[1], Integer.parseInt(parts[3]));
					changeableMap.put(parts[0], c);
				}
			} catch (NumberFormatException nfe) {
				throw new DataFormatException("Invalid file format (cost value is not an int)");
			}
			
			line = br.readLine();
		}
		
		ArrayList<CityInterface> output = new ArrayList<CityInterface>();
		
		changeableMap.forEach((k, v) -> {
            output.add(v);
        });
		
		return output;
	}

}