import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVTOHTML {

    public static void main(String[] args) {
        try {
            System.out.println("<html><head><link rel = \"stylesheet\" href = \"format.css\"></head><body><p>");
		List<String[]> cereal = readAll(new File("data.csv"));
            System.out.println("<table><tr>");
		for(int i = 0; i < (cereal.get(0)).length; i++) {
	            System.out.println("<th>" + (cereal.get(0))[i] + "</th>");
		}
	    System.out.println("</tr>");
		for(int j = 1; j < cereal.size(); j++) {
			System.out.println("<tr>");
		for(int k = 0; k < (cereal.get(j)).length; k++) {
				System.out.println("<td>" + (cereal.get(j))[k] + "</td>");
			}
		System.out.println("</tr>");
		}
		System.out.println("</table>");

            System.out.println("</p></body></html>");
        } catch(Exception e) {
            System.out.println("<html><body><pre>");
            System.out.println("Ooops, something went wrong. There was an exception in the Java program:");
            e.printStackTrace(System.out);
            System.out.println("</pre></body></html>");
        }
    }

    public static List<String[]> readAll(File csvFile) throws Exception {
        List<String[]> output = new ArrayList<>();
        try (Scanner fin = new Scanner(csvFile)) {
            while(fin.hasNextLine()) {
                output.add(fin.nextLine().split(","));
            }    
        }
        return output;
    }    

}

