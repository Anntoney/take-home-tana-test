# Java 21 Take-Home Assessment

Java version: Java 21

## Question 1 - Get Longest String
**File:** `Question1_GetLongestString.java`

**Logic:** Iterates through all strings, checking each for consecutive repeated characters and valid character usage, then returns the longest valid string.

**How to compile/run:** `javac Question1_GetLongestString.java` and `java Question1_GetLongestString`

---

## Question 2 - First Unique Product
**File:** `Question2_FirstUniqueProduct.java`

**Logic:** Uses a HashMap to count frequency of each string, then returns the first string that appears only once in the original order.

**How to compile/run:** `javac Question2_FirstUniqueProduct.java` and `java Question2_FirstUniqueProduct`

---

## Question 3 - Nearest Minimum Distance
**File:** `Question3_NearestMinimumDistance.java`

**Logic:** Finds the minimum value, then tracks all its positions to calculate the minimum distance between any two occurrences.

**How to compile/run:** `javac Question3_NearestMinimumDistance.java` and `java Question3_NearestMinimumDistance`

---

## Question 4 - Top 3 Common Words
**File:** `Question4_Top3CommonWords.java`

**Logic:** Splits sentence by spaces, counts word frequencies, sorts by frequency then alphabetically, and returns the top 3 words in alphabetical order.

**How to compile/run:** `javac Question4_Top3CommonWords.java` and `java Question4_Top3CommonWords`

---

## Question 5 - Rotate List
**File:** `Question5_RotateList.java`

**Logic:** Calculates effective rotation count, finds the new tail and head positions, then reconnects the list to perform the rotation.

**How to compile/run:** `javac Question5_RotateList.java` and `java Question5_RotateList`

---

## Running All Tests

To compile and run all questions:

```bash
javac Question*.java
java Question1_GetLongestString
java Question2_FirstUniqueProduct
java Question3_NearestMinimumDistance
java Question4_Top3CommonWords
java Question5_RotateList
```

## Running Tests

### Option 1: Simple Test Runner 

A simple test runner is provided that doesn't require JUnit. To run all tests:

```bash
javac *.java
java TestRunner
```

This will run comprehensive tests for all questions and provide a summary of results.

### Option 2: Individual Question Tests

Each question file contains its own test cases in the `main` method. You can run them individually:

```bash
java Question1_GetLongestString
java Question2_FirstUniqueProduct
java Question3_NearestMinimumDistance
java Question4_Top3CommonWords
java Question5_RotateList
``` 