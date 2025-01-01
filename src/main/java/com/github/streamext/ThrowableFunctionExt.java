package com.github.streamext;

/**
 * Represents a {@link java.util.function.Function} but throws
 * checked exception(s).
 * @param <T> the type of the input to the function
 * @param <R> the type of the result of the function
 * @param <E> the type of the exception may be thrown by the function
 */
@FunctionalInterface
public interface ThrowableFunctionExt<T, R, E extends Exception> {
    /**
     * Applies this function to the given argument.
     *
     * @param t the function argument
     * @return the function result
     * @throws E the exception
     */
    R apply(T t) throws E;
}
