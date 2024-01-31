package edu.ncsu.csc216.stp.model.io;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.Test;

import edu.ncsu.csc216.stp.model.test_plans.TestPlan;
import edu.ncsu.csc216.stp.model.tests.TestCase;
import edu.ncsu.csc216.stp.model.util.ISortedList;
import edu.ncsu.csc216.stp.model.util.SortedList;

/**
 * test plan writer test file
 */
public class TestPlanWriterTest {
	
	/** Valid test file */
	private final File validTestFile1 = new File("test-files/exp-plans1.txt");
	
	/** Valid test file 2 */
	private final File validTestFile2 = new File("test-files/actual-plans.txt");

	/**
	 * tests write test plan
	 */
	@Test
	public void testWriteTestPlan() {
		ISortedList<TestPlan> testPlans = new SortedList<TestPlan>();
		TestPlan plan1 = new TestPlan("WolfScheduler");
		TestCase case1 = new TestCase("test0", "Boundary Value", "description", "expected results");
		plan1.addTestCase(case1);
		testPlans.add(plan1);
		TestPlanWriter.writeTestPlanFile(validTestFile2, testPlans);
		
		assertEquals(1, testPlans.size());
		
		checkFiles(validTestFile1, validTestFile2);
		
	}
	
	/**
	 * Helper method to compare two files for the same contents
	 * 
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(File expFile, File actFile) {
		try (Scanner expScanner = new Scanner(expFile);
				Scanner actScanner = new Scanner(actFile);) {

			while (expScanner.hasNextLine()) {
				assertEquals(expScanner.nextLine(), actScanner.nextLine());
			}

			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}
	
	/**
	 * Test to get coverage for the exception
	 */
	@Test
	public void testWriteTestPlanException() {
	    ISortedList<TestPlan> testPlans = new SortedList<TestPlan>();
	    TestPlan plan1 = new TestPlan("SamplePlan");
	    TestCase case1 = new TestCase("test1", "Type", "description", "expected");
	    plan1.addTestCase(case1);
	    testPlans.add(plan1);

	    File invalidFile = new File("non_existing_directory/invalid_file.txt");
	    try {
	        TestPlanWriter.writeTestPlanFile(invalidFile, testPlans);
	        fail("Exception should have been thrown");
	    } catch (IllegalArgumentException e) {
	        assertEquals("Unable to save file.", e.getMessage());
	    }
	}



}
