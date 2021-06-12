package com.github.streamext;

@FunctionalInterface
public interface ThrowablePredicateExt<T,E extends Exception> {
    boolean test(T t) throws E;
}
