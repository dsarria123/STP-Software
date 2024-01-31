package edu.ncsu.csc216.stp.model.manager;

import java.io.File;

import edu.ncsu.csc216.stp.model.io.TestPlanReader;
import edu.ncsu.csc216.stp.model.io.TestPlanWriter;
import edu.ncsu.csc216.stp.model.test_plans.AbstractTestPlan;
import edu.ncsu.csc216.stp.model.test_plans.FailingTestList;
import edu.ncsu.csc216.stp.model.test_plans.TestPlan;
import edu.ncsu.csc216.stp.model.tests.TestCase;
import edu.ncsu.csc216.stp.model.util.ISortedList;
import edu.ncsu.csc216.stp.model.util.SortedList;

/**
 * Contains a list of test plans, the FailingTestList, and operations to
 * manipulate these lists.
 * 
 * @author Bryan Phillips
 * @author Diego Sarria
 */
public class TestPlanManager {

	/** Boolean variable used for determining if a test plan has changed */
	private boolean isChanged;

	/** List of Sorted Test Plans */
	private ISortedList<TestPlan> testPlans;

	/** List of failing tests */
	private FailingTestList failingTestList;

	/** Current test plan */
	private AbstractTestPlan currentTestPlan;

	/** Failing test list's name */
	private static final String FAILING_TESTS_LIST_NAME = "Failing Tests";

	/**
	 * The testPlans field is constructed as a SortedList and the failingTestList is
	 * constructed and is set as the currentTestPlan. isChanged is initialized to
	 * false. The clearTestPlans() method can be helpful for completing these tasks.
	 */
	public TestPlanManager() {

		// Initialize testPlans as a SortedList
		testPlans = new SortedList<TestPlan>();

		// Create the failingTestList and set it as the currentTestPlan
		failingTestList = new FailingTestList();
		currentTestPlan = failingTestList;

		// Initialize isChanged to false
		isChanged = false;
	}

	/**
	 * Loads test plans from the file
	 * 
	 * @param testPlanFile file to load
	 */
	public void loadTestPlans(File testPlanFile) {
		clearTestPlans();
		ISortedList<TestPlan> loadedTestPlans = TestPlanReader.readTestPlansFile(testPlanFile);

		for (int i = 0; i < loadedTestPlans.size(); i++) {
			TestPlan loadedTestPlan = loadedTestPlans.get(i);
			testPlans.add(loadedTestPlan);
			isChanged = true;
		}

		// Call getFailingTests to update the failingTestList
		getFailingTests();

		// Set the currentTestPlan to the Failing Tests list
		setCurrentTestPlan(FAILING_TESTS_LIST_NAME);
	}

	/**
	 * Saves the test plans from the file
	 * 
	 * @param testPlanFile file to save
	 */
	public void saveTestPlans(File testPlanFile) {
		TestPlanWriter.writeTestPlanFile(testPlanFile, testPlans);
		isChanged = false;
	}

	/**
	 * Checks to see if test plans are changed
	 * 
	 * @return true if test plans are changed and false if not
	 */
	public boolean isChanged() {
		return isChanged;
	}

	/**
	 * Adds a test plan to the list of test plans and updates the current test plan
	 * to the new test plan. If the test plan's name is "Failing Tests" or a
	 * duplicate then an IllegalArgumentExceptionis thrown.
	 * 
	 * 
	 * @param testPlanName test plan to be added
	 * 
	 * @throws IllegalArgumentException for invalid names
	 */
	public void addTestPlan(String testPlanName) {
		// Check if the new TestPlan's name is the same as FAILING_TESTS_LIST_NAME
		// (case-insensitive)
		if (testPlanName.equalsIgnoreCase(FAILING_TESTS_LIST_NAME)) {
			throw new IllegalArgumentException("Invalid name.");
		}

		// Check for duplicates (case-insensitive)
		for (int i = 0; i < testPlans.size(); i++) {
			TestPlan existingTestPlan = testPlans.get(i);
			if (existingTestPlan.getTestPlanName().equalsIgnoreCase(testPlanName)) {
				throw new IllegalArgumentException("Invalid name.");
			}
		}

		// Create a new TestPlan and add it to the list of test plans
		TestPlan newTestPlan = new TestPlan(testPlanName);
		testPlans.add(newTestPlan);

		// Update the current test plan to the new test plan
		setCurrentTestPlan(testPlanName);

		// Update isChanged to true
		isChanged = true;

	}

	/**
	 * Returns a list of test plan names. The Failing Tests list is always listed
	 * first.
	 * 
	 * @return a string array of test plan names
	 */
	public String[] getTestPlanNames() {
		// Create an array to store the test plan names
		String[] testPlanNames = new String[testPlans.size() + 1];

		// Add "Failing Tests" as the first element
		testPlanNames[0] = FAILING_TESTS_LIST_NAME;

		// Add the remaining test plan names
		for (int i = 0; i < testPlans.size(); i++) {
			testPlanNames[i + 1] = testPlans.get(i).getTestPlanName();
		}

		return testPlanNames;
	}

	/**
	 * Returns the failing tests
	 */
	private void getFailingTests() {
		// Clear the FailingTestList first
		failingTestList.clearTests();

		// Iterate through all the TestPlans
		for (int i = 0; i < testPlans.size(); i++) {
			TestPlan testPlan = testPlans.get(i);

			// Iterate through all the TestCases in the TestPlan
			for (int j = 0; j < testPlan.getTestCases().size(); j++) {
				TestCase testCase = testPlan.getTestCases().get(j);

				// Check if the TestCase is failing and add it to the FailingTestList
				if (!testCase.isTestCasePassing()) {
					failingTestList.addTestCase(testCase);
				}
			}
		}
	}

	/**
	 * Sets the currentTestPlan to the AbstractTestPlan with the given name. If a
	 * TestPlan with that name is not found, then the currentTestPlan is set to the
	 * failingTestList
	 * 
	 * @param testPlanName name of test
	 */
	public void setCurrentTestPlan(String testPlanName) {
		for (int i = 0; i < testPlans.size(); i++) {
			AbstractTestPlan existingTestPlan = testPlans.get(i);
			if (existingTestPlan.getTestPlanName().equalsIgnoreCase(testPlanName)) {
				currentTestPlan = existingTestPlan;
				return;
			}
		}

		// If the matching test plan was not found, update the failingTestList and set
		// it as current
		currentTestPlan = failingTestList;
	}

	/**
	 * Gets the current test plan
	 * 
	 * @return AbstractTestPlan
	 */
	public AbstractTestPlan getCurrentTestPlan() {
		return currentTestPlan;
	}

	/**
	 * Edits the current test plan
	 * 
	 * @param testPlanName name of test plan
	 * 
	 * @throws IllegalArgumentException for invalid names or if the failing test
	 *                                  list is trying to be edited
	 */
	public void editTestPlan(String testPlanName) {
		// Check if the currentTestPlan is a FailingTestList
		if (currentTestPlan instanceof FailingTestList) {
			throw new IllegalArgumentException("The Failing Tests list may not be edited.");
		} else {
			// Check if the new name matches "Failing Tests" (case insensitive)
			if (testPlanName.equalsIgnoreCase(FAILING_TESTS_LIST_NAME)) {
				throw new IllegalArgumentException("Invalid name.");
			}

			if (currentTestPlan.getTestPlanName().equalsIgnoreCase(testPlanName)) {
				throw new IllegalArgumentException("Invalid name.");
			}

			// Check if the new name is a duplicate of another TestPlan's name (case
			// insensitive)
			for (int i = 0; i < testPlans.size(); i++) {
				AbstractTestPlan existingTestPlan = testPlans.get(i);
				if (existingTestPlan != currentTestPlan
						&& existingTestPlan.getTestPlanName().equalsIgnoreCase(testPlanName)) {
					throw new IllegalArgumentException("Invalid name.");
				}
			}

			// Update the name of the currentTestPlan
			currentTestPlan.setTestPlanName(testPlanName);
			// Update the isChanged flag to indicate changes were made
			isChanged = true;
		}
	}

	/**
	 * Removes the current test plan and then set to the failing test list
	 * 
	 * @throws IllegalArgumentException if the current test plan is the failing test list
	 */
	public void removeTestPlan() {
		// Check if the currentTestPlan is an FailingTestList
		if (currentTestPlan == failingTestList) {
			throw new IllegalArgumentException("The Failing Tests list may not be deleted.");
		}

		// Remove the currentTestPlan
		for (int i = 0; i < testPlans.size(); i++) {
			if (testPlans.get(i) == currentTestPlan) {
				testPlans.remove(i);
			}

		}
		// Set the currentTestPlan to the failingTestList
		currentTestPlan = failingTestList;

		// Update isChanged to true
		isChanged = true;
	}

	/**
	 * A TestCase can only be added directly to a TestPlan. If the currentTestPlan
	 * is not a TestPlan do nothing with the TestCase. If the currentTestPlan is a
	 * TestPlan, then add the test case and check if the TestCase is failing. If so,
	 * then you can update the failingTestList. This is a place where the helper
	 * method getFailingTests() can be helpful. isChanged is updated to true.
	 * 
	 * @param t TestCase test case to be added
	 */
	public void addTestCase(TestCase t) {

		// Check if the currentTestPlan is a TestPlan
		if (currentTestPlan instanceof TestPlan) {

			// Add the test case to the currentTestPlan
			currentTestPlan.addTestCase(t);

			// Check if the added test case is failing
			if (!t.isTestCasePassing()) {
				// Update the Failing Test List with the failing test case
				failingTestList.addTestCase(t);
				getFailingTests();
			}

			// Update the isChanged flag to indicate changes were made
			isChanged = true;
		}
	}

	/**
	 * Adds the test result to the test case at the given index in the current test
	 * plan. If the tests are failing, then the failing test list is updated.
	 * 
	 * @param idx          index to be added to
	 * @param passing      passing status of test result
	 * @param actualResult actual results
	 */
	public void addTestResult(int idx, boolean passing, String actualResult) {

		TestCase testCase = currentTestPlan.getTestCase(idx);
		// Add the test result to the test case
		testCase.addTestResult(passing, actualResult);
		// Check if the test case is now failing
		if (!passing) {
			// Update the Failing Test List with the failing test case
			getFailingTests();
			failingTestList.addTestCase(testCase);
		}

		// Update the isChanged flag to indicate changes were made
		isChanged = true;

	}

	/**
	 * Clears out the TestPlanManager by setting testPlans to an empty SortedList,
	 * failingTestList to an empty FailingTestList(), currentTestPlan to the
	 * failingTestList, and isChanged to false.
	 */
	public void clearTestPlans() {
		testPlans = new SortedList<TestPlan>();

		// Create an empty FailingTestList for failingTestList
		failingTestList = new FailingTestList();

		// Set currentTestPlan to failingTestList
		currentTestPlan = failingTestList;

		// Set isChanged to false
		isChanged = false;
	}

}
