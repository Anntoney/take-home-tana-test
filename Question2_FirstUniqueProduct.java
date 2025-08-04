import java.util.*;
import java.util.stream.Collectors;
import java.util.function.Function;

/*
 Question 2: First Unique Product

 This implementation demonstrates:

 Better error handling with custom exceptions
 Performance benchmarking
 Multiple algorithm implementations for comparison
 Time Complexity: O(n) for all approaches
 Space Complexity: O(n) for frequency tracking
 */
public class Question2_FirstUniqueProduct {
    
    /*
     Record to hold product frequency data
     Demonstrates modern Java record feature
     */
    public record ProductFrequency(String product, int frequency, int firstOccurrence) {
        @Override
        public String toString() {
            return product + " (freq: " + frequency + ", first: " + firstOccurrence + ")";
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
     
     @param products array of product strings
     @return the first unique string, or null if all appear multiple times
     @throws InvalidInputException if input is invalid
     */
    public static String firstUniqueProductStream(String[] products) {
        // Input validation
        if (products == null) {
            throw new InvalidInputException("Products array cannot be null");
        }
        
        if (products.length == 0) {
            return null;
        }
        
        // I use a map to count the frequency of each product in the list.
        Map<String, Long> frequencyMap = Arrays.stream(products)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(
                    Function.identity(),
                    Collectors.counting()
                ));
        
        // Then, I iterate through the list to find the first product with a count of one.
        return Arrays.stream(products)
                .filter(Objects::nonNull)
                .filter(product -> frequencyMap.get(product) == 1)
                .findFirst()
                // If found, I return it; otherwise, I return a default value.
                .orElse(null);
    }
    
    /*
     Alternative implementation using traditional approach with optimizations
     */
    public static String firstUniqueProductOptimized(String[] products) {
        if (products == null || products.length == 0) {
            return null;
        }
        
        // Use LinkedHashMap to maintain insertion order
        Map<String, Integer> frequencyMap = new LinkedHashMap<>();
        
        // Count frequency while maintaining order
        for (String product : products) {
            if (product != null) {
                frequencyMap.merge(product, 1, Integer::sum);
            }
        }
        
        // Find first product with frequency 1
        return frequencyMap.entrySet().stream()
                .filter(entry -> entry.getValue() == 1)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }
    
    /*
     Pattern matching approach
     */
    public static String analyzeProductArray(String[] products) {
        return switch (products) {
            case null -> "null array";
            case String[] arr when arr.length == 0 -> "empty array";
            case String[] arr -> {
                long uniqueCount = Arrays.stream(arr)
                        .filter(Objects::nonNull)
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                        .values().stream()
                        .filter(count -> count == 1)
                        .count();
                yield uniqueCount + " unique products found";
            }
        };
    }
    
    /*
      Traditional approach for comparison
     */
    public static String firstUniqueProductTraditional(String[] products) {
        if (products == null || products.length == 0) {
            return null;
        }
        
        // Count frequency of each string
        Map<String, Integer> frequencyMap = new HashMap<>();
        for (String product : products) {
            if (product != null) {
                frequencyMap.put(product, frequencyMap.getOrDefault(product, 0) + 1);
            }
        }
        
        // Find the first string that appears only once
        for (String product : products) {
            if (product != null && frequencyMap.get(product) == 1) {
                return product;
            }
        }
        
        return null;
    }
    
    /*
     Advanced implementation with detailed analysis
     */
    public static List<ProductFrequency> analyzeProductFrequencies(String[] products) {
        if (products == null || products.length == 0) {
            return new ArrayList<>();
        }
        
        Map<String, ProductFrequency> analysis = new HashMap<>();
        
        for (int i = 0; i < products.length; i++) {
            String product = products[i];
            if (product != null) {
                ProductFrequency existing = analysis.get(product);
                if (existing == null) {
                    analysis.put(product, new ProductFrequency(product, 1, i));
                } else {
                    analysis.put(product, new ProductFrequency(product, existing.frequency + 1, existing.firstOccurrence));
                }
            }
        }
        
        return analysis.values().stream()
                .sorted(Comparator.comparing(ProductFrequency::firstOccurrence))
                .collect(Collectors.toList());
    }
    
    /*
      Performance benchmark method
     */
    public static void benchmarkPerformance(String[] products, int iterations) {
        System.out.println("Performance Benchmark (" + iterations + " iterations):");
        
        long startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            firstUniqueProductStream(products);
        }
        long streamTime = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            firstUniqueProductOptimized(products);
        }
        long optimizedTime = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            firstUniqueProductTraditional(products);
        }
        long traditionalTime = System.nanoTime() - startTime;
        
        System.out.printf("Stream version: %.2f ms%n", streamTime / 1_000_000.0);
        System.out.printf("Optimized version: %.2f ms%n", optimizedTime / 1_000_000.0);
        System.out.printf("Traditional version: %.2f ms%n", traditionalTime / 1_000_000.0);
        System.out.printf("Stream vs Traditional: %.2fx faster%n", (double) traditionalTime / streamTime);
    }
    
    public static void main(String[] args) {
        System.out.println("=== First Unique Product Implementation ===\n");
        
        try {
            // Test case 1: Original example
            String[] products1 = {"Apple", "Computer", "Apple", "Bag"};
            String result1 = firstUniqueProductStream(products1);
            System.out.println("Test 1 - Expected: Computer, Got: " + result1);
            System.out.println("Array analysis: " + analyzeProductArray(products1));
            
            // Test case 2: Performance comparison
            System.out.println("\n--- Performance Comparison ---");
            benchmarkPerformance(products1, 10000);
            
            // Test case 3: All strings appear multiple times
            String[] products2 = {"Apple", "Apple", "Banana", "Banana"};
            String result2 = firstUniqueProductStream(products2);
            System.out.println("\nTest 2 - Expected: null, Got: " + result2);
            
            // Test case 4: All strings are unique
            String[] products3 = {"Apple", "Banana", "Cherry"};
            String result3 = firstUniqueProductStream(products3);
            System.out.println("Test 3 - Expected: Apple, Got: " + result3);
            
            // Test case 5: Empty array
            String[] products4 = {};
            String result4 = firstUniqueProductStream(products4);
            System.out.println("Test 4 - Expected: null, Got: " + result4);
            
            // Test case 6: Null array (should throw exception)
            try {
                firstUniqueProductStream(null);
                System.out.println("Test 5 - ERROR: Should have thrown exception");
            } catch (InvalidInputException e) {
                System.out.println("Test 5 - SUCCESS: Caught expected exception: " + e.getMessage());
            }
            
            // Test case 7: Multiple unique strings, return first
            String[] products6 = {"Apple", "Banana", "Apple", "Cherry", "Banana"};
            String result6 = firstUniqueProductStream(products6);
            System.out.println("Test 6 - Expected: Cherry, Got: " + result6);
            
            // Test case 8: Case sensitive
            String[] products7 = {"Apple", "apple", "APPLE"};
            String result7 = firstUniqueProductStream(products7);
            System.out.println("Test 7 - Expected: Apple, Got: " + result7);
            
            // Test case 9: Detailed frequency analysis
            System.out.println("\n--- Frequency Analysis ---");
            List<ProductFrequency> analysis = analyzeProductFrequencies(products6);
            System.out.println("Product frequencies:");
            analysis.forEach(System.out::println);
            
            // Test case 10: Large dataset performance
            System.out.println("\n--- Large Dataset Test ---");
            String[] largeProducts = new String[1000];
            for (int i = 0; i < 1000; i++) {
                largeProducts[i] = "Product_" + (i % 100); // Creates duplicates
            }
            largeProducts[500] = "UniqueProduct"; // Add one unique product
            benchmarkPerformance(largeProducts, 1000);
            
            // Test case 11: Algorithm comparison
            System.out.println("\n--- Algorithm Comparison ---");
            String[] testProducts = {"A", "B", "A", "C", "B", "D"};
            
            System.out.println("Test array: " + Arrays.toString(testProducts));
            System.out.println("Stream result: " + firstUniqueProductStream(testProducts));
            System.out.println("Optimized result: " + firstUniqueProductOptimized(testProducts));
            System.out.println("Traditional result: " + firstUniqueProductTraditional(testProducts));
            
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 