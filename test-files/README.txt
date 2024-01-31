Overview of test files for WolfTasks

test-plans0.txt - valid file with two test plans and less complex information
test-plans1.txt - valid file with two real system test plans
test-plans2.txt - valid file with only test plan name
test-plans3.txt - missing leading ! in file - IAE thrown with message Unable to load file.
test-plans4.txt - test case with only one string for id and type - creates test plan with no test cases
test-plans5.txt - test case with only one * item - creates test plans with no test cases
test-plans6.txt - description with no text - creates test plan with no test cases
test-plans7.txt - expected result with no text - creates test plan with no test cases
test-plans8.txt - test result missing PASS/FAIL info - creates test plan with no test cases
test-plans9.txt - test result missing actual results info - creates test plan with no test cases