package edu.ncsu.csc216.stp.model.test_plans;

import edu.ncsu.csc216.stp.model.tests.TestCase;

/**
 * This class creates the TestPlan object and provides functionality to add test
 * cases. Extends AbstractTestPlan to hold test cases that belong to a name
 * project.
 * 
 * @author Bryan Phillips
 * @author Diego Sarria
 */
public class TestPlan extends AbstractTestPlan implements Comparable<TestPlan> {

	/**
	 * Constructs the TestPlan with the given name.
	 * 
	 * @param testPlanName test plan name
	 * 
	 * @throws IllegalArgumentException if test plan name is the same as the failing
	 *                                  test list name
	 */
	public TestPlan(String testPlanName) {

		super(testPlanName);
		if (testPlanName.equalsIgnoreCase(FailingTestList.FAILING_TEST_LIST_NAME)) {
			throw new IllegalArgumentException("Invalid name.");
		}

	}

	/**
	 * Returns a 2D String array where the first column is the test case id, the
	 * second column is the test type, and the third column is the status (PASS or
	 * FAIL).
	 */
	@Override
	public String[][] getTestCasesAsArray() {
		String[][] caseArr = new String[this.getTestCases().size()][3];
		for (int i = 0; i < this.getTestCases().size(); i++) {
			caseArr[i][0] = this.getTestCase(i).getTestCaseId();
			caseArr[i][1] = this.getTestCase(i).getTestType();
			caseArr[i][2] = this.getTestCase(i).getStatus();
		}
		return caseArr;
	}

	/**
	 * Adds a test case to the current test plan
	 * 
	 * @param t test case to be added
	 */
	@Override
	public void addTestCase(TestCase t) {
		super.addTestCase(t);
		t.setTestPlan(this);
	}

	/**
	 * Compares the names of the TestPlans. This comparison is case insensitive.
	 * 
	 * @param plan Test plan
	 * @return int index
	 */
	public int compareTo(TestPlan plan) {
		return this.getTestPlanName().compareToIgnoreCase(plan.getTestPlanName());
	}

}
