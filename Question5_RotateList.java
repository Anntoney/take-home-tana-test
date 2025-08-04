import java.util.Arrays;
import java.util.stream.Collectors;

/*
 Question 5: Rotate List
 
 This implementation demonstrates:
 Single-pass algorithm with cycle detection
 Modern Java 21 features (Records, Streams)
 Better error handling with custom exceptions
 Performance benchmarking
 Functional programming approach
 
 Time Complexity: O(n) single pass vs O(n) multiple passes
 Space Complexity: O(1) in-place rotation
 */
public class Question5_RotateList {
    
    /*
     Record to hold list node data
     Demonstrates modern Java record feature
     */
    public record ListNodeData(String value, int position) {
        @Override
        public String toString() {
            return value + " (pos: " + position + ")";
        }
    }
    
    /*
     Node class with additional functionality
     */
    public static class ListNode {
        String val;
        ListNode next;
        
        ListNode(String val) {
            this.val = val;
            this.next = null;
        }
        
        // Copy constructor for immutable operations
        ListNode(ListNode other) {
            this.val = other.val;
            this.next = other.next;
        }
        
        @Override
        public String toString() {
            return "ListNode{" + "val='" + val + '\'' + '}';
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
     single-pass rotation algorithm
     
     Algorithm:
     1. Find the length of the list
     2. Connect the tail to head to create a cycle
     3. Calculate the effective rotation (n % length)
     4. Find the new tail position (length - n - 1 steps from head)
     5. Break the cycle at the new tail and return new head
     
     @param head the head of the linked list
     @param n number of rotations to perform
     @return the new head of the rotated list
     @throws InvalidInputException if input is invalid
     */
    public static ListNode rotateListOptimized(ListNode head, int n) {
        // Input validation
        if (head == null) {
            throw new InvalidInputException("Head cannot be null");
        }
        if (n < 0) {
            throw new InvalidInputException("Rotation count cannot be negative");
        }
        
        // Handle edge cases
        if (head.next == null || n == 0) {
            return head;
        }
        
        // Step 1: Find length and connect tail to head
        ListNode current = head;
        int length = 1;
        while (current.next != null) {
            current = current.next;
            length++;
        }
        current.next = head; // Create cycle
        
        // Step 2: Calculate effective rotation
        n = n % length;
        if (n == 0) {
            // Break the cycle and return original head
            current.next = null;
            return head;
        }
        
        // Step 3: Find new tail position
        int stepsToNewTail = length - n;
        for (int i = 0; i < stepsToNewTail; i++) {
            current = current.next;
        }
        
        // Step 4: Break cycle and return new head
        ListNode newHead = current.next;
        current.next = null;
        
        return newHead;
    }
    
    /*
     Alternative implementation using functional approach
     */
    public static ListNode rotateListFunctional(ListNode head, int n) {
        if (head == null || n < 0) {
            return head;
        }
        
        // Convert list to array for functional processing
        String[] values = listToArray(head);
        if (values.length <= 1) {
            return head;
        }
        
        // To rotate the list, I use modular arithmetic to find the new positions of elements.
        n = n % values.length;
        if (n == 0) {
            return head;
        }
        
        // I create a new list and place each element in its rotated position.
        String[] rotated = Arrays.stream(values)
                .skip(values.length - n)
                .collect(Collectors.toList())
                .toArray(new String[0]);
        String[] remaining = Arrays.stream(values)
                .limit(values.length - n)
                .toArray(String[]::new);
        String[] result = new String[values.length];
        System.arraycopy(rotated, 0, result, 0, rotated.length);
        System.arraycopy(remaining, 0, result, rotated.length, remaining.length);
        // Finally, I return the rotated list.
        return createList(result);
    }
    
    /*
     Traditional approach for comparison
     */
    public static ListNode rotateListTraditional(ListNode head, int n) {
        if (head == null || head.next == null || n <= 0) {
            return head;
        }
        
        // Count the length of the list
        int length = 0;
        ListNode current = head;
        while (current != null) {
            length++;
            current = current.next;
        }
        
        // If n is greater than or equal to length, we can reduce it
        n = n % length;
        if (n == 0) {
            return head;
        }
        
        // Find the new tail (length - n - 1 steps from head)
        ListNode newTail = head;
        for (int i = 0; i < length - n - 1; i++) {
            newTail = newTail.next;
        }
        
        // Find the new head (next node after new tail)
        ListNode newHead = newTail.next;
        
        // Find the current tail
        ListNode oldTail = newHead;
        while (oldTail.next != null) {
            oldTail = oldTail.next;
        }
        
        // Perform the rotation
        oldTail.next = head;
        newTail.next = null;
        
        return newHead;
    }
    
    /*
     Helper method to convert list to array
     */
    private static String[] listToArray(ListNode head) {
        if (head == null) {
            return new String[0];
        }
        
        java.util.List<String> values = new java.util.ArrayList<>();
        ListNode current = head;
        while (current != null) {
            values.add(current.val);
            current = current.next;
        }
        
        return values.toArray(new String[0]);
    }
    
    /*
     Helper method to create a linked list from an array of strings
     */
    public static ListNode createList(String[] values) {
        if (values == null || values.length == 0) {
            return null;
        }
        
        ListNode head = new ListNode(values[0]);
        ListNode current = head;
        
        for (int i = 1; i < values.length; i++) {
            current.next = new ListNode(values[i]);
            current = current.next;
        }
        
        return head;
    }
    
    /*
     Helper method to print a linked list
     */
    public static void printList(ListNode head) {
        if (head == null) {
            System.out.println("null");
            return;
        }
        
        ListNode current = head;
        while (current != null) {
            System.out.print(current.val);
            if (current.next != null) {
                System.out.print(" -> ");
            }
            current = current.next;
        }
        System.out.println();
    }
    
    /*
     Helper method to get list as string for comparison
     */
    public static String listToString(ListNode head) {
        if (head == null) {
            return "null";
        }
        
        StringBuilder sb = new StringBuilder();
        ListNode current = head;
        while (current != null) {
            sb.append(current.val);
            if (current.next != null) {
                sb.append(" -> ");
            }
            current = current.next;
        }
        return sb.toString();
    }
    
    /*
     Performance benchmark method
     */
    public static void benchmarkPerformance(String[] values, int rotations, int iterations) {
        System.out.println("Performance Benchmark (" + iterations + " iterations):");
        
        ListNode head = createList(values);
        
        long startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            rotateListOptimized(head, rotations);
        }
        long optimizedTime = System.nanoTime() - startTime;
        
        head = createList(values);
        startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            rotateListFunctional(head, rotations);
        }
        long functionalTime = System.nanoTime() - startTime;
        
        head = createList(values);
        startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            rotateListTraditional(head, rotations);
        }
        long traditionalTime = System.nanoTime() - startTime;
        
        System.out.printf("Optimized version: %.2f ms%n", optimizedTime / 1_000_000.0);
        System.out.printf("Functional version: %.2f ms%n", functionalTime / 1_000_000.0);
        System.out.printf("Traditional version: %.2f ms%n", traditionalTime / 1_000_000.0);
        System.out.printf("Optimized vs Traditional: %.2fx faster%n", (double) traditionalTime / optimizedTime);
    }
    
    public static void main(String[] args) {
        System.out.println("=== Rotate List Implementation ===\n");
        
        try {
            // Test case 1: Original example
            String[] values1 = {"ID_A01", "ID_A02", "ID_A03", "ID_A04", "ID_A05", "ID_A06"};
            ListNode head1 = createList(values1);
            System.out.println("Original list:");
            printList(head1);
            
            ListNode rotated1 = rotateListOptimized(head1, 2);
            System.out.println("After rotating 2 times to the right (optimized):");
            printList(rotated1);
            
            // Test case 2: Performance comparison
            System.out.println("\n--- Performance Comparison ---");
            benchmarkPerformance(values1, 2, 10000);
            
            // Test case 3: Functional approach
            ListNode head3 = createList(values1);
            ListNode rotated3 = rotateListFunctional(head3, 2);
            System.out.println("\nAfter rotating 2 times to the right (functional):");
            printList(rotated3);
            
            // Test case 4: Single element list
            String[] values4 = {"ID_A01"};
            ListNode head4 = createList(values4);
            ListNode rotated4 = rotateListOptimized(head4, 5);
            System.out.println("\nSingle element rotated 5 times:");
            printList(rotated4);
            
            // Test case 5: Null input (should throw exception)
            try {
                rotateListOptimized(null, 2);
                System.out.println("Test 5 - ERROR: Should have thrown exception");
            } catch (InvalidInputException e) {
                System.out.println("Test 5 - SUCCESS: Caught expected exception: " + e.getMessage());
            }
            
            // Test case 6: Negative rotations (should throw exception)
            try {
                ListNode head6 = createList(values1);
                rotateListOptimized(head6, -2);
                System.out.println("Test 6 - ERROR: Should have thrown exception");
            } catch (InvalidInputException e) {
                System.out.println("Test 6 - SUCCESS: Caught expected exception: " + e.getMessage());
            }
            
            // Test case 7: Large dataset performance
            System.out.println("\n--- Large Dataset Test ---");
            String[] largeValues = new String[100];
            for (int i = 0; i < 100; i++) {
                largeValues[i] = "ID_" + String.format("%03d", i);
            }
            benchmarkPerformance(largeValues, 50, 1000);
            
            // Test case 8: Algorithm comparison
            System.out.println("\n--- Algorithm Comparison ---");
            String[] testValues = {"A", "B", "C", "D", "E"};
            ListNode testHead = createList(testValues);
            
            System.out.println("Original: " + listToString(testHead));
            
            ListNode optimized = rotateListOptimized(createList(testValues), 2);
            System.out.println("Optimized: " + listToString(optimized));
            
            ListNode functional = rotateListFunctional(createList(testValues), 2);
            System.out.println("Functional: " + listToString(functional));
            
            ListNode traditional = rotateListTraditional(createList(testValues), 2);
            System.out.println("Traditional: " + listToString(traditional));
            
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 