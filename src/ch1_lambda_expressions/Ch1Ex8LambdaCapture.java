package ch1_lambda_expressions;

import java.util.ArrayList;
import java.util.List;

/**
 * What happens when a lambda expression captures values in an enhanced
 * for loop such as this one?
 * String[] names = { "Peter", "Paul", "Mary" };
 * List<Runnable> runners = new ArrayList<>();
 * for (String name : names)
 * runners.add(() -> System.out.println(name));
 * Is it legal? Does each lambda expression capture a different value, or do they
 * all get the last value? What happens if you use a traditional loop for (int i = 0;
 * i < names.length; i++)?
 */
public class Ch1Ex8LambdaCapture {
    public static void main(String[] args) {
        String[] names = {"Peter", "Paul", "Mary"};
        List<Runnable> runners = new ArrayList<>();
        for (String name : names)
            runners.add(() -> System.out.println(name));

        for (Runnable r : runners) {
            r.run();
        }

        List<Runnable> runners1 = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            String name = names[i]; //cannot use i in lambda as it is non-final
            runners1.add(() -> System.out.println(name));
        }
        for (Runnable r : runners1) {
            r.run();
        }

    }
}
