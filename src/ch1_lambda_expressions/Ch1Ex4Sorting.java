package ch1_lambda_expressions;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Given an array of File objects, sort it so that the directories come before the
 * files, and within each group, elements are sorted by path name. Use a lambda
 * expression, not a Comparator.
 */
public class Ch1Ex4Sorting {
    public static void main(String[] args) {
        File[] files = getFilesToProcess();
        Arrays.sort(files, (f1, f2) -> {
            int dirCompare = compareIsDir(f1, f2);
            if (dirCompare!=0) {
                return dirCompare;
            }
            return f1.getAbsolutePath().compareTo(f2.getAbsolutePath());
        });
        Arrays.stream(files).map(File::getAbsolutePath).forEach(System.out::println);
    }

    private static File[] getFilesToProcess() {
        File f = new File(".");
        File[] localFiles = f.listFiles();
        File[] roots = File.listRoots();
        return Stream.concat(Arrays.stream(localFiles), Arrays.stream(roots)).toArray(File[]::new);
    }

    static int compareIsDir(File f1, File f2) {
            if (f1.isDirectory() && !f2.isDirectory()) {
                return -1;
            }
            else if (!f1.isDirectory() && f2.isDirectory()) {
                return 1;
            }
            else {
                return 0;
            }
    }

}