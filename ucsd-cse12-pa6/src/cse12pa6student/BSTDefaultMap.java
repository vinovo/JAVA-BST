package cse12pa6student;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
//import java.util.IllegalArgumentException;

class Node<K, V> {
	public final K k;
	public V v;
	public Node<K, V> left;
	public Node<K, V> right;

	public Node(K k, V v, Node<K, V> left, Node<K, V> right) {
		this.k = k;
		this.v = v;
		this.left = left;
		this.right = right;
	}
}

public class BSTDefaultMap<K extends Comparable<K>, V> implements DefaultMap<K, V> {
	public Node<K, V> root;
	V defaultValue;
	private int size = 0;

	public BSTDefaultMap(V defaultValue) {
		this.root = null;
		this.defaultValue = defaultValue;
	}

	private Node<K, V> set(Node<K, V> node, K k, V v) {
		if (node == null) {
			this.size += 1;
			return new Node<K, V>(k, v, null, null);
		}
		if (node.k.compareTo(k) < 0) {
			node.right = this.set(node.right, k, v);
			return node;
		} else if (node.k.compareTo(k) > 0) {
			node.left = this.set(node.left, k, v);
			return node;
		} else {
			node.v = v;
			return node;
		}
	}

	public void set(K key, V value) {
    if (key == null) throw new IllegalArgumentException();
		this.root = this.set(this.root, key, value);
	}

	private V get(Node<K, V> node, K k) {
		if (node == null && this.defaultValue != null) {
			return this.defaultValue;
		}
		if (node == null && this.defaultValue == null) {
			throw new NoSuchElementException();
		}
		if (node.k.compareTo(k) > 0) {
			return get(node.left, k);
		} else if (node.k.compareTo(k) < 0) {
			return get(node.right, k);
		} else {
			return node.v;
		}
	}

	@Override
	public V get(K key) {
    if (key == null) throw new IllegalArgumentException();
		return get(this.root, key);
	}

	@Override
	public int size() {
		return this.size;
	}
	
	@Override
	public V defaultValue() {
		return this.defaultValue;
	}

	private void keys(Node<K, V> node, List<K> acc) {
		if (node == null) {
			return;
		}
		keys(node.left, acc);
		acc.add(node.k);
		keys(node.right, acc);
	}
	private void values(Node<K, V> node, List<V> acc) {
		if (node == null) {
			return;
		}
		values(node.left, acc);
		acc.add(node.v);
		values(node.right, acc);
	}

	@Override
	public List<K> keys() {
		List<K> l = new ArrayList<>();
		keys(this.root, l);
		return l;
	}
	@Override
	public List<V> values() {
		List<V> l = new ArrayList<>();
		values(this.root, l);
		return l;
	}
	
	private boolean containsKey(Node<K, V> node, K k) {
		if (node == null) {
			return false;
		}
		if (node.k.compareTo(k) > 0) {
			return containsKey(node.left, k);
		} else if (node.k.compareTo(k) < 0) {
			return containsKey(node.right, k);
		} else {
			return true;
		}
	}

	@Override
	public boolean containsKey(K key) {
    if (key == null) throw new IllegalArgumentException();
		return containsKey(this.root, key);
	}
}

