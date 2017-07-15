package ch2_stream_api;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Verify that asking for the first five long words does not call the filter method
 * once the fifth long word has been found. Simply log each method call
 */
public class Ch2Ex2LogMethodCalls {
    public static void main(String[] args) throws IOException, URISyntaxException {
        List<String> words = AliceHelper.getAliceWords();
        List<String> longWords = words.stream().filter(Ch2Ex2LogMethodCalls::longWordsFilter)
                .peek(e -> System.out.println("Calling peek in filtered stream"))
                .limit(5)
                .collect(Collectors.toList());
        System.out.println("Streams:" + longWords);
    }

    private static boolean longWordsFilter(String w) {
        boolean result = w.length() > 12;
        System.out.println("Filtering: " + result);
        return result;
    }
}
