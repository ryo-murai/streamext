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

    public static <T,E extends Exception> Stream<T> filterSilent(Stream<T> stream, ThrowablePredicateExt<T,E> predicate) {
        return stream.filter(silent(predicate));
    }

    public static <T,E extends Exception> boolean allMatchE(Stream<T> stream, ThrowablePredicateExt<T,E> predicate) {
        return stream.allMatch(rethrow(predicate));
    }

    public static <T,E extends Exception> boolean allMatchE(Stream<T> stream, ThrowablePredicateExt<T,E> predicate, BiPredicate<T,Exception> fallbackFunction) {
        return stream.allMatch(fallback(predicate, fallbackFunction));
    }

    public static <T,E extends Exception> boolean allMatchSilent(Stream<T> stream, ThrowablePredicateExt<T,E> predicate) {
        return stream.allMatch(silent(predicate));
    }

    public static <T,E extends Exception> boolean anyMatchE(Stream<T> stream, ThrowablePredicateExt<T,E> predicate) {
        return stream.anyMatch(rethrow(predicate));
    }

    public static <T,E extends Exception> boolean anyMatchE(Stream<T> stream, ThrowablePredicateExt<T,E> predicate, BiPredicate<T,Exception> fallbackFunction) {
        return stream.anyMatch(fallback(predicate, fallbackFunction));
    }

    public static <T,E extends Exception> boolean anyMatchSilent(Stream<T> stream, ThrowablePredicateExt<T,E> predicate) {
        return stream.anyMatch(silent(predicate));
    }

    public static <T,E extends Exception> boolean nonMatchE(Stream<T> stream, ThrowablePredicateExt<T,E> predicate) {
        return stream.noneMatch(rethrow(predicate));
    }

    public static <T,E extends Exception> boolean nonMatchE(Stream<T> stream, ThrowablePredicateExt<T,E> predicate, BiPredicate<T,Exception> fallbackFunction) {
        return stream.noneMatch(fallback(predicate, fallbackFunction));
    }

    public static <T,E extends Exception> boolean nonMatchSilent(Stream<T> stream, ThrowablePredicateExt<T,E> predicate) {
        return stream.noneMatch(silent(predicate));
    }

    public static <T,R,E extends Exception> Stream<R> mapE(Stream<T> stream, ThrowableFunctionExt<T,R,E> mapper) {
        return stream.map(rethrow(mapper));
    }

    public static <T,R,E extends Exception> Stream<R> mapE(Stream<T> stream, ThrowableFunctionExt<T,R,E> mapper, BiFunction<T, Exception, R> fallbackFunction) {
        return stream.map(fallback(mapper, fallbackFunction));
    }

    public static <T,R,E extends Exception> Stream<R> mapSilent(Stream<T> stream, ThrowableFunctionExt<T,R,E> mapper) {
        return stream.map(silent(mapper));
    }

    public static <T,R,E extends Exception> Stream<R> flatMapE(Stream<T> stream, ThrowableFunctionExt<T,Stream<R>,E> mapper) {
        return stream.flatMap(rethrow(mapper));
    }

    public static <T,R,E extends Exception> Stream<R> flatMapE(Stream<T> stream, ThrowableFunctionExt<T,Stream<R>,E> mapper, BiFunction<T, Exception, Stream<R>> fallbackFunction) {
        return stream.flatMap(fallback(mapper, fallbackFunction));
    }

    public static <T,R,E extends Exception> Stream<R> flatMapSilent(Stream<T> stream, ThrowableFunctionExt<T,Stream<R>,E> mapper) {
        return stream.flatMap(fallback(mapper, (t,e) -> Stream.empty()));
    }

    public static<T,E extends Exception> void forEachE(Stream<T> stream, ThrowableConsumerExt<T,E> consumer) {
        stream.forEach(rethrow(consumer));
    }

    public static<T,E extends Exception> void forEachE(Stream<T> stream, ThrowableConsumerExt<T,E> consumer, BiConsumer<T,Exception> fallbackFunction) {
        stream.forEach(fallback(consumer, fallbackFunction));
    }

    public static<T,E extends Exception> void forEachSilent(Stream<T> stream, ThrowableConsumerExt<T,E> consumer) {
        stream.forEach(silent(consumer));
    }

    public static<T,E extends Exception> void forEachOrderedE(Stream<T> stream, ThrowableConsumerExt<T,E> consumer) {
        stream.forEachOrdered(rethrow(consumer));
    }

    public static<T,E extends Exception> void forEachOrderedE(Stream<T> stream, ThrowableConsumerExt<T,E> consumer, BiConsumer<T,Exception> fallbackFunction) {
        stream.forEachOrdered(fallback(consumer, fallbackFunction));
    }

    public static<T,E extends Exception> void forEachOrderedSilent(Stream<T> stream, ThrowableConsumerExt<T,E> consumer) {
        stream.forEachOrdered(silent(consumer));
    }

    public static <T> List<T> list(Stream<T> stream) {
        return stream.collect(Collectors.toList());
    }
}
