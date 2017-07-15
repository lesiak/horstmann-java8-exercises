package ch2_stream_api;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Measure the difference when counting long words with a parallelStream instead
 * of a stream. Call System.currentTimeMillis before and after the call, and print the
 * difference. Switch to a larger document (such as War and Peace) if you have
 * a fast computer.
 */
public class Ch2Ex3SimpleTiming {
    public static void main(String[] args) throws IOException, URISyntaxException {
        List<String> words = AliceHelper.getAliceWords();
        timeIt(() -> countStreams(words));
        timeIt(() -> countStreamsParallel(words));
    }


    private static void timeIt(Runnable r) {
        long before = System.currentTimeMillis();
        r.run();
        long after = System.currentTimeMillis();
        System.out.println(after - before);
    }

    private static void countStreams(List<String> words) {
        long count = words.stream().filter(w -> w.length() > 12).count();
        System.out.println("Count:" + count);
    }

    private static void countStreamsParallel(List<String> words) {
        long count = words.parallelStream().filter(w -> w.length() > 12).count();
        System.out.println("Count:" + count);
    }
}
