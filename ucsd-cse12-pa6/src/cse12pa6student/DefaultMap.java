package cse12pa6student;

import java.util.List;

public interface DefaultMap<K, V> {
	/*
	 * Sets key to hold value: future calls to get() should find the given key and
	 * size increases by 1.
	 * 
	 * Throws IllegalArgumentException if key is null (but value can be null)
	 * 
	 */
	void set(K key, V value);

	/*
	 * Returns the value associated with key if it has been set
	 * 
	 * If key has _not_ been set, returns a default value
	 * 
	 * Throws IllegalArgumentException if key is null
	 */
	V get(K key);
	
	/*
	 * Returns true if the given key was set by set, false otherwise
	 */
	boolean containsKey(K key);

	/*
	 * Returns the number of key/value pairs in the tree
	 */
	int size();

	/*
	 * Returns the default value for this map
	 */
	V defaultValue();

	/*
	 * Returns a list of all keys in sorted order
	 */
	List<K> keys();

	/*
	 * Returns a list of all values in order _according to the corresponding key order_
	 */
	List<V> values();

}
