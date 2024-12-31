package com.github.streamext;

import java.util.function.*;

/**
 * Utility class for the Java Standard Stream API passing through checked exception.
 *
 * <p>
 *  for the {@link Function}-ish, passing to {@link java.util.stream.Stream#map(Function)}
 *  or {@link java.util.stream.Stream#flatMap(Function)}, see
 *  {@link #rethrow(ThrowableFunctionExt)}, {@link #quiet(ThrowableFunctionExt)} and
 *  {@link #fallback(ThrowableFunctionExt, BiFunction)}
 *
 * <p>
 *  for the {@link Predicate}-ish, passing to {@link java.util.stream.Stream#filter(Predicate)}
 *  or {@link java.util.stream.Stream#allMatch(Predicate)} etc, see
 *  {@link #rethrow(ThrowablePredicateExt)}, {@link #quiet(ThrowablePredicateExt)} and
 *  {@link #fallback(ThrowablePredicateExt, BiPredicate)}
 *
 * <p>
 *  for the {@link Consumer}-ish, passing to {@link java.util.stream.Stream#forEach(Consumer)}}
 *  etc, see
 *  {@link #rethrow(ThrowableConsumerExt)} )}, {@link #quiet(ThrowableConsumerExt)} and
 *  {@link #fallback(ThrowableConsumerExt, BiConsumer)}
 */
public class StreamExt {
    /**
     * private constructor to prevent instantiation of this class
     */
    private StreamExt() {

    }

    // ////////////////////////////////////////////////////////////////////////
    // Predicate extensions
    // ////////////////////////////////////////////////////////////////////////

    /**
     * Converts {@link ThrowablePredicateExt} to {@link Predicate}.
     * {@link FunctionExecutionException} thrown when throwablePredicate throws an exception.
     *
     * @param throwablePredicate the predicate that may throw an checked exception.
     * @param <T> the type of object which the predicate tests.
     * @param <E> the type of exception which the predicate throws.
     * @return the {@link Predicate}
     */
    public static <T, E extends Exception> Predicate<T> rethrow(ThrowablePredicateExt<T, E> throwablePredicate) {
        return fallback(throwablePredicate, (t,e) -> rethrowActually(e));
    }

    /**
     * Converts {@link ThrowablePredicateExt} to {@link Predicate}.
     * When throwablePredicate throws an exception, returns a {@link Predicate}
     * which evaluates {@code false}.
     *
     * @param throwablePredicate the predicate that may throw an checked exception.
     * @param <T> the type of object which the predicate tests.
     * @param <E> the type of exception which the predicate throws.
     * @return the {@link Predicate}
     */
    public static <T, E extends Exception> Predicate<T> quiet(ThrowablePredicateExt<T, E> throwablePredicate) {
        return fallback(throwablePredicate, (t,e) -> false);
    }

    /**
     * Converts {@link ThrowablePredicateExt} to {@link Predicate}.
     * When throwablePredicate throws an exception, {@code fallbackFunction} is called to
     * handle the exception.
     *
     * @param throwablePredicate the predicate that may throw an checked exception.
     * @param fallbackFunction the function to be called when throwablePredicate throws an exception.
     *                         It has to return a boolean result from two input parameters:
     *                         the source element and the exception.
     * @param <T> the type of object which the predicate tests.
     * @param <E> the type of exception which the predicate throws.
     * @return the {@link Predicate}
     */
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

    /**
     * Converts {@link ThrowableFunctionExt} to {@link Function}.
     * {@link FunctionExecutionException} thrown when throwableMapper throws an exception.
     *
     * @param throwableMapper the function that may throw an checked exception.
     * @param <T> the type of object which the function apply.
     * @param <R> the type of object which the function returns.
     * @param <E> the type of exception which the function throws.
     * @return the {@link Function}
     */
    public static <T,R,E extends Exception> Function<T,R> rethrow(ThrowableFunctionExt<T, R, E> throwableMapper) {
        return fallback(throwableMapper, (t,e) -> rethrowActually(e));
    }

    /**
     * Converts {@link ThrowableFunctionExt} to {@link Function}.
     * When throwableMapper throws an exception, returns a {@link Function}
     * which maps to {@code null}.
     *
     * @param throwableMapper the function that may throw an checked exception.
     * @param <T> the type of object which the function apply.
     * @param <R> the type of object which the function returns.
     * @param <E> the type of exception which the function throws.
     * @return the {@link Function}
     */
    public static <T,R,E extends Exception> Function<T,R> quiet(ThrowableFunctionExt<T, R, E> throwableMapper) {
        return fallback(throwableMapper, (t,e) -> null);
    }



    /**
     * Converts {@link ThrowableFunctionExt} to {@link Function}.
     * When throwableMapper throws an exception, {@code fallbackFunction} is called to
     * handle the exception.
     *
     * @param throwableMapper the function that may throw an checked exception.
     * @param fallbackFunction the function to be called when throwableMapper throws an exception.
     *                         It has to map a alternate result of T from two input parameters:
     *                         the source element and the exception.
     * @param <T> the type of object which the function apply.
     * @param <R> the type of object which the function returns.
     * @param <E> the type of exception which the function throws.
     * @return the {@link Function}
     */
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

    /**
     * Converts {@link ThrowableConsumerExt} to {@link Consumer}.
     * {@link FunctionExecutionException} thrown when throwableConsumer throws an exception.
     *
     * @param throwableConsumer the consumer that may throw an checked exception.
     * @param <T> the type of object which the consumer accept.
     * @param <E> the type of exception which the consumer throws.
     * @return the {@link Consumer}
     */
    public static <T,E extends Exception> Consumer<T> rethrow(ThrowableConsumerExt<T, E> throwableConsumer) {
        return fallback(throwableConsumer, (t,e) -> rethrowActually(e));
    }

    /**
     * Converts {@link ThrowableConsumerExt} to {@link Consumer}.
     * When throwableConsumer throws an exception, returns a {@link Consumer}
     * which do nothing.
     *
     * @param throwableConsumer the consumer that may throw an checked exception.
     * @param <T> the type of object which the consumer accept.
     * @param <E> the type of exception which the consumer throws.
     * @return the {@link Consumer}
     */
    public static <T,E extends Exception> Consumer<T> quiet(ThrowableConsumerExt<T, E> throwableConsumer) {
        return fallback(throwableConsumer, StreamExt::noop);
    }

    /**
     * Converts {@link ThrowableConsumerExt} to {@link Consumer}.
     * When throwableConsumer throws an exception, {@code fallbackFunction} is called to
     * handle the exception.
     *
     *
     * @param throwableConsumer the consumer that may throw an checked exception.
     * @param fallbackFunction the function to be called when throwableConsumer throws an exception.
     *                         It has to map a alternate result of T from two input parameters:
     *                         the source element and the exception.
     * @param <T> the type of object which the consumer accept.
     * @param <E> the type of exception which the consumer throws.
     * @return the {@link Consumer}
     */
    public static <T,E extends Exception> Consumer<T> fallback(ThrowableConsumerExt<T, E> throwableConsumer, BiConsumer<T, Exception> fallbackFunction) {
        return t -> {
            try {
                throwableConsumer.accept(t);
            } catch (Exception e) {
                fallbackFunction.accept(t, e);
            }
        };
    }

    /**
     * throws the {@link FunctionExecutionException}
     * @param e exception to be wrapped.
     * @param <R> the type of result
     * @return nothing
     */
    private static <R> R rethrowActually(Exception e) {
        throw new FunctionExecutionException(e);
    }

    /**
     * do nothing
     * @param t object
     * @param e exception
     * @param <T> the type of the object
     * @param <E> the type of the exception
     */
    private static <T,E> void noop(T t, E e) {
        // do nothing
    }
}
