package edu.ncsu.csc216.stp.model.test_plans;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

import edu.ncsu.csc216.stp.model.tests.TestCase;

/**
 * Test plan test
 */
public class TestPlanTest {

	/**
	 * Tests the test plan constructor
	 */
	@Test
	public void testTestPlanConstructor() {
		// Test a valid construction
		assertDoesNotThrow(() -> new TestPlan("WolfScheduler"), "Should not throw exception");

		Exception exception = assertThrows(IllegalArgumentException.class, () -> new TestPlan(null));
		assertEquals("Invalid name.", exception.getMessage(), "Testing null test plan name message");

		exception = assertThrows(IllegalArgumentException.class, () -> new TestPlan(""));
		assertEquals("Invalid name.", exception.getMessage(), "Testing empty test plan name message");

		exception = assertThrows(IllegalArgumentException.class, () -> new TestPlan("Failing Tests"));
		assertEquals("Invalid name.", exception.getMessage(), "Testing empty test plan name message");
	}

	/**
	 * Tests the getTestCasesAsArray method
	 */
	@Test
	public void testGetTestCasesAsArray() {
		TestPlan plan = new TestPlan("WolfScheduler");
		TestCase caseOne = new TestCase("test0", "Equivalence", "desc", "results");
		caseOne.addTestResult(true, "results");
		plan.addTestCase(caseOne);
		String[][] testArr = plan.getTestCasesAsArray();
		String id = testArr[0][0];
		String type = testArr[0][1];
		String status = testArr[0][2];
		assertEquals("test0", id);
		assertEquals("Equivalence", type);
		assertEquals("PASS", status);
	}
	

}
