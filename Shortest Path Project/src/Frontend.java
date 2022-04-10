// --== CS400 File Header Information ==--
// Name: Christopher Yang
// Email: cyang397@wisc.edu
// Team: BG Red
// Role: Frontend Developer
// TA: Brianna Cochran
// Lecturer: Gary Dahl
// Notes to Grader: 

import java.util.NoSuchElementException;
import java.util.zip.DataFormatException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileReader;
import java.util.List;

/**
 * This class instantiates the back end methods and creates a user interface where the user can search an airline for
 * certain or all flights when the user gives their current location and a desired one.
 * @author Christopher Yang
 *
 */
public class Frontend {
	private static BackEnd b;
	private static Frontend f;
	private Scanner sc;
	private String mode;
	private boolean running = false;
	private String input;

	public static void main(String[] args) {
		f = new Frontend();
		f.run();

	}
	public void run() {
		try {
			Frontend.b = new BackEnd(new FileReader("dummy.txt"));
			running = true;
			mode = "home";
			sc = new Scanner(System.in);
			System.out.println("Welcome to Airline Flight Search!");
			while(running) {
				printOptions();
				input = sc.nextLine().toLowerCase();
				if(input.equals("q")) {
					System.out.println("Thank you for using Airline Flight Search.");
					System.out.println("Goodbye.");
					//System.exit(0);
					break;
				} else if(input.equals("s")) {
					shortestPathMode();
				} else if(input.equals("c")) {
					cheapestPathMode();
				} else if(input.equals("r")) {
					listMode();
				} else if(input.equals("a")) {
					addMode();
				} else if(input.equals("d")) {
					removeMode();
				} else {
					System.out.println("Invalid input. Please input a valid key.");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DataFormatException e) {
			e.printStackTrace();
		}
		
	}

	public void printOptions() {
		System.out.println("------------------------------------------");
		System.out.println("Press 's' to enter shortest path mode.");
		System.out.println("Press 'c' to enter cheapest path mode.");
		System.out.println("Press 'r' to enter list all routes mode.");
		System.out.println("Press 'a' to enter add flight mode.");
		System.out.println("Press 'd' to enter remove/delete flight mode.");
		System.out.println("Press 'q' to quit.");
		System.out.println("------------------------------------------");
	}

	/**
	 * This method prints the shortest path between two cities
	 */
	public void shortestPathMode() {
		System.out.println("Welcome to shortest path mode!");
		String origin;
		String destination;
		System.out.println("Enter your current city: ");
		origin = sc.nextLine();
		System.out.println("Enter your desired destination: ");
		destination = sc.nextLine();
		if(b.shortestPath(origin, destination) == null)
			System.out.println("A path between " + origin + " and " + destination
					+ " does not exist.");
		else
			System.out.println("The shortest path between " + origin + " and " + destination
					+ " is " + b.shortestPath(origin, destination));
	}
	/**
	 * This method returns the cheapest path between two cities 
	 */
	public void cheapestPathMode() {
		System.out.println("Welcome to cheapest path mode!");
		String origin;
		String destination;
		System.out.println("Enter your current/origin city: ");
		origin = sc.nextLine();
		System.out.println("Enter your desired destination: ");
		destination = sc.nextLine();
		System.out.println("The cheapest path between " + origin + " and " + destination
				+ " costs " + b.getCheapestPathCost(origin, destination) + " dollars!");
	}
	/**
	 * This methods prints the list of all paths between two cities and also
	 * prints the cost and the distance per path. 
	 */
	public void listMode() {
		System.out.println("Welcome to list all mode!");
		String origin;
		String destination;
		String tempO;
		String tempD;
		//int cost = 0;
		//int distance = 0;
		System.out.println("Enter your current/origin city: ");
		origin = sc.nextLine();
		System.out.println("Enter your desired destination: ");
		destination = sc.nextLine();
		List<List<String>> paths = b.getAllPaths(origin, destination);
		//System.out.println(paths.size());
		if(paths == null)
			System.out.println("There are no paths from " + origin + " to " + destination);
		else {
			for(int i = 0; i < paths.size(); i++) {
				tempO = "";
				tempD = "";
				//cost = 0;
				//distance = 0;
				for(int j = 0; j < paths.get(i).size() - 1; j++) {
					tempO = paths.get(i).get(j);
					tempD = paths.get(i).get(j+1);
					//cost += b.getCheapestPathCost(tempO, tempD);
					//distance += b.getShortestPathDistance(tempO, tempD);
				}
				System.out.println("Path " + (i+1) + ") is " + paths.get(i).toString());
			}
		}
	}
	/**
	 * This methods adds a flight between two cities if it doesn't already exist. Prints
	 * whether or not it was a successful addition
	 */
	public void addMode() {
		System.out.println("Welcome to add flight mode!");
		String origin;
		String destination;
		int cost;
		int distance;
		System.out.println("Enter origin city: ");
		origin = sc.nextLine();
		System.out.println("Enter destination city: ");
		destination = sc.nextLine();
		System.out.println("Enter the cost of the flight: ");
		cost = getInt();
		System.out.println("Enter the distance between the two cities: ");
		distance = getInt();
		b.addCity(origin);
		b.addCity(destination);
		boolean exists = b.addNewFlight(origin, destination, cost, distance);
		if(exists) 
			System.out.println("Flight path from " + origin + " to " + destination 
					+ " has been added.");
		else 
			System.out.println("Flight path from " + origin + " to " + destination
					+ " already exists.");


	}
	/**
	 * This method helps the "getInt()" method in that it repeatedly prompts the user to input
	 * a valid number if given an invalid one.
	 * @param input String input from user containing the int
	 * @return int if it is a valid number
	 */
	public int getIntHelper(String input) {
		int temp;
		try {
			temp = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			System.out.println("Enter a valid number.");
			return -1;
		}
		return temp;
	}
	/**
	 * This method makes sure that the user input is non-negative.
	 * @return int if it is a valid positive number.
	 */
	public int getInt() {
		int temp;
		do { 
			System.out.println("Please enter a valid non-negative number."); 
			temp = getIntHelper(sc.nextLine());
		} while(temp < 0);
		return temp;
	}
	/**
	 * This method removes a flight between two cities if the flight exists. Prints whether
	 * or not the removal was successful
	 */
	public void removeMode() {
		System.out.println("Welcome to remove flight mode!");
		String origin;
		String destination;
		System.out.println("Enter origin city: ");
		origin = sc.nextLine();
		System.out.println("Enter destination city: ");
		destination = sc.nextLine();
		boolean noPath = b.removeFlight(origin, destination);
		if(!noPath)
			System.out.println("Flight path from " + origin + " to " + destination
					+ " does not exist.");
		else 
			System.out.println("Flight path from " + origin + " to " + destination 
					+ " has been removed.");
	}

}
