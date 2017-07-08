package ch1_lambda_expressions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Form a subclass Collection2 from Collection and add a default method void
 * forEachIf(Consumer<T> action, Predicate<T> filter) that applies action to each
 * element for which filter returns true. How could you use it?
 */
public class Ch1Ex9DefaultMethod {
    public static void main(String[] args) {
        List<Integer> list = IntStream.range(1, 10).boxed().collect(Collectors.toList());
        MyList<Integer> myList = new MyList<>(list);
        myList.forEachIf(System.out::println, x -> x > 6);

    }

    interface Collection1<E> extends Collection<E> {
        default void forEachIf(Consumer<E> action, Predicate<E> filter) {
            this.stream().filter(filter).forEach(action);
        }
    }

    static class MyList<E> extends ArrayList<E> implements Collection1<E> {

        public MyList(int initialCapacity) {
            super(initialCapacity);
        }

        public MyList() {
        }

        public MyList(Collection<? extends E> c) {
            super(c);
        }
    }
}
