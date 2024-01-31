package edu.ncsu.csc216.stp.model.tests;

/**
 * This class handles the TestResult for test cases. Determines if results are
 * passing and handles the actual results for test results.
 * 
 * @author Bryan Phillips
 * @author Diego Sarria
 */
public class TestResult {

	/** Passing string */
	public static final String PASS = "PASS";

	/** Failing string */
	public static final String FAIL = "FAIL";

	/** boolean variable to determine passing or failing */
	private boolean passing;

	/** Actual results */
	private String actualResults;

	/**
	 * Test result constructor
	 * 
	 * @param passing       if test result is passing or not
	 * @param actualResults actualResults of the test result
	 * 
	 * @throws IllegalArgumentException if actual results is null or empty
	 */
	public TestResult(boolean passing, String actualResults) {
		setPassing(passing);
		setActualResults(actualResults);
	}

	/**
	 * Returns string of actual results
	 * 
	 * @return actual results in a string
	 */
	public String getActualResults() {
		return actualResults;
	}

	/**
	 * Sets the actual results to the string given
	 * 
	 * @param actualResults actual results
	 * @throws IllegalArgumentException if actual results is null or empty
	 */
	private void setActualResults(String actualResults) {
		if ("".equals(actualResults) || actualResults == null) {
			throw new IllegalArgumentException("Invalid test results.");
		}
		this.actualResults = actualResults;
	}

	/**
	 * Checks to see if passing
	 * 
	 * @return true if test result is passing or false if failing
	 */
	public boolean isPassing() {
		return passing;
	}

	/**
	 * Sets to whether or not passing
	 * 
	 * @param pass passing or not
	 */
	private void setPassing(boolean pass) {
		this.passing = pass;
	}

	/**
	 * toString method to return string in format
	 * 
	 * @return String format
	 */
	public String toString() {
		if (passing) {
			return PASS + ": " + actualResults;
		}
		return FAIL + ": " + actualResults;
	}
}
