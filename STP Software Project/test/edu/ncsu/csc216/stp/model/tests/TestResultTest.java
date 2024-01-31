package edu.ncsu.csc216.stp.model.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

/**
 * Test result test
 * 
 * @author Bryan Phillips
 * @author Diego Sarria
 */
public class TestResultTest {

	/**
	 * Tests the test result constructor
	 */
	@Test
	public void testTestResultConstructor() {
		TestResult a = assertDoesNotThrow(() -> new TestResult(false, "results"),
				"Should not throw exception");
		
		assertFalse(a.isPassing());
		assertEquals("results", a.getActualResults());
		
		
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> new TestResult(false, ""));
		assertEquals("Invalid test results.", exception.getMessage(), "Testing empty results message");
		
		exception = assertThrows(IllegalArgumentException.class,
				() -> new TestResult(true, null));
		assertEquals("Invalid test results.", exception.getMessage(), "Testing null results message");
	}
}
