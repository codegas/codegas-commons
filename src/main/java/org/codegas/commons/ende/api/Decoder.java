package org.codegas.commons.ende.api;

import java.util.function.Function;

@FunctionalInterface
public interface Decoder<T, R> extends Function<T, R> {

    default <V> Decoder<T, V> andThen(Function<? super R, ? extends V> after) {
        return (T t) -> after.apply(apply(t));
    }

    default R applyNullSafe(T t) {
        return t == null ? null : apply(t);
    }

    default R apply(T t) {
        return decode(t);
    }

    R decode(T t);
}
