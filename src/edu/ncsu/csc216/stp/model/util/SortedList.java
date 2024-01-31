package edu.ncsu.csc216.stp.model.util;

/**
 * The SortedList class implements the ISortedList interface. Extends the
 * Comparable class The SortedList class uses the Comparable.compareTo() method
 * to determine the ordering of elements.
 * 
 * @param <E> Sorted list
 * 
 * @author Bryan Phillips
 * @author Diego Sarria
 */
public class SortedList<E extends Comparable<E>> implements ISortedList<E> {

	/**
	 * size of list
	 */
	private int size;

	/**
	 * ListNode at the front of list
	 */
	private ListNode front;

	/**
	 * Public constructor
	 */
	public SortedList() {
		front = null;
		size = 0;
	}

	/**
	 * Adds and element to the list
	 * 
	 * @param element E
	 * 
	 * @throws NullPointerException if element is null
	 * @throws IllegalArgumentException if element is a duplicate
	 */
	public void add(E element) {
		if (element == null) {
			throw new NullPointerException("Cannot add null element.");
		}

		if (contains(element)) {
			throw new IllegalArgumentException("Cannot add duplicate element.");
		}

		ListNode prev = null;
		ListNode current = front;

		while (current != null && element.compareTo(current.data) > 0) {
			prev = current;
			current = current.next;
		}

		if (prev == null) {
			front = new ListNode(element, front);
		} else {
			prev.next = new ListNode(element, current);
		}

		size++;
	}

	/**
	 * Removes an element from the list
	 * 
	 * @param idx index
	 * @return E e
	 */
	public E remove(int idx) {
		checkIndex(idx);
		ListNode prev = null;
		ListNode current = front;

		for (int i = 0; i < idx; i++) {
			prev = current;
			current = current.next;
		}

		if (prev == null) {
			front = front.next;
		} else {
			prev.next = current.next;
		}

		size--;
		return current.data;
	}

	/**
	 * Checks at which index something is in the list
	 * 
	 * @param idx index
	 * 
	 * @throws IndexOutOfBoundsException if index is invalid
	 */
	private void checkIndex(int idx) {
		if (idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException("Invalid index.");
		}
	}

	/**
	 * Check to see if the list contains an element
	 * 
	 * @param element E
	 * @return boolean boolean
	 */
	public boolean contains(E element) {
		ListNode current = front;
		while (current != null) {
			if (current.data.equals(element)) {
				return true;
			}
			current = current.next;
		}
		return false;
	}

	/**
	 * Gets element from list
	 * 
	 * @param idx index
	 * @return E element at the given index
	 */
	public E get(int idx) {
		checkIndex(idx);
		ListNode current = front;

		for (int i = 0; i < idx; i++) {
			current = current.next;
		}

		return current.data;
	}

	/**
	 * Returns the size of the list
	 * 
	 * @return int size
	 */
	public int size() {
		return size;
	}

	/**
	 * Inner class ListNode
	 */
	private class ListNode {

		/**
		 * List node's data
		 */
		public E data;

		/**
		 * Next list node
		 */
		public ListNode next;

		/**
		 * List node constructor
		 * 
		 * @param data data
		 * @param next next
		 */
		public ListNode(E data, ListNode next) {
			this.data = data;
			this.next = next;
		}
	}
}
