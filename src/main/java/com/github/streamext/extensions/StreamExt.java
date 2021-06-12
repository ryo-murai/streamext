package com.github.streamext.extensions;

import com.github.streamext.FunctionExecutionException;
import com.github.streamext.ThrowableFunctionExt;

import java.util.function.Function;
import java.util.stream.Stream;

public class StreamExt {
    public static <T,R,E extends Exception> Stream<R> mapE(Stream<T> stream, ThrowableFunctionExt<T,R,E> mapper) {
        return stream.map(rethrow(mapper));
    }


    private static <T,R,E extends Exception> Function<T,R> rethrow(ThrowableFunctionExt<T,R,E> throwableMapper) {
        return t -> {
            try {
                return throwableMapper.apply(t);
            } catch (Exception e) {
                throw new FunctionExecutionException(e);
            }
        };
    }
}
