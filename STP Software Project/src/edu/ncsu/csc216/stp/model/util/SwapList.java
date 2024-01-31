package edu.ncsu.csc216.stp.model.util;

/**
 * The SwapList class implements the ISwapList interface. The SwapList allows
 * duplicate elements.
 * 
 * @param <E> Swaplist
 * 
 * @author Bryan Phillips
 * @author Diego Sarria
 */
public class SwapList<E> implements ISwapList<E> {

	/** Initial capacity of list */
	private static final int INITIAL_CAPACITY = 10;

	/** Current list */
	private E[] list;

	/** Size of list */
	private int size;

	/**
	 * Constructs an empty list with an initial capacity.
	 */
	public SwapList() {
		list = (E[]) new Object[INITIAL_CAPACITY];
		size = 0;

	}

	/**
	 * Adds an element to the list
	 * 
	 * @param element element
	 * 
	 * @throws NullPointerException if element is null
	 */
	public void add(E element) {
		if (element == null) {
			throw new NullPointerException("Cannot add null element.");
		}
		checkCapacity(size + 1);
		list[size++] = element;

	}

	/**
	 * Checks capacity of list
	 * 
	 * @param idx index
	 */
	private void checkCapacity(int idx) {
		if (size >= list.length) {
			int newLength = list.length * 2 + 1;
			E[] newList = (E[]) new Object[newLength];
			for (int i = 0; i < size; i++) {
				newList[i] = list[i];
			}
			list = newList;
		}
	}

	/**
	 * Removes an element from the list
	 * 
	 * @param idx idx to remove element at
	 * @return E element to be removed
	 */
	public E remove(int idx) {

		checkIndex(idx);
		E element = list[idx];
		for (int i = idx; i < size - 1; i++) {
			list[i] = list[i + 1];
		}
		list[size - 1] = null;
		size--;
		return element;
	}

	/**
	 * Checks index of a certain element in the list
	 * 
	 * @param idx idx to check element at
	 * 
	 * @throws IndexOutOfBoundsException if index is invalid
	 */
	private void checkIndex(int idx) {
		if (idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException("Invalid index.");
		}
	}

	/**
	 * Moves an element in the list up
	 * 
	 * @param idx idx to move element to
	 */
	public void moveUp(int idx) {
		checkIndex(idx);
		if (idx > 0) {
			E temp = list[idx];
			list[idx] = list[idx - 1];
			list[idx - 1] = temp;
		}

	}

	/**
	 * Moves and element in the list down
	 * 
	 * @param idx idx to move element to
	 */
	public void moveDown(int idx) {
		checkIndex(idx);
		if (idx < size - 1) {
			E temp = list[idx];
			list[idx] = list[idx + 1];
			list[idx + 1] = temp;
		}

	}

	/**
	 * Moves and element in the list to the front
	 * 
	 * @param idx idx to move element to
	 */
	public void moveToFront(int idx) {
		checkIndex(idx);
		if (idx > 0) {
			E temp = list[idx];
			for (int i = idx; i > 0; i--) {
				list[i] = list[i - 1];
			}
			list[0] = temp;

		}

	}

	/**
	 * Moves and element in the list to the back
	 * 
	 * @param idx idx to move element to
	 */
	public void moveToBack(int idx) {
		checkIndex(idx);
		if (idx < size - 1) {
			E temp = list[idx];

			for (int i = idx; i < size - 1; i++) {
				list[i] = list[i + 1];
			}
			list[size - 1] = temp;
		}

	}

	/**
	 * Returns element in the list at certain index
	 * 
	 * @param idx idx to retrieve element
	 * @return E element at index
	 */
	public E get(int idx) {
		checkIndex(idx);
		return list[idx];
	}

	/**
	 * Returns size of list
	 * 
	 * @return size of the list
	 */
	public int size() {

		return size;
	}

}
