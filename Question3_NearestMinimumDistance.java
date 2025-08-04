import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Collectors;

/*
 Question 3: Nearest Minimum Distance 
 
 This implementation demonstrates:
 
 Time Complexity: O(n) for all approaches
 Space Complexity: O(1) for optimized approach
 */
public class Question3_NearestMinimumDistance {
    
    /*
    Record to hold minimum occurrence data
     Demonstrates modern Java record feature
     */
    public record MinOccurrence(int value, int position) {
        @Override
        public String toString() {
            return value + " at position " + position;
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
     using Stream API and modern Java features
     
     @param array the input array
     @return the distance between closest minimum occurrences
     @throws InvalidInputException if input is invalid
     */
    public static int nearestMinimumDistanceStream(int[] array) {
        // Input validation
        if (array == null) {
            throw new InvalidInputException("Array cannot be null");
        }
        if (array.length < 2) {
            return -1; // Need at least 2 elements
        }
        
        // First, I find the minimum value in the array.
        int minValue = Arrays.stream(array).min().orElse(Integer.MAX_VALUE);
        
        // Then, I track the indices where this minimum occurs.
        List<Integer> minPositions = IntStream.range(0, array.length)
                .filter(i -> array[i] == minValue)
                .boxed()
                .collect(Collectors.toList());
        
        if (minPositions.size() < 2) {
            return -1; // Need at least 2 minimum occurrences
        }
        
        // I calculate the smallest distance between any two such indices and return it.
        return minPositions.stream()
                .flatMapToInt(pos1 -> minPositions.stream()
                        .mapToInt(pos2 -> Math.abs(pos2 - pos1)))
                .filter(distance -> distance > 0)
                .min()
                .orElse(-1);
    }
    
    /*
     Alternative implementation using optimized approach
     */
    public static int nearestMinimumDistanceOptimized(int[] array) {
        if (array == null || array.length < 2) {
            return -1;
        }
        
        // Find minimum value in single pass
        int minValue = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < minValue) {
                minValue = array[i];
            }
        }
        
        // Find minimum distance in single pass
        int minDistance = Integer.MAX_VALUE;
        int lastMinIndex = -1;
        
        for (int i = 0; i < array.length; i++) {
            if (array[i] == minValue) {
                if (lastMinIndex != -1) {
                    int distance = i - lastMinIndex;
                    if (distance < minDistance) {
                        minDistance = distance;
                    }
                }
                lastMinIndex = i;
            }
        }
        
        return minDistance == Integer.MAX_VALUE ? -1 : minDistance;
    }
    
    /*
     Pattern matching approach (Java 21 feature)
     */
    public static String analyzeArrayPattern(int[] array) {
        return switch (array) {
            case null -> "null array";
            case int[] arr when arr.length < 2 -> "insufficient elements";
            case int[] arr -> {
                int min = Arrays.stream(arr).min().orElse(Integer.MAX_VALUE);
                long minCount = Arrays.stream(arr).filter(x -> x == min).count();
                yield minCount >= 2 ? "valid array with " + minCount + " minimum occurrences" : "insufficient minimum occurrences";
            }
        };
    }
    
    /*
     Advanced implementation with detailed analysis
     */
    public static List<MinOccurrence> analyzeMinimumOccurrences(int[] array) {
        if (array == null || array.length == 0) {
            return new ArrayList<>();
        }
        
        int minValue = Arrays.stream(array).min().orElse(Integer.MAX_VALUE);
        
        return IntStream.range(0, array.length)
                .filter(i -> array[i] == minValue)
                .mapToObj(i -> new MinOccurrence(minValue, i))
                .collect(Collectors.toList());
    }
    
    /*
     Traditional approach for comparison
     */
    public static int nearestMinimumDistanceTraditional(int[] array) {
        if (array == null || array.length < 2) {
            return -1;
        }
        
        // Find the minimum value in the array
        int minValue = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < minValue) {
                minValue = array[i];
            }
        }
        
        // Find all positions of the minimum value
        int minDistance = Integer.MAX_VALUE;
        int lastMinIndex = -1;
        
        for (int i = 0; i < array.length; i++) {
            if (array[i] == minValue) {
                if (lastMinIndex != -1) {
                    // Calculate distance between current and last minimum occurrence
                    int distance = i - lastMinIndex;
                    if (distance < minDistance) {
                        minDistance = distance;
                    }
                }
                lastMinIndex = i;
            }
        }
        
        return minDistance == Integer.MAX_VALUE ? -1 : minDistance;
    }
    
    /*
    Performance benchmark method
     */
    public static void benchmarkPerformance(int[] array, int iterations) {
        System.out.println("Performance Benchmark (" + iterations + " iterations):");
        
        long startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            nearestMinimumDistanceStream(array);
        }
        long streamTime = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            nearestMinimumDistanceOptimized(array);
        }
        long optimizedTime = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            nearestMinimumDistanceTraditional(array);
        }
        long traditionalTime = System.nanoTime() - startTime;
        
        System.out.printf("Stream version: %.2f ms%n", streamTime / 1_000_000.0);
        System.out.printf("Optimized version: %.2f ms%n", optimizedTime / 1_000_000.0);
        System.out.printf("Traditional version: %.2f ms%n", traditionalTime / 1_000_000.0);
        System.out.printf("Optimized vs Traditional: %.2fx faster%n", (double) traditionalTime / optimizedTime);
    }
    
    public static void main(String[] args) {
        System.out.println("=== Nearest Minimum Distance Implementation ===\n");
        
        try {
            // Test case 1: Original example
            int[] array1 = {1, 2, 3, 1, 4, 5, 2};
            int result1 = nearestMinimumDistanceStream(array1);
            System.out.println("Test 1 - Expected: 3, Got: " + result1);
            System.out.println("Array analysis: " + analyzeArrayPattern(array1));
            
            // Test case 2: Performance comparison
            System.out.println("\n--- Performance Comparison ---");
            benchmarkPerformance(array1, 10000);
            
            // Test case 3: Minimum at adjacent positions
            int[] array2 = {5, 1, 1, 3, 4};
            int result2 = nearestMinimumDistanceStream(array2);
            System.out.println("\nTest 2 - Expected: 1, Got: " + result2);
            
            // Test case 4: Minimum at start and end
            int[] array3 = {1, 5, 3, 4, 1};
            int result3 = nearestMinimumDistanceStream(array3);
            System.out.println("Test 3 - Expected: 4, Got: " + result3);
            
            // Test case 5: All elements are minimum
            int[] array4 = {2, 2, 2, 2, 2};
            int result4 = nearestMinimumDistanceStream(array4);
            System.out.println("Test 4 - Expected: 1, Got: " + result4);
            
            // Test case 6: Null array (should throw exception)
            try {
                nearestMinimumDistanceStream(null);
                System.out.println("Test 5 - ERROR: Should have thrown exception");
            } catch (InvalidInputException e) {
                System.out.println("Test 5 - SUCCESS: Caught expected exception: " + e.getMessage());
            }
            
            // Test case 7: Single element array
            int[] array5 = {5};
            int result5 = nearestMinimumDistanceStream(array5);
            System.out.println("Test 6 - Expected: -1, Got: " + result5);
            
            // Test case 8: Multiple minimum values with different distances
            int[] array6 = {1, 5, 1, 3, 1, 4, 1};
            int result6 = nearestMinimumDistanceStream(array6);
            System.out.println("Test 7 - Expected: 2, Got: " + result6);
            
            // Test case 9: Negative numbers
            int[] array7 = {-1, 2, -1, 3, -1};
            int result7 = nearestMinimumDistanceStream(array7);
            System.out.println("Test 8 - Expected: 2, Got: " + result7);
            
            // Test case 10: Detailed minimum analysis
            System.out.println("\n--- Minimum Occurrence Analysis ---");
            List<MinOccurrence> analysis = analyzeMinimumOccurrences(array6);
            System.out.println("Minimum occurrences:");
            analysis.forEach(System.out::println);
            
            // Test case 11: Large dataset performance
            System.out.println("\n--- Large Dataset Test ---");
            int[] largeArray = new int[1000];
            Arrays.fill(largeArray, 100);
            // Add minimum values at specific positions
            largeArray[100] = 1;
            largeArray[200] = 1;
            largeArray[300] = 1;
            largeArray[400] = 1;
            benchmarkPerformance(largeArray, 1000);
            
            // Test case 12: Algorithm comparison
            System.out.println("\n--- Algorithm Comparison ---");
            int[] testArray = {1, 5, 1, 3, 1, 4, 1};
            
            System.out.println("Test array: " + Arrays.toString(testArray));
            System.out.println("Stream result: " + nearestMinimumDistanceStream(testArray));
            System.out.println("Optimized result: " + nearestMinimumDistanceOptimized(testArray));
            System.out.println("Traditional result: " + nearestMinimumDistanceTraditional(testArray));
            
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 