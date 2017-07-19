package warburton.ch5_collectors;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Implement Collectors.groupingBy as a custom collector. You don’t need to
 * provide a downstream collector, so just implementing the simplest variant is
 * fine. If you look at the JDK source code, you’re cheating! Hint: you might want
 * to start with public class GroupingBy<T, K> implements Collector<T,
 * Map<K, List<T>>, Map<K, List<T>>>. This is an advanced exercise, so you
 * might want to attempt it last.
 */
public class Ch5Ex2GroupingBy {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("John", "Paul", "Adam", "George", "Simon", "Marek");
        groupByBuiltIn(names);
        groupByManualFunctions(names);
        groupByManualCollector(names);
    }


    private static void groupByBuiltIn(List<String> names) {
        Map<Integer, List<String>> lengthToNamesBuiltin = names.stream().collect(Collectors.groupingBy(String::length));
        System.out.println(lengthToNamesBuiltin);
    }

    private static void groupByManualFunctions(List<String> names) {
        Function<String, Integer> len = String::length;
        Map<Integer, List<String>> lengthToNamesCustom = names.parallelStream().collect(HashMap::new, (map, s) -> {
                    Integer key = len.apply(s);
                    List<String> valueList = map.computeIfAbsent(key, ArrayList::new);
                    valueList.add(s);
                }, (map1, map2) ->
                        map2.forEach((key, values) -> map1.computeIfAbsent(key, ArrayList::new).addAll(values))
        );

        System.out.println(lengthToNamesCustom);
    }

    private static void groupByManualCollector(List<String> names) {

        Map<Integer, List<String>> lengthToNamesCustom = names.parallelStream().collect(new GroupingByCollector<>(String::length));

        System.out.println(lengthToNamesCustom);
    }

    static class GroupingByCollector<T, K> implements Collector<T, Map<K, List<T>>, Map<K, List<T>>> {

        private final Function<T, K> grouppingMappper;

        private final Set<Characteristics> characteristics = EnumSet.of(Characteristics.IDENTITY_FINISH);

        public GroupingByCollector(Function<T, K> grouppingMappper) {
            this.grouppingMappper = grouppingMappper;
        }

        @Override
        public Supplier<Map<K, List<T>>> supplier() {
            return HashMap::new;
        }

        @Override
        public BiConsumer<Map<K, List<T>>, T> accumulator() {
            return (map, value) -> {
                K key = grouppingMappper.apply(value);
                List<T> valueList = map.computeIfAbsent(key, (k) -> new ArrayList<>());
                valueList.add(value);
            };
        }

        @Override
        public BinaryOperator<Map<K, List<T>>> combiner() {
            return (map1, map2) -> {
                map2.forEach((key, values) -> map1.merge(key, values, (map1values, map2values) -> {
                    map1values.addAll(map2values);
                    return map1values;
                }));
                return map1;
            };
        }

        @Override
        public Function<Map<K, List<T>>, Map<K, List<T>>> finisher() {
            return Function.identity();
        }

        @Override
        public Set<Characteristics> characteristics() {
            return characteristics;
        }
    }

}
