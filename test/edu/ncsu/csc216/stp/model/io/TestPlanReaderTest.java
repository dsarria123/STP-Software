package edu.ncsu.csc216.stp.model.io;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

import edu.ncsu.csc216.stp.model.test_plans.TestPlan;
import edu.ncsu.csc216.stp.model.util.ISortedList;

/**
 * TestPlanReader test file
 */
public class TestPlanReaderTest {

	/** Valid test file */
	private final File validTestFile = new File("test-files/test-plans0.txt");
	
	/** Invalid test file */
	private final File invalidTestFile1 = new File("test-files/test-plans4.txt");
	
	/** Invalid test file */
	private final File invalidTestFile2 = new File("test-files/test-plans5.txt");
	
	/** Invalid test file */
	private final File invalidTestFile3 = new File("test-files/test-plans6.txt");

	/** Invalid test file */
	private final File invalidTestFile4 = new File("test-files/test-plans7.txt");
	
	/** Invalid test file */
	private final File invalidTestFile5 = new File("test-files/test-plans8.txt");
	
	/** Invalid test file */
	private final File invalidTestFile6 = new File("test-files/test-plans9.txt");

	/**
	 * Tests readProductsFile method for valid tests
	 */
	@Test
	public void testValidReadTestPlanFile() {
		ISortedList<TestPlan> plans = TestPlanReader.readTestPlansFile(validTestFile);
		assertEquals(2, plans.size());
		assertEquals("test1", plans.get(1).getTestCase(0).getTestCaseId());
		assertEquals("test2", plans.get(1).getTestCase(1).getTestCaseId());
		assertEquals("test3", plans.get(1).getTestCase(2).getTestCaseId());
		assertEquals("test0", plans.get(0).getTestCase(0).getTestCaseId());
		assertEquals("test1", plans.get(0).getTestCase(1).getTestCaseId());
	}
	
	/**
	 * Tests readProductsFile method for invalid tests
	 */
	@Test
	public void testInvalidReadTestPlanFile() {
		ISortedList<TestPlan> plans1 = TestPlanReader.readTestPlansFile(invalidTestFile1);
		assertEquals(1, plans1.size());
		assertEquals(0, plans1.get(0).getTestCases().size());
		ISortedList<TestPlan> plans2 = TestPlanReader.readTestPlansFile(invalidTestFile2);
		assertEquals(1, plans2.size());
		assertEquals(0, plans2.get(0).getTestCases().size());
		ISortedList<TestPlan> plans3 = TestPlanReader.readTestPlansFile(invalidTestFile3);
		assertEquals(1, plans3.size());
		assertEquals(0, plans3.get(0).getTestCases().size());
		ISortedList<TestPlan> plans4 = TestPlanReader.readTestPlansFile(invalidTestFile4);
		assertEquals(1, plans4.size());
		assertEquals(0, plans4.get(0).getTestCases().size());
		ISortedList<TestPlan> plans5 = TestPlanReader.readTestPlansFile(invalidTestFile5);
		assertEquals(1, plans5.size());
		assertEquals(0, plans5.get(0).getTestCases().size());
		ISortedList<TestPlan> plans6 = TestPlanReader.readTestPlansFile(invalidTestFile6);
		assertEquals(1, plans6.size());
		assertEquals(0, plans6.get(0).getTestCases().size());
		
	}
}