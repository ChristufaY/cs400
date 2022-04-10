// --== CS400 File Header Information ==--
// Name: Christopher Yang
// Email: cyang397@wisc.edu
// Team: BG Red
// Role: Data Wrangler
// TA: Brianna Cochran
// Lecturer: Gary Dahl
// Notes to Grader: None
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.zip.DataFormatException;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collections;
// this class implements the MovieDataReaderInterface of the Movie Mapper Project
public class MovieDataReader implements MovieDataReaderInterface{

	@Override
	public List<MovieInterface> readDataSet(Reader inputFileReader)
			throws FileNotFoundException, IOException, DataFormatException {
		
		BufferedReader br = new BufferedReader(inputFileReader);
		List<MovieInterface> listOfMovies = new ArrayList<MovieInterface>();
		String[] movieData;
		Movie movie;
		String data;
		int headerLength;
		try {
			// reads the header line which contains the name of columns
			data = br.readLine();
			if(data == null)
				return null;
			// regular expression to split the string at commas that are not inside quotes
			movieData = data.split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");
//			for(int i = 0; i < movieData.length; i++)
//				System.out.println(movieData[i]);
//			System.out.println(data);
			// will use headerlength as refernce to check for dataformatexceptions
			headerLength = movieData.length;
			data = br.readLine();
			// checks for null lines
			while (data != null) {
				// separate the movie attribute at the commas and assign to according
				// array index
				//movieData = data.split("(?:(?<=\")([^\"]*)(?=\"))|(?<=,|^)([^,]*)(?=,|$)");
				// collections.sort
				movieData = data.split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");
				if(movieData.length != headerLength)
					throw new DataFormatException("Data is not formatted correctly.");
				movie = new Movie(movieData);
//				for(int i = 0; i < listOfMovies.size(); i++) {
//					//descending
//					if(listOfMovies.get(i).compareTo(movie) == -1 || listOfMovies.get(i).compareTo(movie) == 0) {
//						listOfMovies.add(0, movie);
//						break;
//					}
//				}
				// add the movie
				listOfMovies.add(movie);
				// next line
				data = br.readLine();
			}
		} catch (FileNotFoundException e) {
			e.getMessage();
			e.printStackTrace();
			System.out.println(e);
		} catch (IOException e2) {
			e2.getMessage();
			e2.printStackTrace();
			System.out.println(e2);
		} catch (DataFormatException e3) {
			e3.getMessage();
			e3.printStackTrace();
			System.out.println(e3);
		}
		br.close();
		Collections.sort(listOfMovies);
		return listOfMovies;
	}
}
