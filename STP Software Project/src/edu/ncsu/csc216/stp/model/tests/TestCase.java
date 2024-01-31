package edu.ncsu.csc216.stp.model.tests;

import edu.ncsu.csc216.stp.model.test_plans.TestPlan;
import edu.ncsu.csc216.stp.model.util.Log;

/**
 * This class creates the TestCase object, and takes in and sets all the
 * appropriate information to the object. Handles all the information
 * manipulation for Test Cases.
 * 
 * @author Bryan Phillips
 * @author Diego Sarria
 */
public class TestCase {

	/** Test case's id */
	private String testCaseId;

	/** Test case's type */
	private String testType;

	/** Test case's description */
	private String testDescription;

	/** Test case's expected results */
	private String expectedResults;

	/** Test plan */
	private TestPlan testPlan;

	/** Log of test results */
	private Log<TestResult> testResults;

	/**
	 * Constructor for test case
	 * 
	 * @param testCaseId      test case's id
	 * @param testType        test case's type
	 * @param testDescription test case's description
	 * @param expectedResults test case's expected results
	 */
	public TestCase(String testCaseId, String testType, String testDescription, String expectedResults) {
		setTestCaseId(testCaseId);
		setTestType(testType);
		setTestDescription(testDescription);
		setExpectedResults(expectedResults);
		testResults = new Log<TestResult>();
		testPlan = null;

	}

	/**
	 * Returns test case id
	 * 
	 * @return test case id
	 */
	public String getTestCaseId() {
		return testCaseId;
	}

	/**
	 * Sets the test case id
	 * 
	 * @param id test case id
	 * 
	 * @throws IllegalArgumentException if id is empty or null
	 */
	private void setTestCaseId(String id) {
		if ("".equals(id) || id == null) {
			throw new IllegalArgumentException("Invalid test information.");
		}
		this.testCaseId = id;
	}

	/**
	 * Returns the test type
	 * 
	 * @return test case's type
	 */
	public String getTestType() {
		return testType;
	}

	/**
	 * Sets the test type
	 * 
	 * @param type test case's type
	 * 
	 * @throws IllegalArgumentException if type is empty or null
	 */
	private void setTestType(String type) {
		if ("".equals(type) || type == null) {
			throw new IllegalArgumentException("Invalid test information.");
		}
		this.testType = type;
	}

	/**
	 * Returns the test case's description
	 * 
	 * @return test case's description
	 */
	public String getTestDescription() {
		return testDescription;
	}

	/**
	 * Sets the test description
	 * 
	 * @param description test case's description
	 * 
	 * @throws IllegalArgumentException if description is empty or null
	 */
	private void setTestDescription(String description) {
		if ("".equals(description) || description == null) {
			throw new IllegalArgumentException("Invalid test information.");
		}
		this.testDescription = description;
	}

	/**
	 * Returns the test case's expected results
	 * 
	 * @return test case's expected results
	 */
	public String getExpectedResults() {
		return expectedResults;
	}

	/**
	 * Sets the expected results
	 * 
	 * @param results test case's expected results
	 * 
	 * @throws IllegalArgumentException if results is empty or null
	 */
	private void setExpectedResults(String results) {
		if ("".equals(results) || results == null) {
			throw new IllegalArgumentException("Invalid test information.");
		}
		this.expectedResults = results;
	}

	/**
	 * Adds the test result
	 * 
	 * @param pass    if test is passing or not
	 * @param results results of test case
	 */
	public void addTestResult(boolean pass, String results) {
		TestResult newResult = new TestResult(pass, results);
		testResults.add(newResult);
	}

	/**
	 * Checks if a test case is passing
	 * 
	 * @return true if passing and false if not
	 */
	public boolean isTestCasePassing() {
		if (testResults.size() == 0) {
			return false;
		}
		return testResults.get(testResults.size() - 1).isPassing();
	}

	/**
	 * Gets status of test case
	 * 
	 * @return test case status string
	 */
	public String getStatus() {
		if (isTestCasePassing()) {
			return TestResult.PASS;
		} else {
			return TestResult.FAIL;
		}

	}

	/**
	 * Gets actual results of the log
	 * 
	 * @return actual results log
	 */
	public String getActualResultsLog() {
		String resultString = "";
		for (int i = 0; i < testResults.size(); i++) {
			resultString += "- " + testResults.get(i) + "\n";
		}
		return resultString;
	}

	/**
	 * Sets the test plan to the testPlanName
	 * 
	 * @param testPlan test plan to set
	 * 
	 * @throws IllegalArgumentException if test plan parameter is null
	 */
	public void setTestPlan(TestPlan testPlan) {
		if (testPlan == null) {
			throw new IllegalArgumentException("Invalid test plan.");
		}
		this.testPlan = testPlan;
	}

	/**
	 * Returns the current test plan
	 * 
	 * @return Test plan
	 */
	public TestPlan getTestPlan() {
		return testPlan;
	}

	/**
	 * Returns test case in a formatted String.
	 * 
	 * @return String string
	 */
	public String toString() {
		return "# " + getTestCaseId() + "," + getTestType() + "\n" + "* " + getTestDescription() + "\n" + "* "
				+ getExpectedResults() + "\n" + getActualResultsLog();
	}
}
