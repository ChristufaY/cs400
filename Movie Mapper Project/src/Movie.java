// --== CS400 File Header Information ==--
// Name: Christopher Yang
// Email: cyang397@wisc.edu
// Team: BG Red
// Role: Data Wrangler
// TA: Brianna Cochran
// Lecturer: Gary Dahl
// Notes to Grader: None
import java.util.List;
import java.util.ArrayList;
// This class implements the MovieInterface of the Movie Mapper Project
public class Movie implements MovieInterface {
	String title;
	int year;
	List<String> genres;
	String director;
	String description;
	Float avgVote;

	public Movie(String[] movieData) {
		this.title = movieData[0];
		this.year = Integer.parseInt(movieData[2]);
		// because genres are in a String[] we want to change that to List<String>
		List<String> tempGenres = new ArrayList<String>();
		String[] temp = movieData[3].split(",");
		for(int i = 0; i < temp.length; i++) {
			tempGenres.add(temp[i]);
		}
		this.genres = tempGenres;
		this.director = movieData[7];
		this.description = movieData[11];
		this.avgVote = Float.parseFloat(movieData[12]);
	}
	// returns the title of this.movie
	public String getTitle() {
		return title;
	}
	// returns the year the movie was made
	public Integer getYear() {
		return year;
	}
	// returns the genres of the movie
	public List<String> getGenres() {
		return genres;
	}
	// returns the director of the movie
	public String getDirector() {
		return director;
	}
	// returns the description of the movie
	public String getDescription() {
		return description;
	}
	// returns the average vote/rating of the movie
	public Float getAvgVote() {
		return avgVote;
	}
	// compares the ratings of two movies and returns -1, 0, and 1 accordingly
	public int compareTo(MovieInterface otherMovie) {
		// 1 is a < b
		if(this.avgVote < otherMovie.getAvgVote())
			return 1;
		// -1 is a > b
		if(this.avgVote > otherMovie.getAvgVote())
			return -1;
		// 0 is a = b
				return 0;
	}
}
