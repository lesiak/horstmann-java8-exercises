package ch1_lambda_expressions;

import java.util.ArrayList;
import java.util.Collection;

/**
 * In the past, you were told that it’s bad form to add methods to an interface
 * because it would break existing code. Now you are told that it’s okay to add
 * new methods, provided you also supply a default implementation. How safe
 * is that? Describe a scenario where the new stream method of the Collection
 * interface causes legacy code to fail compilation. What about binary
 * compatibility? Will legacy code from a JAR file still run?
 */
public class Ch1Ex12DefaultBreakigExistingCode {
    public static void main(String[] args) {

    }

    static class MyList<E> extends ArrayList<E> {

        public MyList(int initialCapacity) {
            super(initialCapacity);
        }

        public MyList() {
        }

        public MyList(Collection<? extends E> c) {
            super(c);
        }

        //does not compile
        //compiles under java7
        //when java7-compiled jar is run under java 8, the code is executed
        //void stream() {
        //     System.out.println("aaa");
        //}

        //https://stackoverflow.com/questions/22618493/does-introducing-a-default-method-to-an-interface-really-preserve-back-compatibi
    }
}
