package com.github.streamext;

import java.util.function.*;

public class StreamExt {
    // ////////////////////////
    // Predicate extensions
    // ////////////////////////
    public static <T, E extends Exception> Predicate<T> rethrow(ThrowablePredicateExt<T, E> throwablePredicate) {
        return fallback(throwablePredicate, (t,e) -> rethrowActually(e));
    }

    public static <T, E extends Exception> Predicate<T> quiet(ThrowablePredicateExt<T, E> throwablePredicate) {
        return fallback(throwablePredicate, (t,e) -> false);
    }

    public static <T,E extends Exception> Predicate<T> fallback(ThrowablePredicateExt<T, E> throwablePredicate, BiPredicate<T, Exception> fallbackFunction) {
        return t -> {
            try {
                return throwablePredicate.test(t);
            } catch (Exception e) {
                return fallbackFunction.test(t, e);
            }
        };
    }

    // ////////////////////////
    // Mapper extensions
    // ////////////////////////
    public static <T,R,E extends Exception> Function<T,R> rethrow(ThrowableFunctionExt<T, R, E> throwableMapper) {
        return fallback(throwableMapper, (t,e) -> rethrowActually(e));
    }

    public static <T,R,E extends Exception> Function<T,R> quiet(ThrowableFunctionExt<T, R, E> throwableMapper) {
        return fallback(throwableMapper, (t,e) -> null);
    }

    public static <T,R,E extends Exception> Function<T,R> fallback(ThrowableFunctionExt<T, R, E> throwableMapper, BiFunction<T, Exception, R> fallbackFunction) {
        return t -> {
            try {
                return throwableMapper.apply(t);
            } catch (Exception e) {
                return fallbackFunction.apply(t, e);
            }
        };
    }

    // ////////////////////////
    // Consumer extensions
    // ////////////////////////
    public static <T,E extends Exception> Consumer<T> rethrow(ThrowableConsumerExt<T, E> throwableConsumer) {
        return fallback(throwableConsumer, (t,e) -> rethrowActually(e));
    }

    public static <T,E extends Exception> Consumer<T> quiet(ThrowableConsumerExt<T, E> throwableConsumer) {
        return fallback(throwableConsumer, StreamExt::noop);
    }

    public static <T,R,E extends Exception> Consumer<T> fallback(ThrowableConsumerExt<T, E> throwableConsumer, BiConsumer<T, Exception> fallbackFunction) {
        return t -> {
            try {
                throwableConsumer.accept(t);
            } catch (Exception e) {
                fallbackFunction.accept(t, e);
            }
        };
    }

    private static <R> R rethrowActually(Exception e) {
        throw new FunctionExecutionException(e);
    }

    private static <T,E> void noop(T t, E e) {
        // do nothing
    }
}
