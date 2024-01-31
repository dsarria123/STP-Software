package edu.ncsu.csc216.stp.model.test_plans;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.stp.model.tests.TestCase;

/**
 * Tests the AbstractTestPlan class
 */
public class AbstractTestPlanTest {
	
	/**
	 * Test plan field
	 */
    private AbstractTestPlan testPlan;
    
    /**
	 * Setup for test plan
	 */
    @BeforeEach
    public void setUp() {
        testPlan = new TestPlan("Test Plan 1");
    }
    
    /**
	 * Tests testPlan constructor
	 */
    @Test
    public void testTestPlanConstructor() {
        assertEquals("Test Plan 1", testPlan.getTestPlanName());
        assertEquals(0, testPlan.getTestCases().size());
    }
    
    /**
	 * tests setTestPlanName
	 */
    @Test
    public void testSetTestPlanName() {
        testPlan.setTestPlanName("Updated Test Plan");
        assertEquals("Updated Test Plan", testPlan.getTestPlanName());
        
        // Test setting invalid test plan name
        assertThrows(IllegalArgumentException.class, () -> testPlan.setTestPlanName(null));
        assertThrows(IllegalArgumentException.class, () -> testPlan.setTestPlanName(""));
    }
    
    /**
   	 * Tests the addTestCase method
   	 */
    @Test
    public void testAddTestCase() {
        TestCase testCase1 = new TestCase("TC001", "Functional", "Test case description 1", "Expected results 1");
        TestCase testCase2 = new TestCase("TC002", "Integration", "Test case description 2", "Expected results 2");
        
        testPlan.addTestCase(testCase1);
        testPlan.addTestCase(testCase2);
        
        assertEquals(2, testPlan.getTestCases().size());
        assertEquals(testCase1, testPlan.getTestCase(0));
        assertEquals(testCase2, testPlan.getTestCase(1));
    }
    
  
    
    /**
     * Tests getNumberOfFailingTests method
     */
    @Test
    public void testGetNumberOfFailingTests() {
        TestCase testCase1 = new TestCase("TC001", "Functional", "Test case description 1", "Expected results 1");
        TestCase testCase2 = new TestCase("TC002", "Integration", "Test case description 2", "Expected results 2");
        
        testPlan.addTestCase(testCase1);
        testPlan.addTestCase(testCase2);
        
        // Both test cases are passing
        assertEquals(2, testPlan.getNumberOfFailingTests());
        
        // Add a failing test case
        testCase2.addTestResult(false, "Actual results 2");

    }
    
   
    
    /**
     * Tests addTestResult method
     */
    @Test
    public void testAddTestResult() {
        // Create a test case and add it to the test plan
        TestCase testCase = new TestCase("TC001", "Functional", "Test case description", "Expected results");
        testPlan.addTestCase(testCase);
        
        // Add a test result to the test case
        testPlan.addTestResult(0, true, "Actual results");
        
        // Verify that the test result was added to the test case
        assertEquals(23, testCase.getActualResultsLog().length());
    }
    
    /**
     * Tests hashCode() method
     */
    @Test
    public void testHashCode() {
        TestPlan plan1 = new TestPlan("WolfScheduler");
        TestPlan plan2 = new TestPlan("WolfScheduler");
        assertEquals(plan1.hashCode(), plan2.hashCode());
        TestPlan plan3 = new TestPlan("PackScheduler");
        assertNotEquals(plan1.hashCode(), plan3.hashCode());
    }
    
    
   

}

