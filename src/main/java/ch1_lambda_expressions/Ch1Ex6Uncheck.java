package ch1_lambda_expressions;

/**
 * Didn’t you always hate it that you had to deal with checked exceptions in a
 * Runnable? Write a method uncheck that catches all checked exceptions and turns
 * them into unchecked exceptions. For example,
 *  new Thread(uncheck(
 *  () -> { System.out.println("Zzz"); Thread.sleep(1000); })).start();
 *  // Look, no catch (InterruptedException)!
 * Hint: Define an interface RunnableEx whose run method may throw any exceptions.
 * Then implement public static Runnable uncheck(RunnableEx runner). Use a
 * lambda expression inside the uncheck function.
 * Why can’t you just use Callable<Void> instead of RunnableEx?
 */
public class Ch1Ex6Uncheck {
    public static void main(String[] args) {
        new Thread(uncheck(() -> { System.out.println("Zzz"); Thread.sleep(1000); })).start();
    }

    private static Runnable uncheck(RunnableEx mayThrowRunnable) {
        return () -> {
            try {
                mayThrowRunnable.run();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }


    @FunctionalInterface
    public interface RunnableEx {
       void run() throws Exception;
    }
}
