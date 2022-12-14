package fr.sfc.common;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Function;

public final class Pack<T> {

    private final Function<T, String> toString;
    private final T type;

    public static <T> Pack<T> of(@NotNull final T type,
                                 @NotNull final Function<T, String> toString) {
        return new Pack<>(type, toString);
    }

    private Pack(@NotNull final T type,
                 @NotNull final Function<T, String> toString) {
        this.type = type;
        this.toString = toString;
    }

    public Optional<T> asOptional() {
        return Optional.of(type);
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
