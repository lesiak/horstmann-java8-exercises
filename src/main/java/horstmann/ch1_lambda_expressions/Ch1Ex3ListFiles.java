package horstmann.ch1_lambda_expressions;

import java.io.File;
import java.util.Arrays;

/**
 * Using the list(FilenameFilter) method of the java.io.File class, write a method
 * that returns all files in a given directory with a given extension. Use a lambda
 * expression, not a FilenameFilter. Which variables from the enclosing scope does
 * it capture?
 */
public class Ch1Ex3ListFiles {
    public static void main(String[] args) {
        printFilesWithExtension(new File(".idea"), "xml");
    }

    static void printFilesWithExtension(File directory, String ext) {
        Arrays.stream(listFilesWithExtension(directory, ext)).forEach(System.out::println);
    }

    static File[] listFilesWithExtension(File directory, String ext) {
        return directory.listFiles((dir, name) -> name.endsWith(ext));
    }
}
