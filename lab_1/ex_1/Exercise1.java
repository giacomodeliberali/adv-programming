import java.util.Arrays;

public class Exercise1 {
    public static void main(String args[]) {
        // Sort the args parameters by lexicographical order
        Arrays.sort(args);
        System.out.printf("Sorted args[] : %s\n", Arrays.toString(args));
    }
}