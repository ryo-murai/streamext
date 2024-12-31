package com.github.streamext;

/**
 * Represents a {@link java.util.function.Consumer} but throws
 * checked exception(s).
 * @param <T> the type of the input to the operation
 * @param <E> the type of the exception may be thrown
 */
@FunctionalInterface
public interface ThrowableConsumerExt<T,E extends Exception> {
    /**
     * Performs this operation on the given argument.
     *
     * @param t the input argument
     * @throws E the exception
     */
    void accept(T t) throws E;
}
