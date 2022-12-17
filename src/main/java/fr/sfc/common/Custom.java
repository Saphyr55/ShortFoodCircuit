package fr.sfc.common;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public final class Custom<T> {

    private final Function<T, String> toString;
    private final T type;

    public static <T> Custom<T> of(@NotNull final T type,
                                   @NotNull final Function<T, String> toString) {
        return new Custom<>(type, toString);
    }

    private Custom(@NotNull final T type,
                   @NotNull final Function<T, String> toString) {
        this.type = type;
        this.toString = toString;
    }

    public @NotNull T get() {
        return type;
    }

    public @NotNull Function<T, String> getToString() {
        return toString;
    }

    @Override
    public @NotNull String toString() {
        return toString.apply(type);
    }

}
