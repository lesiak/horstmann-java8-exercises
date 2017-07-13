package ch2_stream_api;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Write a parallel version of the for loop in Section 2.1, “From Iteration to Stream Operations,” on page 22.
 * Obtain the number of processors. Make that many separate threads, each working on a segment of the list,
 * and total up the results as they come in.
 * (You don’t want the threads to update a single counter. Why?)
 */
public class Ch2Ex1ManualParallel {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        ;
        URI file = Ch2Ex1ManualParallel.class.getResource("alice.txt").toURI();
        Path alicePath = Paths.get(file);
        String contents = new String(Files.readAllBytes(alicePath), StandardCharsets.UTF_8); // Read ﬁle into string
        List<String> words = Arrays.asList(contents.split("[\\P{L}]+"));

        //countManualLoop(words);
        //countStreams(words);
        //countStreamsParallel(words);


        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("cores:" + cores);
        countManualParallelLoop(words, cores);

    }

    private static void countManualLoop(List<String> words) {
        int count = 0;
        for (String w : words) {
            if (w.length() > 12) {
                count++;
            }
        }
        System.out.println(count);
    }

    private static void countStreams(List<String> words) {
        long count = words.stream().filter(w -> w.length() > 12).count();
        System.out.println(count);
    }

    private static void countStreamsParallel(List<String> words) {
        long count = words.parallelStream().filter(w -> w.length() > 12).count();
        System.out.println(count);
    }

    private static void countManualParallelLoop(List<String> words, int cores) throws InterruptedException {
        List<List<String>> wordsBatches = splitIntoEqualParts(words,cores);

       int[] counts = new int[wordsBatches.size()];
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < wordsBatches.size(); ++i) {
            int idx = i;
            List<String> wordsBatch = wordsBatches.get(i);
            Runnable r = () -> {
                int count = 0;
                for (String w : wordsBatch) {
                    if (w.length() > 12) {
                        count++;
                    }
                }
                counts[idx] =  count;
            };
            Thread t = new Thread(r);
            t.start();
            threads.add(t);
        }

        for (Thread t : threads)
        {
            t.join();
        }
        int count = IntStream.of(counts).sum();
        System.out.println(count);
    }

    private static <E> List<List<E>> splitIntoEqualParts(List<E> list, int numGroups){
        final int groupSize = (list.size() + numGroups - 1 )/ numGroups;
        
        // old style
        //List<List<E>> result = new ArrayList<>(numGroups);
        //IntStream.range(0, list.size())
        //        .forEach(i -> {
        //            if (i % groupSize == 0) {
        //                result.add(i/groupSize, new ArrayList<>());
        //            }
        //            result.get(i/groupSize).add(list.get(i));
        //        });
        //System.out.println(result);

        List<List<E>> result1 = IntStream.range(0, numGroups)
                .mapToObj(i -> list.subList(groupSize * i, Math.min(groupSize * i + groupSize, list.size())))
                .collect(Collectors.toList());

        return result1;
    }
}
