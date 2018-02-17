/* 
 * Class to load data from file,
 * generate your BST, and create 
 * a chart from the data. 
 */


package cse12pa6student;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import org.jfree.ui.RefineryUtilities;

public class Loader {
	
	public static String FILES_PATH = "path to test files";
	
	
	/** TODO **/
	public static DefaultMap<Integer, DefaultMap<String, Integer>> generateDatabase(Path p) {
		return null;
	}
	
	/** TODO **/
	public static Graph makeGraph(String[] query) {
		return null;
	}
	
	public static void main(String[] args) throws IOException {
			
		
		 
		/* Example of creating a chart */
				
		String nGramPhrase = "";
		List<Integer> listOfYears = new ArrayList<Integer>();
		DefaultMap<String, List<Integer>> mapOfNGramToCounts = new BSTDefaultMap<>(null);
		
		final Graph chart = new Graph(nGramPhrase, listOfYears, mapOfNGramToCounts);
		chart.showChart();
		


	}
}
