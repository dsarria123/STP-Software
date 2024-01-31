package edu.ncsu.csc216.stp.model.util;

/**
 * The Log class implements the ILog interface. The Log allows duplicate
 * elements.
 * 
 * @param <E> Log
 * 
 * @author Bryan Phillips
 * @author Diego Sarria
 */
public class Log<E> implements ILog<E> {

	/** Log array list */
	private E[] log;

	/** Size of array */
	private int size;

	/** Log's capacity */
	private static final int INIT_CAPACITY = 10;

	/**
	 * Log constructor
	 */
	public Log() {
		log = (E[]) new Object[INIT_CAPACITY];
		size = 0;
	}

	/**
	 * Adds an element
	 * 
	 * @param element element
	 * 
	 * @throws NullPointerException if element is null
	 */
	public void add(E element) {
		if (element == null) {
			throw new NullPointerException("Cannot add null element.");
		}
		if (size >= log.length) {
			int newCapacity = log.length * 2;
			E[] newLog = (E[]) new Object[newCapacity];
			for (int i = 0; i < size; i++) {
				newLog[i] = log[i];
			}
			log = newLog;
		}
		log[size] = element;
		size++;
	}

	/**
	 * Gets the element at given idx.
	 * 
	 * @param idx index to retrieve element
	 * @return E element at idx
	 * 
	 * @throws IndexOutOfBoundsException for an invalid index
	 */
	public E get(int idx) {
		if (idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException("Invalid index.");
		}

		return log[idx];
	}

	/**
	 * Gets the size of the array list.
	 * 
	 * @return size of array
	 */
	public int size() {
		return size;
	}
}
