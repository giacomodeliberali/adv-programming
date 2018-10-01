public class MainC {
    public static void main(String[] args) {
        BSub x = new BSub();
        ASuper y = x;
        y.tee();
        x.tee();
    }
}

// Does not compile, tee()
// is not a valid method of ASuper at line 6