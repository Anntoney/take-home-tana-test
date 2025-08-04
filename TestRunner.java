import java.util.List;
import java.util.Arrays;

/*
  Simple test runner for all questions
  This provides a way to run tests without requiring JUnit
 */
public class TestRunner {
    
    private static int passedTests = 0;
    private static int totalTests = 0;
    
    public static void main(String[] args) {
        System.out.println("=== Running All Tests ===\n");
        
        // Run tests for each question
        testQuestion1();
        testQuestion2();
        testQuestion3();
        testQuestion4();
        testQuestion5();
        
        // Print summary
        System.out.println("\n=== Test Summary ===");
        System.out.println("Passed: " + passedTests + "/" + totalTests + " tests");
        if (passedTests == totalTests) {
            System.out.println("✅ All tests passed!");
        } else {
            System.out.println("❌ Some tests failed!");
        }
    }
    
    private static void testQuestion1() {
        System.out.println("Testing Question 1: Get Longest String");
        
        // Test 1: Basic functionality
        try {
            String allowed = "ABCD";
            String[] arr = {"AABCDA", "ABCDZADC", "ABCDBCA", "ABCDABDCA"};
            String result = Question1_GetLongestString.getLongestValidStringStream(allowed, arr);
            assert result.equals("ABCDABDCA") : "Expected 'ABCDABDCA', got '" + result + "'";
            passedTests++;
            System.out.println("  ✅ Basic test passed");
        } catch (Exception e) {
            System.out.println("  ❌ Basic test failed: " + e.getMessage());
        }
        totalTests++;
        
        // Test 2: No valid strings
        try {
            String allowed = "ABCD";
            String[] arr = {"ZZZ", "YYY"};
            String result = Question1_GetLongestString.getLongestValidStringStream(allowed, arr);
            assert result.equals("") : "Expected empty string, got '" + result + "'";
            passedTests++;
            System.out.println("  ✅ No valid strings test passed");
        } catch (Exception e) {
            System.out.println("  ❌ No valid strings test failed: " + e.getMessage());
        }
        totalTests++;
        
        // Test 3: Null input exception
        try {
            String[] arr = {"A"};
            Question1_GetLongestString.getLongestValidStringStream(null, arr);
            System.out.println("  ❌ Should have thrown exception for null allowed");
        } catch (Question1_GetLongestString.InvalidInputException e) {
            passedTests++;
            System.out.println("  ✅ Null input exception test passed");
        } catch (Exception e) {
            System.out.println("  ❌ Wrong exception type: " + e.getClass().getSimpleName());
        }
        totalTests++;
        
        System.out.println();
    }
    
    private static void testQuestion2() {
        System.out.println("Testing Question 2: First Unique Product");
        
        // Test 1: Basic functionality
        try {
            String[] arr = {"Apple", "Computer", "Apple", "Bag"};
            String result = Question2_FirstUniqueProduct.firstUniqueProductStream(arr);
            assert "Computer".equals(result) : "Expected 'Computer', got '" + result + "'";
            passedTests++;
            System.out.println("  ✅ Basic test passed");
        } catch (Exception e) {
            System.out.println("  ❌ Basic test failed: " + e.getMessage());
        }
        totalTests++;
        
        // Test 2: All duplicates
        try {
            String[] arr = {"A", "A", "B", "B"};
            String result = Question2_FirstUniqueProduct.firstUniqueProductStream(arr);
            assert result == null : "Expected null, got '" + result + "'";
            passedTests++;
            System.out.println("  ✅ All duplicates test passed");
        } catch (Exception e) {
            System.out.println("  ❌ All duplicates test failed: " + e.getMessage());
        }
        totalTests++;
        
        // Test 3: Null input exception
        try {
            Question2_FirstUniqueProduct.firstUniqueProductStream(null);
            System.out.println("  ❌ Should have thrown exception for null array");
        } catch (Question2_FirstUniqueProduct.InvalidInputException e) {
            passedTests++;
            System.out.println("  ✅ Null input exception test passed");
        } catch (Exception e) {
            System.out.println("  ❌ Wrong exception type: " + e.getClass().getSimpleName());
        }
        totalTests++;
        
        System.out.println();
    }
    
    private static void testQuestion3() {
        System.out.println("Testing Question 3: Nearest Minimum Distance");
        
        // Test 1: Basic functionality
        try {
            int[] arr = {1, 2, 3, 1, 4, 5, 2};
            int result = Question3_NearestMinimumDistance.nearestMinimumDistanceStream(arr);
            assert result == 3 : "Expected 3, got " + result;
            passedTests++;
            System.out.println("  ✅ Basic test passed");
        } catch (Exception e) {
            System.out.println("  ❌ Basic test failed: " + e.getMessage());
        }
        totalTests++;
        
        // Test 2: Adjacent minimums
        try {
            int[] arr = {5, 1, 1, 3, 4};
            int result = Question3_NearestMinimumDistance.nearestMinimumDistanceStream(arr);
            assert result == 1 : "Expected 1, got " + result;
            passedTests++;
            System.out.println("  ✅ Adjacent minimums test passed");
        } catch (Exception e) {
            System.out.println("  ❌ Adjacent minimums test failed: " + e.getMessage());
        }
        totalTests++;
        
        // Test 3: Null input exception
        try {
            Question3_NearestMinimumDistance.nearestMinimumDistanceStream(null);
            System.out.println("  ❌ Should have thrown exception for null array");
        } catch (Question3_NearestMinimumDistance.InvalidInputException e) {
            passedTests++;
            System.out.println("  ✅ Null input exception test passed");
        } catch (Exception e) {
            System.out.println("  ❌ Wrong exception type: " + e.getClass().getSimpleName());
        }
        totalTests++;
        
        System.out.println();
    }
    
    private static void testQuestion4() {
        System.out.println("Testing Question 4: Top 3 Common Words");
        
        // Test 1: Basic functionality
        try {
            String sentence = "hi there care to discuss algorithm basis or how to solve algorithm or";
            List<String> result = Question4_Top3CommonWords.topThreeWordsEnhanced(sentence);
            List<String> expected = Arrays.asList("algorithm", "or", "to");
            assert result.equals(expected) : "Expected " + expected + ", got " + result;
            passedTests++;
            System.out.println("  ✅ Basic test passed");
        } catch (Exception e) {
            System.out.println("  ❌ Basic test failed: " + e.getMessage());
        }
        totalTests++;
        
        // Test 2: Empty string
        try {
            List<String> result = Question4_Top3CommonWords.topThreeWordsEnhanced("");
            assert result.isEmpty() : "Expected empty list, got " + result;
            passedTests++;
            System.out.println("  ✅ Empty string test passed");
        } catch (Exception e) {
            System.out.println("  ❌ Empty string test failed: " + e.getMessage());
        }
        totalTests++;
        
        // Test 3: Null input exception
        try {
            Question4_Top3CommonWords.topThreeWordsEnhanced(null);
            System.out.println("  ❌ Should have thrown exception for null input");
        } catch (Question4_Top3CommonWords.InvalidInputException e) {
            passedTests++;
            System.out.println("  ✅ Null input exception test passed");
        } catch (Exception e) {
            System.out.println("  ❌ Wrong exception type: " + e.getClass().getSimpleName());
        }
        totalTests++;
        
        System.out.println();
    }
    
    private static void testQuestion5() {
        System.out.println("Testing Question 5: Rotate List");
        
        // Test 1: Basic functionality
        try {
            String[] values = {"A", "B", "C", "D", "E"};
            Question5_RotateList.ListNode head = Question5_RotateList.createList(values);
            Question5_RotateList.ListNode rotated = Question5_RotateList.rotateListOptimized(head, 2);
            String result = Question5_RotateList.listToString(rotated);
            assert result.equals("D -> E -> A -> B -> C") : "Expected 'D -> E -> A -> B -> C', got '" + result + "'";
            passedTests++;
            System.out.println("  ✅ Basic test passed");
        } catch (Exception e) {
            System.out.println("  ❌ Basic test failed: " + e.getMessage());
        }
        totalTests++;
        
        // Test 2: Single element
        try {
            String[] values = {"A"};
            Question5_RotateList.ListNode head = Question5_RotateList.createList(values);
            Question5_RotateList.ListNode rotated = Question5_RotateList.rotateListOptimized(head, 3);
            String result = Question5_RotateList.listToString(rotated);
            assert result.equals("A") : "Expected 'A', got '" + result + "'";
            passedTests++;
            System.out.println("  ✅ Single element test passed");
        } catch (Exception e) {
            System.out.println("  ❌ Single element test failed: " + e.getMessage());
        }
        totalTests++;
        
        // Test 3: Null input exception
        try {
            Question5_RotateList.rotateListOptimized(null, 2);
            System.out.println("  ❌ Should have thrown exception for null head");
        } catch (Question5_RotateList.InvalidInputException e) {
            passedTests++;
            System.out.println("  ✅ Null input exception test passed");
        } catch (Exception e) {
            System.out.println("  ❌ Wrong exception type: " + e.getClass().getSimpleName());
        }
        totalTests++;
        
        System.out.println();
    }
} 