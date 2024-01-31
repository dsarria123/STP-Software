package edu.ncsu.csc216.stp.model.test_plans;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

import edu.ncsu.csc216.stp.model.tests.TestCase;

/**
 * Failing test list test
 */
public class FailingTestListTest {

	/**
	 * Tests the failing test list constructor
	 */
	@Test
	public void testFailingTestListConstructor() {
		FailingTestList a = new FailingTestList();
		// Test a valid construction
		assertDoesNotThrow(() -> new FailingTestList(), "Should not throw exception");
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> a.setTestPlanName("woop"));
		
		assertEquals("The Failing Tests list cannot be edited.", exception.getMessage(), "Testing setting a different name for failingtestlist message");
	}

	/**
	 * Tests the addTestCase method
	 */ 
	@Test
	public void testAddTestCase() {
		FailingTestList a = new FailingTestList();
		TestCase caseOne = new TestCase("id", "type", "desc", "results");
		caseOne.addTestResult(true, "results");
		TestCase caseTwo = new TestCase("id2", "type2", "desc2", "results2");
		caseTwo.addTestResult(false, "results2");
		
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> a.addTestCase(caseOne));
		assertEquals("Cannot add passing test case.", exception.getMessage(), "Testing adding passing test message");
		
		assertDoesNotThrow(() -> a.addTestCase(caseTwo), "Should not throw exception");
	}
	
	/**
	 * Tests the addTestCase method in another way
	 */ 
	@Test
	public void testAddTestCase2() {
		FailingTestList a = new FailingTestList();
		TestCase case1 = new TestCase("ID1", "type1", "description1", "expected1");
		TestCase case2 = new TestCase("ID2", "type2", "description2", "expected2");
		TestCase case3 = new TestCase("ID3", "type3", "description3", "expected3");
		TestCase case4 = new TestCase("ID4", "type4", "description4", "expected4");
		a.addTestCase(case1);
		a.addTestCase(case2);
		a.addTestCase(case3);
		a.addTestCase(case4);
		assertEquals(4, a.getNumberOfFailingTests());
		
		
		
	
	}
	
	/**
	 * Tests the getTestCasesAsArray method
	 */
	@Test
	public void testGetTestCasesAsArray() {
		FailingTestList a = new FailingTestList();
		TestCase caseOne = new TestCase("test0", "Equivalence", "desc", "results");
		caseOne.addTestResult(false, "results");
		a.addTestCase(caseOne);
		String[][] testArr = a.getTestCasesAsArray();
		String id = testArr[0][0];
		String type = testArr[0][1];
		String status = testArr[0][2];
		assertEquals("test0", id);
		assertEquals("Equivalence", type);
		assertEquals("", status);
	}
	
	/**
	 * Test clear Test
	 */
	@Test
	public void testClearTEsts() {
		FailingTestList failingTestList = new FailingTestList();
		TestCase test1 = new TestCase("ID1", "Type1", "Description1", "Expected1");
		TestCase test2 = new TestCase("ID2", "Type2", "Description2", "Expected2");
		TestCase test3 = new TestCase("ID3", "Type3", "Description3", "Expected3");
		 failingTestList.addTestCase(test1);
	      failingTestList.addTestCase(test2);
	      failingTestList.addTestCase(test3);
		failingTestList.clearTests();
		  
        
        
        assertEquals(0, failingTestList.getTestCases().size());
	}
	
	
}
