package com.github.streamext;

@FunctionalInterface
public interface ThrowableConsumerExt<T,E extends Exception> {
    void accept(T t) throws E;
}
