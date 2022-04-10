// --== CS400 File Header Information ==--
// Name: Christopher Yang
// Email: cyang397@wisc.edu
// Team: BG Red
// Role: Frontend Developer
// TA: Brianna Cochran
// Lecturer: Gary Dahl
// Notes to Grader: 
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.NoSuchElementException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
/*
 * This class contains a set of tests for the Front End of the Airline Flight Project
 */
public class FrontendTest {

	/**
	 *  This test method checks whether pressing 'a' works as intended
	 */
	@Test
	public void testAToAdd() {
		PrintStream standardOut = System.out;
		InputStream standardIn = System.in;
		try {
			String input = "a" + System.lineSeparator() + "LA" + System.lineSeparator()
			+ "Madison" + System.lineSeparator() + "5" + System.lineSeparator() + "10" 
			+ System.lineSeparator() + "s" + System.lineSeparator() + "LA" + System.lineSeparator() 
			+ "Madison" + System.lineSeparator() + "q";
			InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
			System.setIn(inputStreamSimulator);
			ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
			System.setOut(new PrintStream(outputStreamCaptor));
			Object frontend = new Frontend();
			((Frontend) frontend).run();
			System.setOut(standardOut);
			System.setIn(standardIn);
			String appOutput = outputStreamCaptor.toString();
			assertNotEquals(frontend, null);
			//System.out.println(appOutput);
			if(!appOutput.contains("added")) {
				fail();
			}
			if(!appOutput.contains("shortest")) {
				fail();
			}
		} catch (Exception e) {
			System.setOut(standardOut);
			System.setIn(standardIn);
			e.printStackTrace();
			fail();
		}
	} 
	/**
	 *  This test method checks whether pressing 'q' works as intended
	 */
	@Test
	public void testQToExit() {
		PrintStream standardOut = System.out;
		InputStream standardIn = System.in;
		try {
			String input = "q";
			InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
			System.setIn(inputStreamSimulator);
			ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
			System.setOut(new PrintStream(outputStreamCaptor));
			Object frontend = new Frontend();
			((Frontend) frontend).run();
			System.setOut(standardOut);
			System.setIn(standardIn);
			String appOutput = outputStreamCaptor.toString();
			assertNotEquals(frontend, null);
			//System.out.println(appOutput);
			if(!appOutput.contains("Goodbye.")) {
				fail();
			}
		} catch (Exception e) {
			System.setOut(standardOut);
			System.setIn(standardIn);
			e.printStackTrace();
			fail();
		}
	} 
	/**
	 *  This test method checks whether pressing 's' works as intended
	 */
	@Test
	public void testSToSearchShortest() {
		PrintStream standardOut = System.out;
		InputStream standardIn = System.in;
		try {
			String input = "a" + System.lineSeparator() + "LA" + System.lineSeparator()
			+ "Madison" + System.lineSeparator() + "5" + System.lineSeparator() + "10" 
			+ System.lineSeparator() + "s" + System.lineSeparator() + "LA" 
			+ System.lineSeparator() + "Madison" + System.lineSeparator() + "q";
			InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
			System.setIn(inputStreamSimulator);
			ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
			System.setOut(new PrintStream(outputStreamCaptor));
			Object frontend = new Frontend();
			((Frontend) frontend).run();
			System.setOut(standardOut);
			System.setIn(standardIn);
			String appOutput = outputStreamCaptor.toString();
			assertNotEquals(frontend, null);
			//System.out.println(appOutput);
			if(!appOutput.contains("shortest")) {
				fail();
			}
		} catch (Exception e) {
			System.setOut(standardOut);
			System.setIn(standardIn);
			e.printStackTrace();
			fail();
		}
	} 
	/**
	 *  This test method checks whether pressing 'c' works as intended
	 */
	@Test
	public void testCtoSearchCheapest() {
		PrintStream standardOut = System.out;
		InputStream standardIn = System.in;
		try {
			String input = "a" + System.lineSeparator() + "LA" + System.lineSeparator()
			+ "Madison" + System.lineSeparator() + "5" + System.lineSeparator() + "10" 
			+ System.lineSeparator() + "c" + System.lineSeparator() + "LA" + System.lineSeparator() 
			+ "Madison" + System.lineSeparator() + "q";
			InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
			System.setIn(inputStreamSimulator);
			ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
			System.setOut(new PrintStream(outputStreamCaptor));
			Object frontend = new Frontend();
			((Frontend) frontend).run();
			System.setOut(standardOut);
			System.setIn(standardIn);
			String appOutput = outputStreamCaptor.toString();
			assertNotEquals(frontend, null);
			//System.out.println(appOutput);
			if(!appOutput.contains("cheapest")) {
				fail();
			}
		} catch (Exception e)
		{
			System.setOut(standardOut);
			System.setIn(standardIn);
			e.printStackTrace();
			fail();
		}
	} 
	/**
	 *  This test method checks whether pressing 'r' works as intended
	 */
	@Test
	public void testRToList() {
		PrintStream standardOut = System.out;
		InputStream standardIn = System.in;
		try {
			String input = "a" + System.lineSeparator() +  "a" + System.lineSeparator() + "b"
					+ System.lineSeparator() + "1" + System.lineSeparator() + "1" + System.lineSeparator()
					+ "a" + System.lineSeparator() +"b" + System.lineSeparator() + "c" 
					+ System.lineSeparator() + "2" + System.lineSeparator() + "2" + System.lineSeparator() 
					+ "a" + System.lineSeparator() + "a" + System.lineSeparator() + "c"
					+ System.lineSeparator() + "3" + System.lineSeparator() + "3" 
					+ System.lineSeparator() + "r" + System.lineSeparator() + "a" + System.lineSeparator() 
					+ "c" + System.lineSeparator() + "q";
			InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
			System.setIn(inputStreamSimulator);
			ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
			System.setOut(new PrintStream(outputStreamCaptor));
			Object frontend = new Frontend();
			((Frontend) frontend).run();
			System.setOut(standardOut);
			System.setIn(standardIn);
			String appOutput = outputStreamCaptor.toString();
			assertNotEquals(frontend, null);
			//System.out.println(appOutput);
			if(!appOutput.contains("Path 1)")) {
				fail();
			}
		} catch (Exception e) {
			System.setOut(standardOut);
			System.setIn(standardIn);
			e.printStackTrace();
			fail();
		}
	} 
	
	/**
	 *  This test method checks whether pressing 'd' works as intended
	 */
	@Test
	public void testDtoDelete() {
		PrintStream standardOut = System.out;
		InputStream standardIn = System.in;
		try {
			String input = "a" + System.lineSeparator() + "LA" + System.lineSeparator()
			+ "Madison" + System.lineSeparator() + "5" + System.lineSeparator() + "10" 
			+ System.lineSeparator() + "d" + System.lineSeparator() + "LA" + System.lineSeparator() 
			+ "Madison" + System.lineSeparator() + "q";
			InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
			System.setIn(inputStreamSimulator);
			ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
			System.setOut(new PrintStream(outputStreamCaptor));
			Object frontend = new Frontend();
			((Frontend) frontend).run();
			System.setOut(standardOut);
			System.setIn(standardIn);
			String appOutput = outputStreamCaptor.toString();
			assertNotEquals(frontend, null);
			//System.out.println(appOutput);
			if(!appOutput.contains("removed")) {
				fail();
			}
		} catch (Exception e) {
			System.setOut(standardOut);
			System.setIn(standardIn);
			e.printStackTrace();
			fail();
		}
	} 

}