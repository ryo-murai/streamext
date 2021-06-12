package com.github.streamext;

public class FunctionExecutionException extends RuntimeException {

    public FunctionExecutionException(Exception e) {
        super(e);
    }
}
