package com.github.streamext.extensions;

import com.github.streamext.ThrowableConsumerExt;
import com.github.streamext.ThrowableFunctionExt;
import com.github.streamext.ThrowablePredicateExt;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.streamext.StreamExt.*;

public class StreamExtOps {
    public static <T,E extends Exception> Stream<T> filterE(Stream<T> stream, ThrowablePredicateExt<T,E> predicate) {
        return stream.filter(rethrow(predicate));
    }

    public static <T,E extends Exception> Stream<T> filterE(Stream<T> stream, ThrowablePredicateExt<T,E> predicate, BiPredicate<T,Exception> fallbackFunction) {
        return stream.filter(fallback(predicate, fallbackFunction));
    }

    public static <T,E extends Exception> Stream<T> filterQuiet(Stream<T> stream, ThrowablePredicateExt<T,E> predicate) {
        return stream.filter(quiet(predicate));
    }

    public static <T,E extends Exception> boolean allMatchE(Stream<T> stream, ThrowablePredicateExt<T,E> predicate) {
        return stream.allMatch(rethrow(predicate));
    }

    public static <T,E extends Exception> boolean allMatchE(Stream<T> stream, ThrowablePredicateExt<T,E> predicate, BiPredicate<T,Exception> fallbackFunction) {
        return stream.allMatch(fallback(predicate, fallbackFunction));
    }

    public static <T,E extends Exception> boolean allMatchQuiet(Stream<T> stream, ThrowablePredicateExt<T,E> predicate) {
        return stream.allMatch(quiet(predicate));
    }

    public static <T,E extends Exception> boolean anyMatchE(Stream<T> stream, ThrowablePredicateExt<T,E> predicate) {
        return stream.anyMatch(rethrow(predicate));
    }

    public static <T,E extends Exception> boolean anyMatchE(Stream<T> stream, ThrowablePredicateExt<T,E> predicate, BiPredicate<T,Exception> fallbackFunction) {
        return stream.anyMatch(fallback(predicate, fallbackFunction));
    }

    public static <T,E extends Exception> boolean anyMatchQuiet(Stream<T> stream, ThrowablePredicateExt<T,E> predicate) {
        return stream.anyMatch(quiet(predicate));
    }

    public static <T,E extends Exception> boolean nonMatchE(Stream<T> stream, ThrowablePredicateExt<T,E> predicate) {
        return stream.noneMatch(rethrow(predicate));
    }

    public static <T,E extends Exception> boolean nonMatchE(Stream<T> stream, ThrowablePredicateExt<T,E> predicate, BiPredicate<T,Exception> fallbackFunction) {
        return stream.noneMatch(fallback(predicate, fallbackFunction));
    }

    public static <T,E extends Exception> boolean nonMatchQuiet(Stream<T> stream, ThrowablePredicateExt<T,E> predicate) {
        return stream.noneMatch(quiet(predicate));
    }

    public static <T,R,E extends Exception> Stream<R> mapE(Stream<T> stream, ThrowableFunctionExt<T,R,E> mapper) {
        return stream.map(rethrow(mapper));
    }

    public static <T,R,E extends Exception> Stream<R> mapE(Stream<T> stream, ThrowableFunctionExt<T,R,E> mapper, BiFunction<T, Exception, R> fallbackFunction) {
        return stream.map(fallback(mapper, fallbackFunction));
    }

    public static <T,R,E extends Exception> Stream<R> mapQuiet(Stream<T> stream, ThrowableFunctionExt<T,R,E> mapper) {
        return stream.map(quiet(mapper));
    }

    public static <T,R,E extends Exception> Stream<R> flatMapE(Stream<T> stream, ThrowableFunctionExt<T,Stream<R>,E> mapper) {
        return stream.flatMap(rethrow(mapper));
    }

    public static <T,R,E extends Exception> Stream<R> flatMapE(Stream<T> stream, ThrowableFunctionExt<T,Stream<R>,E> mapper, BiFunction<T, Exception, Stream<R>> fallbackFunction) {
        return stream.flatMap(fallback(mapper, fallbackFunction));
    }

    public static <T,R,E extends Exception> Stream<R> flatMapQuiet(Stream<T> stream, ThrowableFunctionExt<T,Stream<R>,E> mapper) {
        return stream.flatMap(fallback(mapper, (t,e) -> Stream.empty()));
    }

    public static<T,E extends Exception> void forEachE(Stream<T> stream, ThrowableConsumerExt<T,E> consumer) {
        stream.forEach(rethrow(consumer));
    }

    public static<T,E extends Exception> void forEachE(Stream<T> stream, ThrowableConsumerExt<T,E> consumer, BiConsumer<T,Exception> fallbackFunction) {
        stream.forEach(fallback(consumer, fallbackFunction));
    }

    public static<T,E extends Exception> void forEachQuiet(Stream<T> stream, ThrowableConsumerExt<T,E> consumer) {
        stream.forEach(quiet(consumer));
    }

    public static<T,E extends Exception> void forEachOrderedE(Stream<T> stream, ThrowableConsumerExt<T,E> consumer) {
        stream.forEachOrdered(rethrow(consumer));
    }

    public static<T,E extends Exception> void forEachOrderedE(Stream<T> stream, ThrowableConsumerExt<T,E> consumer, BiConsumer<T,Exception> fallbackFunction) {
        stream.forEachOrdered(fallback(consumer, fallbackFunction));
    }

    public static<T,E extends Exception> void forEachOrderedQuiet(Stream<T> stream, ThrowableConsumerExt<T,E> consumer) {
        stream.forEachOrdered(quiet(consumer));
    }

    public static <T> List<T> list(Stream<T> stream) {
        return stream.collect(Collectors.toList());
    }
}
