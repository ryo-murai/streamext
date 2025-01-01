package com.github.streamext;

/**
 * Exception thrown when running a combinator function to operate
 * {@link java.util.stream.Stream} that failed by throwing an exception.
 * This exception can be inspected using the {@link #getCause()} method.
 */
public class FunctionExecutionException extends RuntimeException {

    /**
     * Constructs an {@code FunctionExecutionException} with the specified cause.
     * The detail message is set to {@code (cause == null ? null :
     * cause.toString())} (which typically contains the class and
     * detail message of {@code cause}).
     *
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method)
     */
    public FunctionExecutionException(Exception cause) {
        super(cause);
    }
}
