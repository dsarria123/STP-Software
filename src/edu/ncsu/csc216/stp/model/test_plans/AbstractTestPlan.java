package edu.ncsu.csc216.stp.model.test_plans;

import edu.ncsu.csc216.stp.model.tests.TestCase;
import edu.ncsu.csc216.stp.model.util.ISwapList;
import edu.ncsu.csc216.stp.model.util.SwapList;

/**
 * Abstract test plan class that provides basic functionality for test plan
 * objects. 
 * 
 * @author Bryan Phillips
 * @author Diego Sarria
 */
public abstract class AbstractTestPlan {

	/** Test Plan's Name */
	private String testPlanName;

	/** Swap list of test cases */
	private SwapList<TestCase> testCases;

	/**
	 * Sets the fields from the parameters and constructs a SwapList for the
	 * TestCases. An IAE is thrown with the message Invalid name. if the
	 * testPlanName is null or empty string.
	 * 
	 * @param testPlanName test plan's name
	 * @throws IllegalArgumentException if testPlanName is invalid
	 */
	public AbstractTestPlan(String testPlanName) {

		setTestPlanName(testPlanName);
		testCases = new SwapList<TestCase>();
	}

	/**
	 * Sets the test plan's name. If the test plan name is null or empty then an IAE
	 * is thrown.
	 * 
	 * @param testPlanName test plan's name
	 * @throws IllegalArgumentException if testPlanName is invalid
	 */
	public void setTestPlanName(String testPlanName) {
		if ("".equals(testPlanName) || testPlanName == null) {
			throw new IllegalArgumentException("Invalid name.");
		}
		this.testPlanName = testPlanName;
	}

	/**
	 * Returns current test plan name
	 * 
	 * @return test plan's name
	 */
	public String getTestPlanName() {
		return testPlanName;
	}

	/**
	 * Gets test cases from ISwapList
	 * 
	 * @return SwapList of test cases
	 */
	public ISwapList<TestCase> getTestCases() {
		return testCases;
	}

	/**
	 * Uses the given test case to add to the end of the testCases list
	 * 
	 * @param t test case to be added
	 */
	public void addTestCase(TestCase t) {
		testCases.add(t);
	}

	/**
	 * Removes the TestCase from the list of test cases and returns the removed test
	 * case. Note that any exceptions from the list should be thrown out of the
	 * method
	 * 
	 * @param idx idx of the test case that will be removed
	 * @return TestCase test case that got removed
	 */
	public TestCase removeTestCase(int idx) {
		TestCase testCase = testCases.get(idx);
		testCases.remove(idx);
		return testCase;
	}

	/**
	 * Gets test case at the given index.
	 * 
	 * @param idx index
	 * @return TestCase test case
	 */
	public TestCase getTestCase(int idx) {
		return testCases.get(idx);
	}

	/**
	 * Returns the number of failing test cases.
	 * 
	 * @return number of failing tests
	 */
	public int getNumberOfFailingTests() {
		int count = 0;
		for (int i = 0; i < testCases.size(); i++) {
			if (!(testCases.get(i).isTestCasePassing())) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Adds a test result to the test case at the given index
	 * 
	 * @param idx           index of test case
	 * @param passing       if a test is passing or failing
	 * @param actualResults actual results of test
	 */
	public void addTestResult(int idx, boolean passing, String actualResults) {
		TestCase caseAdd = testCases.get(idx);
		caseAdd.addTestResult(passing, actualResults);
	}

	/**
	 * An abstract method that returns a 2D String array.
	 * 
	 * @return test cases as an array
	 */
	public abstract String[][] getTestCasesAsArray();

	/**
	 * Hashes code for testPlanName
	 * 
	 * @return hash code for testPlanname
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((testPlanName == null) ? 0 : testPlanName.hashCode());
		return result;
	}

	
	/**
	 * Compares on the testPlanName, case insensitive
	 * 
	 * @param obj to be compared
	 * 
	 * @return true if objects equal each other or false if not
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractTestPlan other = (AbstractTestPlan) obj;
		if (testPlanName == null) {
			if (other.testPlanName != null)
				return false;
		} else if (!testPlanName.equalsIgnoreCase(other.testPlanName))
			return false;
		return true;
	}

	

	

	

}
