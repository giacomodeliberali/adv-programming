# Check NPE

The system is composed by a single project containing:
- DTO classes
    -  [Descriptor.java](./Descriptor.java): a container for a single constructor/method NPE check
    -  [CheckNpeResult.java](./CheckNpeResult.java): a wrapper for checking result
- Main class
    -  [CheckNpe.java](./CheckNpe.java): loops all arguments and instantiates a new `CheckNpe(argument)` on which the `check()` method is called. This invocation returns a `CheckNpeResult` containing a list of `Descriptor` for both constructors and methods.
- Test class
    - [TestClass.java](./TestClass.java): a test class used to simulate all possible NPE cases.