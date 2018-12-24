package exercises;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author giacomodeliberali
 */
public class WinnerImpl implements Winner {

    public int year;
    public int index;
    public int age;
    public String name;
    public String movie;

    public WinnerImpl(int year, int index, int age, String name, String movie) {
        this.year = year;
        this.index = index;
        this.age = age;
        this.name = name;
        this.movie = movie;
    }

    /**
     * Reads the files and concatenate their parsed content
     * @param paths The file paths
     * @return The concatenation of the files parsed content
     */
    public static Collection<Winner> loadData(String[] paths) {

        Collection<Winner> winners = new ArrayList<>();
        
        for(String p : paths){
            winners.addAll(loadFromCsvFile(p));
        }
        
        return winners;

    }

    /**
     * Read the CSV file provided and parse its content
     * @param path The file path
     * @return The collection containing the parsed content of the file
     */
    private static Collection<Winner> loadFromCsvFile(String path) {
        FileReader fileReader = null;
        Collection<Winner> winners = new ArrayList<>();
        try {
            String line = null;

            fileReader = new FileReader(path);

            BufferedReader bufferedReader = new BufferedReader(fileReader);
            bufferedReader.readLine(); // skip heading

            while ((line = bufferedReader.readLine()) != null) {

                winners.add(parseFromCsvLine(line));
            }
            bufferedReader.close();
        } catch (Exception ex) {
            Logger.getLogger(WinnerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fileReader.close();
            } catch (IOException ex) {
                Logger.getLogger(WinnerImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

            return winners;
        }
    }

    /**
     * Parse the CSV string line to create a Winner instance
     * @param line The raw string line
     * @return The Winner object representing that line
     */
    private static Winner parseFromCsvLine(String line) {
        String[] split = line.split(",");

        int index = Integer.parseInt(split[0]);
        int year = Integer.parseInt(split[1]);
        int age = Integer.parseInt(split[2]);
        String name = split[3].replaceAll("\"", "");
        String movie = split[4].replaceAll("\"", "");

        return new WinnerImpl(year, index, age, name, movie);
    }

    @Override
    public int getYear() {
        return this.year;
    }

    @Override
    public int getWinnerAge() {
        return this.age;
    }

    @Override
    public String getWinnerName() {
        return this.name;
    }

    @Override
    public String getFilmTitle() {
        return this.movie;
    }

}
