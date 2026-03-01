package com.example.saga.core;

public interface SagaStep<C> {
    String name();
    void doAction(C ctx);
    void compensate(C ctx);
}
