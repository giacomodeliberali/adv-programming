package exercises;

import java.util.Collection;
import java.util.stream.Stream;
import java.util.function.Function;

/**
 *
 * @author giacomodeliberali
 */
public class MainDirector {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String[] paths = new String[2];
        paths[0] = "oscar_age_male.csv";
        paths[1] = "oscar_age_female.csv";

        Collection<Winner> winners = WinnerImpl.loadData(paths);

        Stream<Function<Stream<Winner>, Stream<String>>> functions = Stream.of(
                WinnerOperations::extremeWinners
                //,WinnerOperations::extremeWinners
                //,WinnerOperations::multiAwardedFilm
                //,WinnerOperations::oldWinners
        );

        Stream<String> jobResults = WinnerOperations.runJobs(functions, winners);
        
        jobResults.forEach(System.out::println);
    }

}
