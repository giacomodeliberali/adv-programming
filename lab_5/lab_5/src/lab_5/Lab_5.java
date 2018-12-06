package lab_5;

import java.util.List;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author giacomodeliberali
 */
public class Lab_5 {

    public static void main(String[] args) {

        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            list.add(i);
        }

        System.out.println(exercise1_sumOdd(list));

        Object[] objs = new Object[3];
        objs[0] = new String("Uno");
        objs[1] = new String("Due");
        objs[2] = new String("Tre");

        Object[] result = exercise3_repl(objs, 3);
        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
        }

        System.out.println(exercise4_titlecase("la mamma è in giro"));

        exercise5_zipWithIndex(objs).map(a -> {
            System.out.println(a.getFirst() + " - " + a.getSecond());
            return a;
        });

    }

    // Write a static method sumOdd that given a List of integers computes the sum of the values that are odd.
    public static int exercise1_sumOdd(List<Integer> list) {
        return list.stream().filter(a -> a % 2 != 0).reduce(0, (a, acc) -> a + acc);
    }

    // The first element of the pair is the number of elements of lst in the range [0.2, Math.PI],
    // and the second element is the average of the values of lst in the range [10, 100].
    public static ImmutablePair<Integer, Double> exercise2_someCalculation(List<Double> list) {
        //return new ImmutablePair(
        //        list.stream().filter(a -> a >= 0.2 && a <= Math.PI).count(),
        //        list.stream().filter(a -> a >= 10 && a <= 100).reduce(0.0, (a, acc) -> a + acc) / list.stream().filter(a -> a >= 10 && a <= 100).count()
        //);

        return new ImmutablePair(
                list.stream().filter(a -> a >= 0.2 && a <= Math.PI).count(),
                list.stream().filter(a -> a >= 10 && a <= 100).parallel().mapToInt(a -> a.intValue()).average().getAsDouble()
        );
    }

    // Write a static method repl that given an array xs of Object and a integer n 
    // returns an array containing the elements of xs replicated n times in any order.
    public static Object[] exercise3_repl(Object[] xs, int n) {
        return Arrays.asList(xs).stream().flatMap(a -> {

            Object[] replica = new Object[n];

            for (int i = 0; i < n; i++) {
                replica[i] = a;
            }

            return Stream.of(replica);

        }).toArray();
    }

    // Write a static method titlecase that given a string s converts it to titlecase by upper-casing the first letter of every word.
    public static String exercise4_titlecase(String s) {
        return Arrays
                .asList(s.split(" "))
                .stream()
                .map(w -> Character.toUpperCase(w.charAt(0)) + w.substring(1))
                .collect(Collectors.joining(" "));
    }

    // Write a static method zipWithIndex that given an array of type T returns a Stream<ImmutablePair<T, Integer>>
    // containing the elements of the array with its indexes. 
    public static <T> Stream<ImmutablePair<T, Integer>> exercise5_zipWithIndex(T[] array) {

        ArrayList<ImmutablePair<T, Integer>> list = new ArrayList<>();

        for (int i = 0; i < array.length; i++) {
            list.add(new ImmutablePair(array[i], i));
        }

        return Stream.of(list);
    }

}
