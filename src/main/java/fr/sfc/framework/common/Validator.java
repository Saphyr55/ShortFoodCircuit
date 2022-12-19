package fr.sfc.framework.common;

import java.util.Stack;
import java.util.function.Predicate;

public class Validator<T> {

    private final T type;
    private final Stack<Throwable> throwableStack = new Stack<>();

    public static <T>  Validator<T> of(T type) {
        return new Validator<>(type);
    }

    private Validator(T type) {
        this.type = type;
    }

    public Validator<T> validate(Predicate<T> predicate) {
        return validate(predicate, "");
    }

    public Validator<T> invalidate(Predicate<T> predicate) {
        return invalidate(predicate, "");
    }

    public Validator<T> validate(Predicate<T> predicate, String message) {
        if (!predicate.test(type)) throwableStack.add(new IllegalStateException(message));
        return this;
    }

    public Validator<T> invalidate(Predicate<T> predicate, String message) {
        if (predicate.test(type)) throwableStack.add(new IllegalStateException(message));
        return this;
    }

    public T get() {
        if (throwableStack.isEmpty()) return type;
        IllegalStateException e = new IllegalStateException();
        throwableStack.forEach(e::addSuppressed);
        throw e;
    }

}
