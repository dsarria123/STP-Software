package edu.ncsu.csc216.stp.model.test_plans;

import edu.ncsu.csc216.stp.model.tests.TestCase;

/**
 * Extends Abstract Test Plan to hold failing test cases for all projects
 * currently available in the system.
 * 
 * @author Bryan Phillips
 * @author Diego Sarria
 */
public class FailingTestList extends AbstractTestPlan {

	/** Constant holding the name of the Failing Tests list. */
	public static final String FAILING_TEST_LIST_NAME = "Failing Tests";

	/**
	 * Constructs the FailingTestList with the expected name.
	 */
	public FailingTestList() {
		super(FAILING_TEST_LIST_NAME);
		setTestPlanName(FAILING_TEST_LIST_NAME);
	}

	
	/**
	 * Adds a test case to the FailingTestList
	 * 
	 * @param t test case to be added
	 * 
	 * @throws IllegalArgumentException if given test case is not passing
	 */
	@Override
	public void addTestCase(TestCase t) {
		if (t.isTestCasePassing()) {
			throw new IllegalArgumentException("Cannot add passing test case.");
		}
		super.addTestCase(t);
	}



	/**
	 * Sets the test plan name to the constant value of "Failing Tests". If the
	 * given parameter is not the constant then an IAE is thrown.
	 * 
	 * @param testPlanName name to set the test plan name to
	 * 
	 * @throws IllegalArgumentException if the testPlanName doesn't match the constant
	 */
	@Override
	public void setTestPlanName(String testPlanName) {
		
	    if (testPlanName == null || "".equals(testPlanName)) {
	        throw new IllegalArgumentException("Invalid name.");
	    }
	   
	    if (!testPlanName.equalsIgnoreCase(FAILING_TEST_LIST_NAME)) {
	        throw new IllegalArgumentException("The Failing Tests list cannot be edited.");
	    }
	    super.setTestPlanName(FAILING_TEST_LIST_NAME);

	}

	/**
	 * Returns a 2D String array where the first column is the test case id, the
	 * second column is the test type, and the third column is the test plan's name
	 */
	@Override
	public String[][] getTestCasesAsArray() {
		String planName = "";
		String[][] caseArr = new String[this.getTestCases().size()][3];
		for (int i = 0; i < this.getTestCases().size(); i++) {
			if (this.getTestCase(i).getTestPlan() == null) {
				planName = "";
			} else {
				planName = this.getTestCase(i).getTestPlan().getTestPlanName();
			}
			caseArr[i][0] = this.getTestCase(i).getTestCaseId();
			caseArr[i][1] = this.getTestCase(i).getTestType();
			caseArr[i][2] = planName;

		}
		return caseArr;
	}

	/**
	 * Clears the FailingTestList of all TestCases.
	 */
	public void clearTests() {
	    for (int i = this.getTestCases().size() - 1; i >= 0; i--) {
	        this.removeTestCase(i);
	    }
	}

}
