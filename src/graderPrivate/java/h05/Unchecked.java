package h05;

import org.tudalgo.algoutils.tutor.general.callable.Callable;
import org.tudalgo.algoutils.tutor.general.callable.ObjectCallable;

@SuppressWarnings("unchecked")
public class Unchecked {

    public static <T extends Throwable> void propagate(Throwable t) throws T {
        throw (T) t;
    }

    public static Callable uncheckedCallable(UncheckedRunnable callable) {
        return () -> {
            try {
                callable.run();
            } catch (Throwable t) {
                Unchecked.propagate(t);
                throw new RuntimeException("Unreachable");
            }
        };
    }

    public static <T> ObjectCallable<T> uncheckedObjectCallable(UncheckedSupplier<T> callable) {
        return () -> {
            try {
                return callable.call();
            } catch (Throwable t) {
                Unchecked.propagate(t);
                throw new RuntimeException("Unreachable");
            }
        };
    }

    @FunctionalInterface
    public interface UncheckedRunnable {

        void run() throws Throwable;
    }

    @FunctionalInterface
    public interface UncheckedSupplier<T> {

        T call() throws Throwable;

    }
}
