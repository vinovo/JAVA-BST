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

public class Loader {

	public static String FILES_PATH = "path to test files";

	/** TODO **/
	/** Path is found in java.nio.file **/
	public static DefaultMap<Integer, DefaultMap<String, Integer>> generateDatabase(Path p) {
		FILES_PATH = p.toString();
		try {
			DefaultMap<Integer, DefaultMap<String, Integer>> yearMap = new BSTDefaultMap<Integer, DefaultMap<String, Integer>>(
					null);
			DefaultMap<String, Integer> gramMap;
			DirectoryStream<Path> d = Files.newDirectoryStream(p, "*.{txt}");
			for (Path path : d) {
				List<String> lst = Files.readAllLines(path);
				List<String> allWords = new ArrayList<String>();
				// combine lists for different n.
				for (int i = 1; i < 4; i++) {
					allWords.addAll(collectWords(lst, i));
				}
				String pathway = path.toString();
				int yearIndex = pathway.indexOf(".txt") - 4;
				Integer year = Integer.parseInt(pathway.substring(yearIndex, yearIndex + 4));
				// if yearMap already contains the year, set the gramMap to
				// existing map
				if (yearMap.containsKey(year)) {
					gramMap = yearMap.get(year);
					for (int i = 0; i < allWords.size(); i++) {
						String word = allWords.get(i);
						if (!gramMap.containsKey(word))
							gramMap.set(word, 1);
						else
							gramMap.set(word, gramMap.get(word) + 1);
					}
				} else { // else create a new gramMap
					gramMap = new BSTDefaultMap<String, Integer>(0);
					for (int i = 0; i < allWords.size(); i++) {
						String word = allWords.get(i);
						if (!gramMap.containsKey(word))
							gramMap.set(word, 1);
						else
							gramMap.set(word, gramMap.get(word) + 1);
					}
					yearMap.set(year, gramMap);
				}

			}
			return yearMap;
		} catch (IOException e) {
			System.out.println("Invalid Path!");
			return null;
		}
	}

	/** TODO **/
	public static Graph makeGraph(DefaultMap<Integer, DefaultMap<String, Integer>> db, String[] query) {
		if (db == null || query == null)
			throw new NullPointerException("Some parameter is null!");
		String title = "";
		for (String s : query) {
			title += s + "; ";
		}
		title = title.substring(0, title.length() - 2);
		List<Integer> year = db.keys();
		DefaultMap<String, List<Integer>> map = new BSTDefaultMap<String, List<Integer>>(null);
		for (int i = 0; i < query.length; i++) {
			List<Integer> lst = new ArrayList<Integer>();
			for (int j = 0; j < year.size(); j++) {
				// add a query's count on certain year to the List of Integer
				lst.add(db.get(year.get(j)).get(query[i]));
			}
			map.set(query[i], lst); // Set the map with current query and its
									// corresponding Integer list
		}
		return new Graph(title, year, map);
	}

	// helper method
	private static List<String> collectWords(List<String> lst, int nValue) {
		List<String> gramList = new ArrayList<String>();
		List<String> wordList = new ArrayList<String>();
		for (String str : lst) {
			String s = new String(str);
			while (s.indexOf(" ") >= 0) { // When there's still space in the
											// String
				int spaceIndex = s.indexOf(" ");
				String add = s.substring(0, spaceIndex);
				if (add.charAt(0) == '\'')
					// If the starting character is an apostrophes, add it to
					// the end of last word
					wordList.set(wordList.size() - 1, wordList.get(wordList.size() - 1) + add);
				else
					wordList.add(add);
				s = s.substring(spaceIndex);
				// In case there are multiple spaces between words
				while (s.length() > 0 && s.charAt(0) == ' ') {
					s = s.substring(1);
				}
			}
			// In case the end of a line is not a space
			if (s.length() != 0)
				wordList.add(s);
			// !!! signals the end of a line.
			wordList.add("!!!");
		}
		// wordList should have been properly filled upon here.
		for (int i = 0; i < wordList.size() - nValue + 1; i++) {
			// boolean to check whether to add the String to list
			boolean add = true;
			// Increment i until we get a word
			while (i < wordList.size() && !isWord(wordList.get(i)))
				i++;
			// If there's no enough valid word left in the list, just break the
			// for loop
			if (i >= wordList.size() - nValue + 1)
				break;
			String str = wordList.get(i);
			for (int j = i + 1; j < i + nValue; j++) {
				if (!isWord(wordList.get(j))) {
					i = j; // skip all the element between i and j.
					add = false; // Don't add this String to the list.
					break;
				}
				str += " " + wordList.get(j);
			}
			if (add) {
				gramList.add(str);
			}
		}
		return gramList;
	}

	// helper method that determine if a String is a word.
	private static boolean isWord(String s) {
		if (!Character.isAlphabetic(s.charAt(0)) && !Character.isDigit(s.charAt(0)))
			return false;
		return true;
	}

	public static void main(String[] args) throws IOException {
		Path path = Paths.get("./data");
		long start1 = System.currentTimeMillis();
		DefaultMap<Integer, DefaultMap<String, Integer>> database = generateDatabase(path);
		long end1 = System.currentTimeMillis();
		System.out.println("runtime of database is " + (end1 - start1) / 1000);
		Scanner input = new Scanner(System.in);
		String[] queries = new String[2];
		int index = 0;
		while (index < queries.length) {
			System.out.print("Enter query: ");
			String query = input.nextLine();
			queries[index] = query;
			index++;
		}
		long start2 = System.currentTimeMillis();
		final Graph chart = makeGraph(database, queries);
		chart.showChart();
		long end2 = System.currentTimeMillis();
		System.out.println("runtime of making graph is " + (end2 - start2));
		/* Example of creating a chart */

		// String nGramPhrase = "";
		// List<Integer> listOfYears = new ArrayList<Integer>();
		// DefaultMap<String, List<Integer>> mapOfNGramToCounts = new
		// BSTDefaultMap<>(null);

		// final Graph chart = new Graph(nGramPhrase, listOfYears,
		// mapOfNGramToCounts);
		// chart.showChart();
	}
}
