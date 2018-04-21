package org.codegas.commons.lang.function;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public final class FunctionUtil {

    private FunctionUtil() {

    }

    public static <T, R> Function<T, R> memoize(Function<T, R> function) {
        return new Function<T, R>() {

            Map<T, R> memoMap = new HashMap<>();

            @Override
            public R apply(T t) {
                return memoMap.computeIfAbsent(t, key -> function.apply(t));
            }
        };
    }

    public static <T, U, R> BiFunction<T, U, R> memoize(BiFunction<T, U, R> biFunction) {
        return new BiFunction<T, U, R>() {

            Map<T, Map<U, R>> memoMap = new HashMap<>();

            @Override
            public R apply(T t, U u) {
                return memoMap.computeIfAbsent(t, key -> new HashMap<>()).computeIfAbsent(u, key -> biFunction.apply(t, u));
            }
        };
    }

    public static <T, R extends Invertible<? extends R>> BiFunction<T, T, R> inverseMemoize(BiFunction<T, T, R> biFunction) {
        return new BiFunction<T, T, R>() {

            Map<T, Map<T, R>> memoMap = new HashMap<>();

            @Override
            public R apply(T t1, T t2) {
                R result = memoMap.computeIfAbsent(t1, key -> new HashMap<>()).computeIfAbsent(t2, key -> biFunction.apply(t1, t2));
                memoMap.computeIfAbsent(t2, key -> new HashMap<>()).computeIfAbsent(t1, key -> result.invert());
                return result;
            }
        };
    }
}
