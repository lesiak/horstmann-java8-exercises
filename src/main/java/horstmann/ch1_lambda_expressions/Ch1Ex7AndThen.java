package horstmann.ch1_lambda_expressions;

/**
 * Write a static method andThen that takes as parameters two Runnable instances
 * and returns a Runnable that runs the first, then the second. In the main method,
 * pass two lambda expressions into a call to andThen, and run the returned
 * instance.
 */
public class Ch1Ex7AndThen {
    public static void main(String[] args) {
        andThen(() -> System.out.println("msg1"),
                () -> System.out.println("msg2")).run();
    }

    static Runnable andThen(Runnable r1, Runnable r2) {
        return () -> {
            r1.run();
            r2.run();
        };
    }
}
