/* Class to add JUnit tests to test your BST */

package cse12pa6student;

import static org.junit.Assert.*;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.*;

public class BSTTest {

	@Test
	public void testSetAndGet() {
		BSTDefaultMap<String, Integer> b = new BSTDefaultMap<String, Integer>(100000);
		assertEquals(0, b.size);
		b.set("", 15);
		assertEquals((Integer) 15, (Integer) b.get(""));
		b.set("A", 20);
		assertEquals((Integer) 100000, (Integer) b.get("C"));
		assertEquals((Integer) 20, (Integer) b.get("A"));
		b.set("A", 250);
		assertEquals((Integer) 250, (Integer) b.get("A"));
	}

	@Test(expected = NoSuchElementException.class)
	public void getNull() {
		BSTDefaultMap<String, Integer> b = new BSTDefaultMap<String, Integer>(0);
		b.set("A", null);
		b.get("A");
	}

	@Test(expected = NoSuchElementException.class)
	public void nullDefaultValue() {
		BSTDefaultMap<String, Integer> b = new BSTDefaultMap<String, Integer>(null);
		b.get("A");
	}

	@Test
	public void testContainsKey() {
		BSTDefaultMap<String, Integer> b = new BSTDefaultMap<String, Integer>(0);
		assertFalse(b.containsKey("A"));
		for (int i = 65; i < 91; i++) {
			b.set((char) i + "", i);
		}
		assertTrue(b.containsKey("Z"));
		assertFalse(b.containsKey("!@#$%^&(*)"));
	}

	@Test
	public void testSize() {
		BSTDefaultMap<String, Integer> b = new BSTDefaultMap<String, Integer>(0);
		assertEquals(0, b.size());
		for (int i = 65; i < 91; i++) {
			b.set((char) i + "", i);
		}
		assertEquals(26, b.size());
		b.set("a", 1);
		b.set("Z", 26); // should not change the size
		assertEquals(27, b.size());
	}

	@Test
	public void testDefaultValue() {
		BSTDefaultMap<String, Integer> b1 = new BSTDefaultMap<String, Integer>(null);
		assertNull(b1.defaultValue());
		BSTDefaultMap<String, Integer> b2 = new BSTDefaultMap<String, Integer>(0);
		assertEquals((Integer) 0, b2.defaultValue());
		BSTDefaultMap<String, Integer> b3 = new BSTDefaultMap<String, Integer>(1000000);
		assertEquals((Integer) 1000000, b3.defaultValue());
	}

	@Test
	public void testKeys() {
		BSTDefaultMap<String, Integer> b = new BSTDefaultMap<String, Integer>(0);
		List<String> lst = b.keys();
		assertEquals(0, lst.size());
		for (int i = 69; i >= 65; i--) {
			b.set((char) i + "", i);
		}
		lst = b.keys();
		assertEquals(5, lst.size());
		for (int i = 0; i < 5; i++) {
			assertEquals((char) (i + 65) + "", lst.get(i));
		}
	}

	@Test
	public void testValues() {
		BSTDefaultMap<String, Integer> b = new BSTDefaultMap<String, Integer>(0);
		List<Integer> lst = b.values();
		assertEquals(0, lst.size());
		for (int i = 69, j = 1; i >= 65; i--, j++) { //("E", 1), ("D", 2)...
			b.set((char) i + "", j);
		}
		lst = b.values();
		assertEquals(5, lst.size());
		for (int i = 0; i < 5; i++) {
			assertEquals((Integer) (5 - i), lst.get(i));
		}
	}
}