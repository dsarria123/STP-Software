package edu.ncsu.csc216.stp.model.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.stp.model.test_plans.TestPlan;
import edu.ncsu.csc216.stp.model.tests.TestCase;

/**
 * Tests the TestPlanManager class
 */
public class TestPlanManagerTest {

	/** Manager instance */
	private TestPlanManager manager;

	/** Test plan */
	private TestPlan testPlan;

	/**
	 * Set up for manager
	 * @throws Exception if manager has trouble constructing
	 */
	@Before
	public void setUp() throws Exception {
		manager = new TestPlanManager();
	}

	/**
	 * testGetandSetCurrentTestPlan
	 */
	@Test
	public void testGetandSetCurrentTestPlan() {
		manager.addTestPlan("Test Plan 1");
		manager.setCurrentTestPlan("Test Plan 1");
		assertEquals("Test Plan 1", manager.getCurrentTestPlan().getTestPlanName());
		manager.setCurrentTestPlan("Non-existent Test Plan");
		assertEquals("Failing Tests", manager.getCurrentTestPlan().getTestPlanName());
	}

	/**
	 * tests the isChanged method
	 */
	@Test
	public void testIsChanged() {
		assertFalse(manager.isChanged());
		manager.addTestPlan("Test Plan 1");
		assertTrue(manager.isChanged());
		manager.saveTestPlans(new File("test-plans3"));
		assertFalse(manager.isChanged());
	}

	/**
	 * tests the clearTestPlans method
	 */
	@Test
	public void testClearTestPlans() {
		// Test clearing test plans
		manager.addTestPlan("Test Plan 1");
		assertEquals(2, manager.getTestPlanNames().length);
		manager.clearTestPlans();
		assertEquals(1, manager.getTestPlanNames().length);
	}
	
	
	/**
	 * Tests the getTestPlanNames method
	 */
	@Test
	public void testGetTestPlanNames() {

		// Add a new test plan
		manager.addTestPlan("Test Plan 1");
		manager.addTestPlan("Test Plan 2");
		manager.addTestPlan("Test Plan 3");

		// Verify that both "Failing Tests" and the new test plan are present in the
		// names
		String[] updatedTestPlanNames = manager.getTestPlanNames();
		assertEquals(4, updatedTestPlanNames.length);
		assertEquals("Failing Tests", updatedTestPlanNames[0]);
		assertEquals("Test Plan 1", updatedTestPlanNames[1]);
		assertEquals("Test Plan 2", updatedTestPlanNames[2]);
		assertEquals("Test Plan 3", updatedTestPlanNames[3]);
	}

	

	/**
	 * Tests the addTestRessult method
	 */
	@Test
	public void testAddTestResult() {

		manager.addTestPlan("Test Plan 1"); // Add a test plan to use
		testPlan = (TestPlan) manager.getCurrentTestPlan();

		// Create a test case with a passing test result
		TestCase testCase = new TestCase("TC001", "Functional", "Passing Test", "Expected result: Pass");
		
		testCase.addTestResult(true, "Actual result: Pass");
		testPlan.addTestCase(testCase);
		// Add a passing test result to the existing test case
		manager.addTestResult(0, true, "New passing result");
		
		assertTrue(testPlan.getTestCase(0).isTestCasePassing());

	}

	/**
	 * Tests the removeTestPlan method
	 */
	@Test
	public void testRemoveTestPlan() {

		manager.addTestPlan("Test Plan 1"); // Add a test plan to use
		// Attempt to remove the current test plan (should be successful)
		manager.removeTestPlan();

		// Ensure the current test plan is the failing test list
		assertEquals("Failing Tests", manager.getCurrentTestPlan().getTestPlanName());

		// Ensure isChanged is updated to true
		assertTrue(manager.isChanged());

		// Attempt to remove the current test plan again (should throw an exception)
		try {
			manager.removeTestPlan();
			fail("Expected IllegalArgumentException not thrown.");
		} catch (IllegalArgumentException e) {
			assertEquals("The Failing Tests list may not be deleted.", e.getMessage());
		}
	}

	/**
	 * tests the editTestPlan method
	 */
	@Test
	public void testEditTestPlan() {

		manager.addTestPlan("Test Plan 1");
		// Attempt to edit the current test plan (should be successful)
		manager.editTestPlan("New Test Plan Name");

		// Ensure the current test plan's name is updated
		assertEquals("New Test Plan Name", manager.getCurrentTestPlan().getTestPlanName());

		// Ensure isChanged is updated to true
		assertTrue(manager.isChanged());

		// Attempt to edit the current test plan to have the same name as "Failing
		// Tests" (should throw an exception)
		try {
			manager.editTestPlan("Failing Tests");
			fail("Expected IllegalArgumentException not thrown.");
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid name.", e.getMessage());
		}

	}

	/**
	 * tests the loadTestPlans method
	 */
	@Test
	public void testLoadTestPlans() {
		// Create a temporary test plan file
		File testPlanFile = new File("test-plans3");

		// Load test plans from the temporary file
		manager.loadTestPlans(testPlanFile);

		// Verify that the loaded test plans are correctly added to the manager
		// You can use assertions to check the state of the manager after loading

		// For example, check the size of the test plans
		assertEquals(2, manager.getTestPlanNames().length);

	}
	
	/**
	 * Tests the add test case method
	 */
	@Test
	public void testAddTestCase() {
		manager.addTestPlan("Test Plan");
		manager.addTestCase(new TestCase("id", "description", "type", "results"));
		TestPlan plan = (TestPlan) manager.getCurrentTestPlan();
		assertEquals("id", plan.getTestCase(0).getTestCaseId());

	}

}
