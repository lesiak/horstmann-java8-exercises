package ch1_lambda_expressions;

import java.io.File;
import java.util.Arrays;

/**
 * Using the listFiles(FileFilter) and isDirectory methods of the java.io.File class,
 * write a method that returns all subdirectories of a given directory. Use a
 * lambda expression instead of a FileFilter object. Repeat with a method
 * expression.
 */
public class Ch1Ex2 {
    public static void main(String[] args) {
        File f = new File(".");
        Arrays.stream(f.listFiles(File::isDirectory)).forEach(System.out::println);
    }


}
