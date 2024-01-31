package edu.ncsu.csc216.stp.model.io;

import java.io.File;
import java.io.PrintStream;

import edu.ncsu.csc216.stp.model.test_plans.TestPlan;
import edu.ncsu.csc216.stp.model.tests.TestCase;
import edu.ncsu.csc216.stp.model.util.ISortedList;
import edu.ncsu.csc216.stp.model.util.ISwapList;

/**
 * Writes the test plan’s to the given file.
 * 
 * @author Bryan Phillips
 * @author Diego Sarria
 */
public class TestPlanWriter {

	/**
	 * Receives a File with the file name to write to and an ISortedList of
	 * TestPlans to write to file. TestPlanWriter should use TestCase’s toString()
	 * method to create the properly formatted output for a TestCase. If there are
	 * any errors or exceptions, an IllegalArgumentException is thrown with the
	 * message Unable to save file.
	 * 
	 * @param fileName file name
	 * @param planList plan list
	 */
	public static void writeTestPlanFile(File fileName, ISortedList<TestPlan> planList) {
	    try {
	        PrintStream fileWriter = new PrintStream(fileName);
	        for (int i = 0; i < planList.size(); i++) {
	            fileWriter.print("! " + planList.get(i).getTestPlanName() + "\n");
	            ISwapList<TestCase> testPlan = planList.get(i).getTestCases(); 
	             
	            for (int j = 0; j < testPlan.size(); j++) {
	                fileWriter.print(testPlan.get(j).toString()); 
	                if (j < testPlan.size() - 1) {
	                    fileWriter.print(""); 
	                }
	            }
	            if (i < planList.size() - 1) {
	                fileWriter.print("");
	            }
	        }

	        fileWriter.close();
	    } catch (Exception e) {
	        throw new IllegalArgumentException("Unable to save file.");
	    }
	}
}

