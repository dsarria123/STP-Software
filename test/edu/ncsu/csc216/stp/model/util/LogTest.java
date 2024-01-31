package edu.ncsu.csc216.stp.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the Log class
 */
public class LogTest {

	/**
	 * Log instance
	 */
	private Log<String> log;

	/**
	 * Setup for log list
	 */
	@BeforeEach
	public void setUp() {
		log = new Log<String>();
	}

	/**
	 * Tests the add method
	 */
	@Test
	public void testAdd() {
		// Add elements to the log exceeding the initial capacity
		for (int i = 0; i < 15; i++) {
			log.add("Element " + i);
		}

		// Check that the elements were added correctly
		assertEquals("Element 0", log.get(0));
		assertEquals("Element 1", log.get(1));

	}

	/**
	 * Tests the add methof or a null element
	 */
	@Test
	public void testAddNullElement() {
		// Adding a null element should throw a NullPointerException
		assertThrows(NullPointerException.class, () -> log.add(null));
	}

	/**
	 * Tests the get method 
	 */
	@Test
	public void testGet() {
		// Add elements to the log
		log.add("X");
		log.add("Y");
		log.add("Z");

		// Retrieve elements by index
		assertEquals("X", log.get(0));
		assertEquals("Y", log.get(1));
		assertEquals("Z", log.get(2));
	}

	/**
	 * Tests the get method at an invalid index
	 */
	@Test
	public void testGetInvalidIndex() {
		// Trying to get an element at an invalid index should throw an
		// IndexOutOfBoundsException
		assertThrows(IndexOutOfBoundsException.class, () -> log.get(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> log.get(10));
	}

	/**
	 * Tests the size method
	 */
	@Test
	public void testSize() {
		// Add elements to the log
		log.add("1");
		log.add("2");
		log.add("3");

		// Check the size of the log
		assertEquals(3, log.size());
	}

}
