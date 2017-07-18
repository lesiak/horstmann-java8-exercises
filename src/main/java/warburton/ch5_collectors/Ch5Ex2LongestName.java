package warburton.ch5_collectors;

import sun.security.pkcs11.wrapper.Functions;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Find the artist with the longest name. You should implement this using a Collector
 * and the reduce higher-order function from Chapter 3. Then compare
 the differences in your implementation: which was easier to write and which
 was easier to read? The following example should return "Stuart Sutcliffe":
 Stream<String> names = Stream.of("John Lennon", "Paul McCartney",
 "George Harrison", "Ringo Starr", "Pete Best", "Stuart Sutcliffe");
 */
public class Ch5Ex2LongestName {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("John Lennon", "Paul McCartney",
                "George Harrison", "Ringo Starr", "Pete Best", "Stuart Sutcliffe");

        String longestWithReduce = byReduce(names);
        System.out.println(longestWithReduce);

        String longestWithManualCollect = byManualCollector(names);
        System.out.println(longestWithManualCollect);

        String longestWithCollect = byCollector(names);
        System.out.println(longestWithCollect);
    }



    private static String byReduce(List<String> names) {
        return names
                .stream()
                .reduce((acc, right) -> right.length() > acc.length() ? right : acc).orElseThrow(RuntimeException::new);
    }

    private static String byManualCollector(List<String> names) {
        return names
                .parallelStream()
                .collect(new LongestStringCollector());
    }

    private static String byCollector(List<String> names) {
        return names

                .stream()
                .collect(Collectors.maxBy(Comparator.comparing(String::length)))
                //.max(Comparator.comparing(String::length))
                .orElseThrow(RuntimeException::new);
    }

    static class LongestStringCollector implements Collector<String, StringBuilder, String> {
        @Override
        public Supplier<StringBuilder> supplier() {
            return StringBuilder::new;
        }

        @Override
        public BiConsumer<StringBuilder, String> accumulator() {
           return (sbAcc,s1) -> {
               if (sbAcc.length() < s1.length()) {
                   sbAcc.replace(0, sbAcc.length(), s1);
               }
           };
        }

        @Override
        public BinaryOperator<StringBuilder> combiner() {
            return (sb1, sb2)-> {
                if (sb1.length() < sb2.length()) {
                    return sb2;
                } else {
                    return sb1;
                }
            };
        }

        @Override
        public Function<StringBuilder, String> finisher() {
            return StringBuilder::toString;
        }

        @Override
        public Set<Characteristics> characteristics() {
            return EnumSet.of(Characteristics.CONCURRENT);
        }
    }


}
