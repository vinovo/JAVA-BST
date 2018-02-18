/* 
 * Implementation of a DefaultMap<K, V>
 * Fill in the required methods (and others you might need)
 * 
 */

package cse12pa6student;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class BSTDefaultMap<K extends Comparable<K>, V> implements DefaultMap<K, V> {
	V defaultValue;
	int size;
	Node<K, V> root;

	public BSTDefaultMap(V defaultValue) {
		/* Constructor */
		this.defaultValue = defaultValue;
		this.size = 0;
		root = null;
	}

	@Override
	public void set(K key, V value) {
		if (size == 0)
			this.root = new Node<K, V>(key, value);
		else {
			Node<K, V> node = root.findPos(key);
			if (node == null)
				node = new Node<K, V>(key, value);
			else {
				node.v = value;
				size--; // If the key is already in the tree, no need to
						// increment size.
			}
		}
		size++;
	}

	@Override
	public V get(K key) {
		V toReturn = null;
		Node<K, V> node = root.findPos(key);
		if (node == null)
			toReturn = this.defaultValue;
		else
			toReturn = node.v;
		if (toReturn == null)
			throw new NoSuchElementException();
		else
			return toReturn;
	}

	@Override
	public boolean containsKey(K key) {
		Node<K, V> node = root.findPos(key);
		if (node == null)
			return false;
		else
			return true;
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public V defaultValue() {
		return this.defaultValue;
	}

	@Override
	public List<K> keys() {
		List<K> keyList = new ArrayList<K>();
		List<Node<K, V>> nodeList = root.formList();
		for (int i = 0; i < nodeList.size(); i++) {
			keyList.add(nodeList.get(i).k);
		}
		return keyList;
	}

	@Override
	public List<V> values() {
		List<V> valueList = new ArrayList<V>();
		List<Node<K, V>> nodeList = root.formList();
		for (int i = 0; i < nodeList.size(); i++) {
			valueList.add(nodeList.get(i).v);
		}
		return valueList;
	}

	// Inner class Node that helps building a BST.
	public class Node<key extends Comparable<key>, value> {
		value v;
		key k;
		Node<key, value> left;
		Node<key, value> right;

		public Node(key k, value v) {
			this.k = k;
			this.v = v;
			left = null;
			right = null;
		}

		public Node<key, value> findPos(key K) {
			if (K.compareTo(this.k) == 0)
				return this;
			else if (K.compareTo(this.k) < 0) {
				if (this.left == null)
					return this.left;
				else
					return this.left.findPos(K);
			} else {
				if (this.right == null)
					return this.right;
				else
					return this.right.findPos(K);
			}
		}

		public List<Node<key, value>> formList() {
			List<Node<key, value>> lst = new ArrayList<Node<key, value>>();
			if (this.left == null && this.right == null)
				lst.add(this);
			else {
				if (this.left != null)
					lst.addAll(this.left.formList());
				lst.add(this);
				if (this.right != null)
					lst.addAll(this.right.formList());
			}
			return lst;
		}
	}
}
