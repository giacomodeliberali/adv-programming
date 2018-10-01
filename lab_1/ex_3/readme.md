# Exercise 3
Run the program `WrongQueue` and inspect its behaviour using `visualvm`. Can you explain the continuos growth of the heap? Find the code causing the bug and fix it.

**Goal**: Using `visualvm` to inspect the memory consumed by a Java program; Reviewing Java code to detect non-trivial errors; Fixing bugs

**Expected output**: One sentence identifying the bug in the code; A revised version of the class with minimal changes to fix the bug.

# Analysis

The the original code the `current` var in `dequeue()` keeps linked to the previous node and it never gets garbage-collected. So we explicitly remove the useless link to permit GC.

![VisualVM](./assets/visualvm.png "VisualVM")

The heap usage of the new implementation