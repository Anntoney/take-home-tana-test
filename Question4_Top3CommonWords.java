import java.util.*;
import java.util.stream.Collectors;
import java.util.function.Function;

/*
 Question 4: Top 3 Common Words 
  
 This implementation demonstrates:

 Time Complexity: O(n log k) where n is number of words, k is 3
 Space Complexity: O(n) for storing word frequencies
 */
public class Question4_Top3CommonWords {
    
    /*
     Record to hold word frequency data
     */
    public record WordFrequency(String word, int frequency) implements Comparable<WordFrequency> {
        @Override
        public int compareTo(WordFrequency other) {
            // Primary sort by frequency (descending), secondary by word (ascending)
            int freqCompare = Integer.compare(other.frequency, this.frequency);
            return freqCompare != 0 ? freqCompare : this.word.compareTo(other.word);
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
         
     @param sentence the input sentence
     @return list of the three most common words in alphabetical order
     @throws InvalidInputException if input is invalid
     */
    public static List<String> topThreeWordsEnhanced(String sentence) {
        // Input validation with custom exception
        if (sentence == null) {
            throw new InvalidInputException("Sentence cannot be null");
        }
        
        String trimmedSentence = sentence.trim();
        if (trimmedSentence.isEmpty()) {
            return new ArrayList<>();
        }
        
        // I split the text into words and use a map to count their frequencies.
        Map<String, Long> wordFrequencyMap = Arrays.stream(trimmedSentence.split("\\s+"))
                .map(String::trim)
                .filter(word -> !word.isEmpty())
                .collect(Collectors.groupingBy(
                    Function.identity(),
                    Collectors.counting()
                ));
        
        // I then sort the words by frequency and pick the top three.
        PriorityQueue<WordFrequency> pq = new PriorityQueue<>();
        wordFrequencyMap.forEach((word, frequency) -> {
            pq.offer(new WordFrequency(word, frequency.intValue()));
        });
        List<String> result = new ArrayList<>();
        int count = 0;
        while (!pq.isEmpty() && count < 3) {
            result.add(pq.poll().word());
            count++;
        }
        Collections.sort(result);
        // These top three words are returned as the result.
        return result;
    }
    
    /*
     Alternative implementation using traditional approach but with optimizations
     */
    public static List<String> topThreeWordsTraditional(String sentence) {
        if (sentence == null || sentence.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        // Use HashMap for O(1) lookups
        Map<String, Integer> wordFrequency = new HashMap<>();
        String[] words = sentence.split("\\s+");
        
        for (String word : words) {
            String trimmedWord = word.trim();
            if (!trimmedWord.isEmpty()) {
                wordFrequency.merge(trimmedWord, 1, Integer::sum);
            }
        }
        
        // Create list and sort efficiently
        List<String> sortedWords = new ArrayList<>(wordFrequency.keySet());
        sortedWords.sort((a, b) -> {
            int freqCompare = Integer.compare(wordFrequency.get(b), wordFrequency.get(a));
            return freqCompare != 0 ? freqCompare : a.compareTo(b);
        });
        
        // Return top 3 in alphabetical order
        List<String> topThree = sortedWords.subList(0, Math.min(3, sortedWords.size()));
        Collections.sort(topThree);
        
        return topThree;
    }
    
    /*
     Performance benchmark method
     */
    public static void benchmarkPerformance(String sentence, int iterations) {
        System.out.println("Performance Benchmark (" + iterations + " iterations):");
        
        long startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            topThreeWordsEnhanced(sentence);
        }
        long enhancedTime = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            topThreeWordsTraditional(sentence);
        }
        long traditionalTime = System.nanoTime() - startTime;
        
        System.out.printf("Enhanced version: %.2f ms%n", enhancedTime / 1_000_000.0);
        System.out.printf("Traditional version: %.2f ms%n", traditionalTime / 1_000_000.0);
        System.out.printf("Performance ratio: %.2fx%n", (double) traditionalTime / enhancedTime);
    }
    
    public static void main(String[] args) {
        System.out.println("=== Enhanced Top 3 Common Words Implementation ===\n");
        
        // Test cases with enhanced error handling
        try {
            // Test case 1: Original example
            String sentence1 = "hi there care to discuss algorithm basis or how to solve algorithm or";
            List<String> result1 = topThreeWordsEnhanced(sentence1);
            System.out.println("Test 1 - Expected: [algorithm, or, to], Got: " + result1);
            
            // Test case 2: Performance comparison
            System.out.println("\n--- Performance Comparison ---");
            benchmarkPerformance(sentence1, 10000);
            
            // Test case 3: Edge cases
            String sentence2 = "a   b   c   a   b";
            List<String> result2 = topThreeWordsEnhanced(sentence2);
            System.out.println("\nTest 2 - Expected: [a, b, c], Got: " + result2);
            
            // Test case 4: Empty input
            String sentence3 = "";
            List<String> result3 = topThreeWordsEnhanced(sentence3);
            System.out.println("Test 3 - Expected: [], Got: " + result3);
            
            // Test case 5: Null input (should throw exception)
            try {
                topThreeWordsEnhanced(null);
                System.out.println("Test 4 - ERROR: Should have thrown exception");
            } catch (InvalidInputException e) {
                System.out.println("Test 4 - SUCCESS: Caught expected exception: " + e.getMessage());
            }
            
            // Test case 6: Large dataset performance
            System.out.println("\n--- Large Dataset Test ---");
            StringBuilder largeSentence = new StringBuilder();
            for (int i = 0; i < 1000; i++) {
                largeSentence.append("word").append(i % 10).append(" ");
            }
            benchmarkPerformance(largeSentence.toString(), 1000);
            
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 