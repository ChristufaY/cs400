// --== CS400 File Header Information ==--
// Name: Karsey Renfert
// Email: krenfert@wisc.edu
// Team: BG red
// Role: Data Wrangler
// TA: Bri Cochran
// Lecturer: Florian
// Notes to Grader: n/a

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.zip.DataFormatException;

public interface CityReaderInterface {
	public List<CityInterface> getListOfCities(Reader fileReader)
			throws IOException, FileNotFoundException, DataFormatException;
}