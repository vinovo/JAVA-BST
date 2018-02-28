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

	// helper method that collects words from data to a list
	private static List<String> collectWords(List<String> lst, int nValue) {
		List<String> gramList = new ArrayList<String>();
		List<String> stringList = new ArrayList<String>();
		for (String str : lst) {
			String s = new String(str); // each s is a line of text
			while (s.length() > 0) { // When the line is not empty
				// Delete all the blank spaces before a word
				while (s.length() > 0 && s.charAt(0) == ' ') {
					s = s.substring(1);
				}
				int spaceIndex = s.indexOf(" "); // find first blank space after
													// the string.
				// if there's no more blank spaces in the line.
				if (spaceIndex < 0) {
					if (s.length() > 0) {
						stringList.add(s);
						s = "";
					} else
						break;
				} else { // when there's still blank spaces.
					String add = s.substring(0, spaceIndex);
					if (add.charAt(0) == '\'')
						// If the starting character is an apostrophes, add it
						// to
						// the end of last word
						stringList.set(stringList.size() - 1, stringList.get(stringList.size() - 1) + add);
					// if the preceded string is a period.
					else if (stringList.size() > 0 && stringList.get(stringList.size() - 1).equals(".")) {
						// if the string to be added starts with an upper-case
						// letter,
						// convert it to lower
						add = toLowerCase(add);
						stringList.add(add);
					} else
						stringList.add(add); // Otherwise simply add the string
												// to the list.
					s = s.substring(spaceIndex);
				}
			}
			// !!! signals the end of a line.
			stringList.add("!!!");
		}
		// stringList should have been properly filled upon here.
		for (int i = 0; i < stringList.size() - nValue + 1; i++) {
			// boolean to check whether to add the String to list
			boolean add = true;
			// Increment i until we get a word
			while (i < stringList.size() && !isWord(stringList.get(i)))
				i++;
			// If there's no enough valid word left in the list, just break the
			// for loop
			if (i >= stringList.size() - nValue + 1)
				break;
			String str = stringList.get(i);
			for (int j = i + 1; j < i + nValue; j++) {
				if (!isWord(stringList.get(j))) {
					i = j; // skip all the element between i and j.
					add = false; // Don't add this String to the list.
					break;
				}
				str += " " + stringList.get(j);
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

	// helper method that convert starting upper letter to lower
	private static String toLowerCase(String s) {
		if (!s.equals("I")) // If s is a single letter "I", don't transform to
							// lower case
			s = Character.toLowerCase(s.charAt(0)) + s.substring(1);
		return s;
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
		input.close();
		long start2 = System.currentTimeMillis();
		final Graph chart = makeGraph(database, queries);
		chart.showChart();
		long end2 = System.currentTimeMillis();
		System.out.println("runtime of making graph is " + (end2 - start2));
	}
}
