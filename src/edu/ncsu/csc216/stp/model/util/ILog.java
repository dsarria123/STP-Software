package edu.ncsu.csc216.stp.model.util;

/**
 * Represents as list where elements can only be added
 * to the end of the list.  Once an element is added, it cannot
 * be removed. 
 * @author Dr. Sarah Heckman
 * 
 * @param <E> type for the ILog
 */
public interface ILog<E> {
	
	/**
	 * Adds the element to the end of the list.
	 * @param element element to add
	 * @throws NullPointerException if element is null 
	 */
	void add(E element);
	
	/**
	 * Returns the element at the given index.
	 * @param idx index of the element to retrieve
	 * @return element at the given index
	 * @throws IndexOutOfBoundsException if the idx is out of bounds
	 * 		for the list
	 */
	E get(int idx);
	
	/**
	 * Returns the number of elements in the list.
	 * @return number of elements in the list
	 */
	int size();

}