import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
 Question 1: Get Longest String
  
 This implementation demonstrates:
 Java 21 features (Streams, Records, Pattern Matching) 
 Performance benchmarking 
 Time Complexity: O(n*m) where n is number of strings, m is max string length
 Space Complexity: O(k) where k is number of allowed characters
 */



public class Question1_GetLongestString {
    
   /*
     Record to hold string validation result
     */
    public record ValidationResult(boolean isValid, String reason) {
        public static ValidationResult valid() {
            return new ValidationResult(true, "Valid string");
        }
        
        public static ValidationResult invalid(String reason) {
            return new ValidationResult(false, reason);
        }
    }
    
    /*
      Custom exception for invalid input
     */
    public static class InvalidInputException extends RuntimeException {
        public InvalidInputException(String message) {
            super(message);
        }
    }
    
    /*
     using Stream API   
      
     @param allowedChars String containing allowed characters
     @param strings Array of strings to check
     @return The longest valid string, or empty string if none found
     @throws InvalidInputException if input is invalid
     */
    public static String getLongestValidStringStream(String allowedChars, String[] strings) {
        // Input validation
        if (allowedChars == null) {
            throw new InvalidInputException("Allowed characters cannot be null");
        }
        if (strings == null) {
            throw new InvalidInputException("Strings array cannot be null");
        }
        
        if (strings.length == 0) {
            return "";
        }
        
        // Create set of allowed characters using Stream API
        Set<Character> allowedSet = allowedChars.chars()
                .mapToObj(ch -> (char) ch)
                .collect(Collectors.toSet());
        
        // Use Stream API to process strings
        return Arrays.stream(strings)
                .filter(Objects::nonNull)
                .filter(s -> !s.isEmpty())
                .filter(s -> isValidString(s, allowedSet))
                .max(Comparator.comparingInt(String::length))
                .orElse("");
    }
    
   /*
     Alternative implementation using traditional approach with optimizations
     */
    public static String getLongestValidStringOptimized(String allowedChars, String[] strings) {
        if (allowedChars == null || strings == null || strings.length == 0) {
            return "";
        }
        
        // Create set of allowed characters
        Set<Character> allowedSet = new HashSet<>();
        for (char c : allowedChars.toCharArray()) {
            allowedSet.add(c);
        }
        
        String longest = "";
        
        // I iterate through the array of strings to find the longest one.
        for (String s : strings) {
            if (s == null || s.isEmpty()) continue;
            
            ValidationResult result = validateString(s, allowedSet);
            // I keep track of the longest string found so far and update it whenever I find a longer one.
            if (result.isValid() && s.length() > longest.length()) {
                longest = s;
            }
        }
        
        // Finally, I return the longest string.
        return longest;
    }
    
    /*
     Pattern matching approach (Java 21 feature)
     */
    public static String analyzeStringArray(String[] strings) {
        return switch (strings) {
            case null -> "null array";
            case String[] arr when arr.length == 0 -> "empty array";
            case String[] arr -> {
                long validCount = Arrays.stream(arr)
                        .filter(Objects::nonNull)
                        .filter(s -> !s.isEmpty())
                        .count();
                yield validCount + " non-empty strings found";
            }
        };
    }
    
    /*
      Validates if a string meets the criteria using Stream API
     */
    
    private static boolean isValidString(String s, Set<Character> allowedSet) {
        // Check if all characters are allowed using Stream API
        boolean allCharsAllowed = s.chars()
                .mapToObj(ch -> (char) ch)
                .allMatch(allowedSet::contains);
        
        if (!allCharsAllowed) {
            return false;
        }
        
        // Check for consecutive identical characters using Stream API
        return IntStream.range(1, s.length())
                .noneMatch(i -> s.charAt(i) == s.charAt(i - 1));
    }
    
    /*
     Validates string using traditional approach with detailed result
     */
    private static ValidationResult validateString(String s, Set<Character> allowedSet) {
        // Check if all characters are allowed
        for (char c : s.toCharArray()) {
            if (!allowedSet.contains(c)) {
                return ValidationResult.invalid("Contains disallowed character: " + c);
            }
        }
        
        // Check for consecutive identical characters
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                return ValidationResult.invalid("Contains consecutive identical characters");
            }
        }
        
        return ValidationResult.valid();
    }
    
    /*
     Performance benchmark method
     */
    public static void benchmarkPerformance(String allowedChars, String[] strings, int iterations) {
        System.out.println("Performance Benchmark (" + iterations + " iterations):");
        
        long startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            getLongestValidStringStream(allowedChars, strings);
        }
        long streamTime = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            getLongestValidStringOptimized(allowedChars, strings);
        }
        long optimizedTime = System.nanoTime() - startTime;
        
        System.out.printf("Stream version: %.2f ms%n", streamTime / 1_000_000.0);
        System.out.printf("Optimized version: %.2f ms%n", optimizedTime / 1_000_000.0);
        System.out.printf("Performance ratio: %.2fx%n", (double) optimizedTime / streamTime);
    }
    
    public static void main(String[] args) {
        System.out.println("=== Get Longest String Implementation ===\n");
        
        try {
            // Test case 1: Original example
            String allowed = "ABCD";
            String[] test1 = {"AABCDA", "ABCDZADC", "ABCDBCA", "ABCDABDCA"};
            String result1 = getLongestValidStringStream(allowed, test1);
            System.out.println("Test 1 - Expected: ABCDABDCA, Got: " + result1);
            System.out.println("Array analysis: " + analyzeStringArray(test1));
            
            // Test case 2: Performance comparison
            System.out.println("\n--- Performance Comparison ---");
            benchmarkPerformance(allowed, test1, 10000);
            
            // Test case 3: No valid strings
            String[] test2 = {"ABC", "DEF", "GHI"};
            String result2 = getLongestValidStringStream(allowed, test2);
            System.out.println("\nTest 2 - Expected: , Got: '" + result2 + "'");
            
            // Test case 4: Single character strings
            String[] test3 = {"A", "B", "C", "D"};
            String result3 = getLongestValidStringStream(allowed, test3);
            System.out.println("Test 3 - Expected: A, Got: " + result3);
            
            // Test case 5: Empty strings
            String[] test4 = {""};
            String result4 = getLongestValidStringStream(allowed, test4);
            System.out.println("Test 4 - Expected: , Got: '" + result4 + "'");
            
            // Test case 6: Valid string with alternating characters
            String[] test5 = {"ABABABAB"};
            String result5 = getLongestValidStringStream(allowed, test5);
            System.out.println("Test 5 - Expected: ABABABAB, Got: " + result5);
            
            // Test case 7: Null input (should throw exception)
            try {
                getLongestValidStringStream(null, test1);
                System.out.println("Test 6 - ERROR: Should have thrown exception");
            } catch (InvalidInputException e) {
                System.out.println("Test 6 - SUCCESS: Caught expected exception: " + e.getMessage());
            }
            
            // Test case 8: Null strings array (should throw exception)
            try {
                getLongestValidStringStream(allowed, null);
                System.out.println("Test 7 - ERROR: Should have thrown exception");
            } catch (InvalidInputException e) {
                System.out.println("Test 7 - SUCCESS: Caught expected exception: " + e.getMessage());
            }
            
            // Test case 9: Large dataset performance
            System.out.println("\n--- Large Dataset Test ---");
            String[] largeStrings = new String[1000];
            for (int i = 0; i < 1000; i++) {
                largeStrings[i] = "ABCD".repeat(i % 10 + 1);
            }
            benchmarkPerformance(allowed, largeStrings, 100);
            
            // Test case 10: Algorithm comparison
            System.out.println("\n--- Algorithm Comparison ---");
            String[] testStrings = {"A", "AB", "ABC", "ABCD", "ABCDE"};
            
            System.out.println("Test strings: " + Arrays.toString(testStrings));
            System.out.println("Stream result: " + getLongestValidStringStream(allowed, testStrings));
            System.out.println("Optimized result: " + getLongestValidStringOptimized(allowed, testStrings));
            
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 