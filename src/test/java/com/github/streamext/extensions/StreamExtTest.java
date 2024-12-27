package com.github.streamext.extensions;

import com.github.streamext.FunctionExecutionException;
import lombok.experimental.ExtensionMethod;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtensionMethod(StreamExtOps.class)
class StreamExtTest {

    @Test
    void test_mapE() {
        var actual = Stream.of("1", "2", "3")
                .mapE(s -> "0" + s)
                .collect(Collectors.toList());
        assertThat(actual).containsExactly("01", "02", "03");

        assertThatThrownBy(() -> {
            Stream.of("one")
                    .mapE(s -> {
                        throw new RuntimeException("error");
                    })
                    .findFirst();
        }).isInstanceOf(FunctionExecutionException.class).cause().hasMessage("error");

    }
}