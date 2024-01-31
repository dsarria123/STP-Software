package edu.ncsu.csc216.stp.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the SwapList class
 */
public class SwapListTest {

	/**
	 * SwapList field
	 */
    private SwapList<String> swapList;

    /**
     * Sets up the swaplist for testing
     */
    @BeforeEach
    public void setUp() {
        swapList = new SwapList<String>();
    }

    /**
     * Tests the add method
     */
    @Test
    public void testAdd() {
        assertEquals(0, swapList.size());

        // Add elements to the swap list
        swapList.add("A");
        swapList.add("B");
        swapList.add("C");
        swapList.add("D");
        swapList.add("E");

        assertEquals(5, swapList.size());

        // Check the elements in the swap list
        assertEquals("A", swapList.get(0));
        assertEquals("B", swapList.get(1));
        assertEquals("C", swapList.get(2));
        assertEquals("D", swapList.get(3));
        assertEquals("E", swapList.get(4));
    }
    
    /**
     * Tests the add method but with capacity expansion
     */
    @Test
    public void testAddWithCapacityExpansion() {
        assertEquals(0, swapList.size());

        // Add elements to reach the initial capacity
        swapList.add("A");
        swapList.add("B");
        swapList.add("C");
        swapList.add("D");
        swapList.add("E");
        swapList.add("F");
        swapList.add("G");
        swapList.add("H");
        swapList.add("I");
        swapList.add("J");

        assertEquals(10, swapList.size());

        // Check the elements in the swap list
        assertEquals("A", swapList.get(0));
        assertEquals("B", swapList.get(1));
        assertEquals("C", swapList.get(2));
        assertEquals("D", swapList.get(3));
        assertEquals("E", swapList.get(4));
        assertEquals("F", swapList.get(5));
        assertEquals("G", swapList.get(6));
        assertEquals("H", swapList.get(7));
        assertEquals("I", swapList.get(8));
        assertEquals("J", swapList.get(9));

        // Add one more element to trigger capacity expansion
        swapList.add("K");
        assertEquals(11, swapList.size());

        // Check the elements after capacity expansion
        assertEquals("A", swapList.get(0));
        assertEquals("B", swapList.get(1));
        assertEquals("C", swapList.get(2));
        assertEquals("D", swapList.get(3));
        assertEquals("E", swapList.get(4));
        assertEquals("F", swapList.get(5));
        assertEquals("G", swapList.get(6));
        assertEquals("H", swapList.get(7));
        assertEquals("I", swapList.get(8));
        assertEquals("J", swapList.get(9));
        assertEquals("K", swapList.get(10));
    }


    /**
     * tests the add method but for adding a null element
     */
    @Test
    public void testAddNull() {
        // Adding null should throw a NullPointerException
        assertThrows(NullPointerException.class, () -> swapList.add(null));
    }

    /**
     * Tests the remove method
     */
    @Test
    public void testRemove() {
        // Add elements to the swap list
        swapList.add("A");
        swapList.add("B");
        swapList.add("C");
        swapList.add("D");
        swapList.add("E");

        // Remove elements and check the size and remaining elements
        assertEquals(5, swapList.size());

        // Remove element "C" (index 2)
        assertEquals("C", swapList.remove(2));
        assertEquals(4, swapList.size());
        assertEquals("A", swapList.get(0));
        assertEquals("B", swapList.get(1));
        assertEquals("D", swapList.get(2));
        assertEquals("E", swapList.get(3));

        // Remove element "A" (index 0)
        assertEquals("A", swapList.remove(0));
        assertEquals(3, swapList.size());
        assertEquals("B", swapList.get(0));
        assertEquals("D", swapList.get(1));
        assertEquals("E", swapList.get(2));
    }

    /**
     * Tests the remove method at an invalid index
     */
    @Test
    public void testRemoveInvalidIndex() {
        // Removing an element at an invalid index should throw an IndexOutOfBoundsException
        assertThrows(IndexOutOfBoundsException.class, () -> swapList.remove(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> swapList.remove(0));

        // Add elements to the swap list
        swapList.add("A");
        swapList.add("B");
        swapList.add("C");

        // Trying to remove an element at an invalid index should throw an IndexOutOfBoundsException
        assertThrows(IndexOutOfBoundsException.class, () -> swapList.remove(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> swapList.remove(3));
    }

    /**
     * Tests the moveUp method
     */
    @Test
    public void testMoveUp() {
        // Add elements to the swap list
        swapList.add("A");
        swapList.add("B");
        swapList.add("C");
        swapList.add("D");
        swapList.add("E");

        // Move element "D" up (index 3)
        swapList.moveUp(3);
        assertEquals("D", swapList.get(2));
        assertEquals("C", swapList.get(3));

        // Try to move the first element up, should remain the same
        swapList.moveUp(0);
        assertEquals("A", swapList.get(0));
    }

    /**
     * Tests the moveDown method
     */
    @Test
    public void testMoveDown() {
        // Add elements to the swap list
        swapList.add("A");
        swapList.add("B");
        swapList.add("C");
        swapList.add("D");
        swapList.add("E");

        // Move element "B" down (index 1)
        swapList.moveDown(1);
        assertEquals("C", swapList.get(1));
        assertEquals("B", swapList.get(2));

        // Try to move the last element down, should remain the same
        swapList.moveDown(4);
        assertEquals("E", swapList.get(4));
    }

    /**
     * Tests the moveToFront method
     */
    @Test
    public void testMoveToFront() {
        // Add elements to the swap list
        swapList.add("A");
        swapList.add("B");
        swapList.add("C");
        swapList.add("D");
        swapList.add("E");

        // Move element "C" to the front (index 2)
        swapList.moveToFront(2);
        assertEquals("C", swapList.get(0));
        assertEquals("A", swapList.get(1));
        assertEquals("B", swapList.get(2));
        assertEquals("D", swapList.get(3));
        assertEquals("E", swapList.get(4));

        // Try to move the first element to the front, should remain the same
        swapList.moveToFront(0);
        assertEquals("C", swapList.get(0));
    }

    /**
     * Tests the moveToBack method
     */
    @Test
    public void testMoveToBack() {
        // Add elements to the swap list
        swapList.add("A");
        swapList.add("B");
        swapList.add("C");
        swapList.add("D");
        swapList.add("E");

        // Move element "D" to the back (index 3)
        swapList.moveToBack(3);
        assertEquals("A", swapList.get(0));
        assertEquals("B", swapList.get(1));
        assertEquals("C", swapList.get(2));
        assertEquals("E", swapList.get(3));
        assertEquals("D", swapList.get(4));

        // Try to move the last element to the back, should remain the same
        swapList.moveToBack(4);
        assertEquals("D", swapList.get(4));
    }

    /**
     * Tests the get method
     */
    @Test
    public void testGet() {
        // Add elements to the swap list
        swapList.add("A");
        swapList.add("B");
        swapList.add("C");
        swapList.add("D");
        swapList.add("E");

        assertEquals("A", swapList.get(0));
        assertEquals("B", swapList.get(1));
        assertEquals("C", swapList.get(2));
        assertEquals("D", swapList.get(3));
        assertEquals("E", swapList.get(4));
    }

    /**
     * Tests the size method
     */
    @Test
    public void testSize() {
        assertEquals(0, swapList.size());

        // Add elements to the swap list
        swapList.add("A");
        swapList.add("B");
        swapList.add("C");

        assertEquals(3, swapList.size());

        // Remove elements and check the size
        swapList.remove(1);
        assertEquals(2, swapList.size());

        swapList.remove(0);
        assertEquals(1, swapList.size());

        swapList.remove(0);
        assertEquals(0, swapList.size());
    }
}
