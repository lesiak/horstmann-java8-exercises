package warburton.ch5_collectors;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Given a Stream where each element is a word, count the number of times each
 * word appears. So, if you were given the following input, you would return a Map
 * of [John → 3, Paul → 2, George → 1]:
 * Stream<String> names = Stream.of("John", "Paul", "George", "John",
 * "Paul", "John");
 */
public class Ch5Ex2WordCount {
    public static void main(String[] args) {
        Stream<String> names = Stream.of("John", "Paul", "George", "John", "Paul", "John");
        Map<String, Long> nameToCount = names.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println(nameToCount);
    }
}
