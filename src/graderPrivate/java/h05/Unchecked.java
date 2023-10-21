package h05;

import org.tudalgo.algoutils.tutor.general.callable.ObjectCallable;

public class Unchecked {

    public static <T extends Throwable> void propagate(Throwable t) throws T {
        throw (T) t;
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
    public interface UncheckedSupplier<T> {
        T call() throws Throwable;
    }
}
