package com.github.streamext;

@FunctionalInterface
public interface ThrowableFunctionExt<T, R, E extends Exception> {
    R apply(T t) throws E;
}
