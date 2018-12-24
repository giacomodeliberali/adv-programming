package exercises;

import java.util.Collection;
import java.util.Comparator;
import java.util.OptionalInt;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 *
 * @author giacomodeliberali
 */
public class WinnerOperations {


    /**
     * Returns only the winners who are > 35 years old
     * @param winners The winners stream
     * @return The winners who are > 35 years old
     */
    public static Stream<String> oldWinners(Stream<Winner> winners) {
        return winners
                .filter(w -> w.getWinnerAge() > 35)
                .map(w -> w.getWinnerName()) // Extract only the name
                .sorted();
    }

    /**
     * Returns only the youngest and oldest winners
     * @param winners The winners stream
     * @return The youngest and oldest winners
     */
    public static Stream<String> extremeWinners(Stream<Winner> winners) {
        // Since i close the stream, I need someone who provides a new one
        StreamSupplier<Winner> streamSupplier = new StreamSupplier(winners);
        
        // Get min and max (the operation closes the stream)
        OptionalInt min = streamSupplier.get().mapToInt(w -> w.getWinnerAge()).min();
        OptionalInt max = streamSupplier.get().mapToInt(w -> w.getWinnerAge()).max();

        return streamSupplier.get()
                .filter(w -> w.getWinnerAge() == min.getAsInt() || w.getWinnerAge() == max.getAsInt())
                .map(w -> w.getWinnerName())
                .sorted(Comparator.reverseOrder()); // SOrt by reverse 
    }

    /**
     * Returns only the movies that won twice
     * @param winners The winners stream
     * @return The movies that won twice (male and female actor)
     */
    public static Stream<String> multiAwardedFilm(Stream<Winner> winners) {

        // Since i close the stream, I need someone who provides a new one
        StreamSupplier<Winner> streamSupplier = new StreamSupplier(winners);

        //TODO: better solution than O(n^2)
        return streamSupplier.get()
                .filter(w -> {
                    // Count all others in the stream that are like the current
                    return streamSupplier.get()
                            .filter(p -> p.getFilmTitle().equals(w.getFilmTitle()))
                            .count() == 2; // if that count equals 2, return true and pick this element
                })
                .sorted(Comparator.comparing(Winner::getWinnerAge)) // Compare by winner age with method reference
                .map(w -> w.getWinnerName());
    }

    /**
     * Returns a Stream<U> obtained by concatenating the results of the execution of all the jobs on the data contained in coll
     * @param <T> The type parameter that the functions in the stream accept as input
     * @param <U> The type parameter that the functions returns as output
     * @param jobs The jobs stream which contains the function to execute
     * @param coll The collection used to feed the function in the jobs stream
     * @return A Stream<U> obtained by concatenating the results of the execution of all the jobs on the data contained in coll
     */
    public static <T, U> Stream<U> runJobs(Stream<Function<Stream<T>, Stream<U>>> jobs, Collection<T> coll) {
        // Call all function in the stream with the coll stream and flat map the results
        // since each of that function returns another stream (Since I want Stream<String> and not Stream<Stream<String>>). 
        Stream<U> flatMap = jobs.map(j -> j.apply(coll.stream())).flatMap(Function.identity());

        return flatMap;
    }
}