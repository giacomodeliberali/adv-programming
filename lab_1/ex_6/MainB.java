public class MainB {
    public static void main(String[] args) {
        ASuper x = new BSub();
        BSub y = x;
        y.bar();
    }
}

// Does not compile, missing
// cast to BSub at line 4