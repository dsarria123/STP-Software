package edu.ncsu.csc216.stp.model.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.ncsu.csc216.stp.model.test_plans.TestPlan;
import edu.ncsu.csc216.stp.model.tests.TestCase;
import edu.ncsu.csc216.stp.model.util.ISortedList;
import edu.ncsu.csc216.stp.model.util.SortedList;

/**
 * Processes a file containing test plans with zero to many test cases each with
 * zero to many test results.
 * 
 * @author Bryan Phillips
 * @author Diego Sarria
 */
public class TestPlanReader {

	/**
	 * Uses the given file to read from and processes its contents. With help from
	 * the private methods, this method processes through each test plan in the file
	 * as well as the test plan's test cases. If a file doesn't exist then it will
	 * throw an IllegalArgumentException
	 * 
	 * @param filename name of the file to be read from
	 * @return ISortedList Sorted list of test plans
	 * @throws IllegalArgumentException if file is unable to be used or loaded
	 */
	public static ISortedList<TestPlan> readTestPlansFile(File filename) {
		try {
			Scanner fileReader = new Scanner(new FileInputStream(filename));
			ISortedList<TestPlan> testPlans = new SortedList<TestPlan>();

			String list = "";
			while (fileReader.hasNextLine()) {
				list = list + fileReader.nextLine().trim() + "\n";
			}
			if (!list.startsWith("!")) {
				throw new IllegalArgumentException("Unable to load file.");
			}
			Scanner planList = new Scanner(list);
			planList.useDelimiter("\\r?\\n?[!]");

			while (planList.hasNext()) {
				TestPlan plan = processTestPlan(planList.next().trim());
				testPlans.add(plan);
			}

			fileReader.close();
			planList.close();
			return testPlans;
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to load file.");
		}
	}

	/**
	 * Processes the test plan from the plan contents
	 * 
	 * @param planContents plan contents
	 * @return TestPlan test plan
	 */
	private static TestPlan processTestPlan(String planContents) {
		Scanner testPlanInfo = new Scanner(planContents);
		String planName = testPlanInfo.nextLine().trim();
		TestPlan plan = new TestPlan(planName);
		testPlanInfo.useDelimiter("\\r?\\n?[#]");
		while (testPlanInfo.hasNext()) {
			TestCase testCase = processTest(testPlanInfo.next().trim());
			try {
				plan.addTestCase(testCase);
			} catch (Exception e) {
				continue;
			}

		}
		testPlanInfo.close();

		return plan;
	}

	/**
	 * Processes the test plan from the testPlan and case contents.
	 * 
	 * @param caseContents case contents
	 * @return TestCase test case
	 * @throws IllegalArgumentException if any errors occur
	 */
	private static TestCase processTest(String caseContents) {
		Scanner testCase = new Scanner(caseContents);
		String testCaseIdType = testCase.nextLine().trim();
		Scanner testCaseIdTypeScan = new Scanner(testCaseIdType);
		testCaseIdTypeScan.useDelimiter(",");
		String id = testCaseIdTypeScan.next().trim();
		if (!testCaseIdTypeScan.hasNext()) {
			testCase.close();
			testCaseIdTypeScan.close();
			return null;
		}
		String type = testCaseIdTypeScan.next().trim();
		String description = "";
		String expResults = "";
		String accResults = "";
		String results = "";
		try {
			testCase.useDelimiter("\\r?\\n?[*]");
			description = testCase.next().trim();
			if ("".equals(description)) {
				testCaseIdTypeScan.close();
				testCase.close();
				return null;
			}
			if (!testCase.hasNext()) {
				testCaseIdTypeScan.close();
				testCase.close();
				return null;
			}
			results = testCase.next().trim();
			Scanner expActResults = new Scanner(results);
			expActResults.useDelimiter("\\r?\\n?[-]");
			expResults = expActResults.next().trim();
			if (expResults.startsWith("PASS")) {
				expActResults.close();
				return null;
			} else if (expResults.startsWith("FAIL")) {
				expActResults.close();
				return null;
			}
			TestCase newCase = new TestCase(id, type, description, expResults);
			while (expActResults.hasNext()) {
				accResults = expActResults.next().trim();
				boolean passFail = false;
				if (accResults.startsWith("PASS")) {
					passFail = true;
				} else if (accResults.startsWith("FAIL")) {
					passFail = false;
				} else {
					expActResults.close();
					return null;
				}
				if (accResults.length() <= 5) {
					expActResults.close();
					return null;
				}
				String actualResults = accResults.substring(6, accResults.length());
				newCase.addTestResult(passFail, actualResults);
			}
			testCaseIdTypeScan.close();
			expActResults.close();
			testCase.close();
			return newCase;
		} catch (Exception e) {
			testCase.close();
			testCaseIdTypeScan.close();
			throw new IllegalArgumentException("");

		}
	}

}