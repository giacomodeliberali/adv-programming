# Analysis

The `finalize()` method, called by garbage collector insert the current instance into a static linked list, saved in the non-heap memory of the JVM. In this case the GC can not delete the object since its reference is saved in a per-thread shared memory area. To properly count the numbers of `finalize()` calls, instead of inserting the current instance, simply increment a shared counter.

## Memory usage
The above plot refers to the original source code, with the insertion of the current instance in a linked list by the `finalize()` method, while the bottom plot refers to the modified code with the increment of a interger variable inside the `finalize()`. In the bottom we can see the garbage collector de-allocating memory (the descending peaks in the middle and at the end).

![VisualVM](./assets/visualvm.png "VisualVM")

## Finalizer thread

Here we can see that the finalize thread has been active twice the time in the second implementation rather than the first (`2,888ms` vs `1,528ms`).

![VisualVM](./assets/finalizer.png "VisualVM")