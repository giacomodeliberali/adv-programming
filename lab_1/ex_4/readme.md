# Exercise 4
The file Strings.java contains classes `String1` and `String2`, that just differ for the way they print the same string in a loop. At the end of the last lecture we inspected the bytecode of the print/println statements, identifying a difference in the allocation of memory.

Run and inspect classes `String1` and `String2` with `visualvm`, comparing in particular the allocation patterns on the heap and the time spent for Garbage Collection. Are they similar or not? Can you explain why? (Hint: inspect the relevant `PrintStream.java`'s println source code; you can browse it also in NetBeans.)

**Goal**: Browsing the Java API source code to identify the resources used by the runtime support during execution.

**Expected output**: One short answer to the first question, supported by a detailed explanation.

# Analysis

The `String1` does not allocate a new heap cell for each string printed in the loop, while the `Strings2` does (strings are immutable, a concatenation results in a new string allocation). In any case the memory distribution remains almost the same thanks to garbage collector that de-allocates the used string after a while.

![VisualVM](./assets/visualvm.png "VisualVM")

The heap usage of the two approaches.