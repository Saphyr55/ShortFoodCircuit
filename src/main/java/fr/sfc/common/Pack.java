package fr.sfc.common;


import java.util.function.Function;

public class Pack<T> {

    private final Function<T, String> toString;
    private final T type;

    public static <T> Pack<T> of(T type, Function<T, String> toString) {
        return new Pack<>(type, toString);
    }

    private Pack(T type, Function<T, String> toString) {
        this.type = type;
        this.toString = toString;
    }

    public T get() {
        return type;
    }

    public Function<T, String> getToString() {
        return toString;
    }

    @Override
    public String toString() {
        return toString.apply(type);
    }

}
