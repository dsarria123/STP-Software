package edu.ncsu.csc216.stp.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests SortedList class
 */
public class SortedListTest {

	/**
	 * SortedList field
	 */
	private SortedList<Integer> sortedList;

	/**
	 * Sets up the SortedList for testing
	 */
	@BeforeEach
	public void setUp() {
		sortedList = new SortedList<Integer>();
	}

	/**
	 * Tests the add method
	 */
	@Test
	public void testAdd() {
		// Add elements to the sorted list
		sortedList.add(5);
		sortedList.add(3);
		sortedList.add(8);
		sortedList.add(1);
		sortedList.add(9);

		// Check that the elements are sorted correctly
		assertEquals(1, sortedList.get(0));
		assertEquals(3, sortedList.get(1));
		assertEquals(5, sortedList.get(2));
		assertEquals(8, sortedList.get(3));
		assertEquals(9, sortedList.get(4));
	}

	/**
	 * Tests the add method for duplicate elements
	 */
	@Test
	public void testAddDuplicate() {
		// Add an element to the sorted list
		sortedList.add(5);

		// Adding a duplicate element should throw an IllegalArgumentException
		assertThrows(IllegalArgumentException.class, () -> sortedList.add(5));
	}

	/**
	 * Tests the add method for null elements
	 */
	@Test
	public void testAddNullElement() {
		// Adding a null element should throw a NullPointerException
		assertThrows(NullPointerException.class, () -> sortedList.add(null));
	}

	/**
	 * Tests the remove method
	 */
	@Test
	public void testRemove() {
		// Add elements to the sorted list
		sortedList.add(5);
		sortedList.add(3);
		sortedList.add(8);
		sortedList.add(1);
		sortedList.add(9);

		// Remove elements and check the size and remaining elements
		assertEquals(5, sortedList.size());

		// Remove element 3 (index 1)
		assertEquals(3, sortedList.remove(1));
		assertEquals(4, sortedList.size());
		assertEquals(1, sortedList.get(0));
		assertEquals(5, sortedList.get(1));
		assertEquals(8, sortedList.get(2));
		assertEquals(9, sortedList.get(3));

		// Remove element 9 (index 3)
		assertEquals(9, sortedList.remove(3));
		assertEquals(3, sortedList.size());
		assertEquals(1, sortedList.get(0));
		assertEquals(5, sortedList.get(1));
		assertEquals(8, sortedList.get(2));

		// Remove element 5 (index 1)
		assertEquals(5, sortedList.remove(1));
		assertEquals(2, sortedList.size());
		assertEquals(1, sortedList.get(0));
		assertEquals(8, sortedList.get(1));
	}

	/**
	 * Tests the remove method but at an invalid index
	 */
	@Test
	public void testRemoveInvalidIndex() {
		// Removing an element at an invalid index should throw an
		// IndexOutOfBoundsException
		assertThrows(IndexOutOfBoundsException.class, () -> sortedList.remove(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> sortedList.remove(0));
	}

	/**
	 * Tests the contains method
	 */
	@Test
	public void testContains() {
		// Add elements to the sorted list
		sortedList.add(5);
		sortedList.add(3);
		sortedList.add(8);

		// Check if the elements are contained in the list
		assertTrue(sortedList.contains(5));
		assertTrue(sortedList.contains(3));
		assertTrue(sortedList.contains(8));
		assertFalse(sortedList.contains(2));
		assertFalse(sortedList.contains(9));
	}

	/**
	 * Tests the get method
	 */
	@Test
	public void testGet() {
		// Add elements to the sorted list
		sortedList.add(5);
		sortedList.add(3);
		sortedList.add(8);

		// Retrieve elements by index
		assertEquals(3, sortedList.get(0));
		assertEquals(5, sortedList.get(1));
		assertEquals(8, sortedList.get(2));
	}

	/**
	 * Tests the get method at an invalid index
	 */
	@Test
	public void testGetInvalidIndex() {
		// Trying to get an element at an invalid index should throw an
		// IndexOutOfBoundsException
		assertThrows(IndexOutOfBoundsException.class, () -> sortedList.get(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> sortedList.get(3));
	}

	/**
	 * Tests the size method
	 */
	@Test
	public void testSize() {
		// Add elements to the sorted list
		sortedList.add(5);
		sortedList.add(3);
		sortedList.add(8);

		// Check the size of the sorted list
		assertEquals(3, sortedList.size());
	}
}
