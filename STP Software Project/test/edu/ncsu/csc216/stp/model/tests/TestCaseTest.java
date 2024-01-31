package edu.ncsu.csc216.stp.model.tests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

import edu.ncsu.csc216.stp.model.test_plans.TestPlan;



/**
 * Tests the TestCaseTest Class
 * 
 * @author Bryan Phillips
 * @author Diego Sarria 
 */
public class TestCaseTest {

	/**
	 * Tests the test case constructor including the setters and getters
	 */
	@Test
	public void testTestCaseConstructor() {
		// Test a valid construction
		TestCase a = assertDoesNotThrow(() -> new TestCase("test0", "Equivalence Class", "description", "notnull"),
				"Should not throw exception");

		assertEquals("test0", a.getTestCaseId());
		assertEquals("Equivalence Class", a.getTestType());
		assertEquals("description", a.getTestDescription());
		assertEquals("notnull", a.getExpectedResults());

		
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> new TestCase(null, "Equivalence Class", "description", "notnull"));
		assertEquals("Invalid test information.", exception.getMessage(), "Testing null id message");
		
		exception = assertThrows(IllegalArgumentException.class,
				() -> new TestCase("test0", null, "description", "notnull"));
		assertEquals("Invalid test information.", exception.getMessage(), "Testing null type message");
		
		exception = assertThrows(IllegalArgumentException.class,
				() -> new TestCase("test0", "Equivalence Class", null, "notnull"));
		assertEquals("Invalid test information.", exception.getMessage(), "Testing null description message");

		exception = assertThrows(IllegalArgumentException.class,
				() -> new TestCase("test0", "Equivalence Class", "description", null));
		assertEquals("Invalid test information.", exception.getMessage(), "Testing null expected results message");
		
		exception = assertThrows(IllegalArgumentException.class,
				() -> new TestCase("", "Equivalence Class", "description", "notnull"));
		assertEquals("Invalid test information.", exception.getMessage(), "Testing empty id message");
		
		exception = assertThrows(IllegalArgumentException.class,
				() -> new TestCase("test0", "", "description", "notnull"));
		assertEquals("Invalid test information.", exception.getMessage(), "Testing empty type message");
		
		exception = assertThrows(IllegalArgumentException.class,
				() -> new TestCase("test0", "Equivalence Class", "", "notnull"));
		assertEquals("Invalid test information.", exception.getMessage(), "Testing empty description message");
		
		exception = assertThrows(IllegalArgumentException.class,
				() -> new TestCase("test0", "Equivalence Class", "description", ""));
		assertEquals("Invalid test information.", exception.getMessage(), "Testing empty expected results message");
	} 
	
	/** 
	 * Tests the test plan methods in test case
	 */
	@Test
	public void testTestPlan() {
		TestCase a = new TestCase("test0", "Equivalence Class", "description", "notnull");
		a.setTestPlan(new TestPlan("WolfScheduler"));
		assertEquals("WolfScheduler", a.getTestPlan().getTestPlanName());
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> a.setTestPlan(null));
		assertEquals("Invalid test plan.", exception.getMessage(), "Testing null test plan message");
		
	
	}
}
