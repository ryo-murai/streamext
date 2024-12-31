package com.github.streamext;

/**
 * Represents a {@link java.util.function.Predicate} but throws
 * checked exception(s).
 *
 * @param <T> the type of the input to the predicate
 * @param <E> the type of the exception may be thrown by the predicate
 */
@FunctionalInterface
public interface ThrowablePredicateExt<T,E extends Exception> {
    /**
     * Evaluates this predicate on the given argument.
     *
     * @param t the input argument
     * @return {@code true} if the input argument matches the predicate,
     * otherwise {@code false}
     * @throws E the exception
     */
    boolean test(T t) throws E;
}
