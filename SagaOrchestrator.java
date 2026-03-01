package com.example.saga.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SagaOrchestrator<C> {

    private final List<SagaStep<C>> steps;

    public SagaOrchestrator(List<SagaStep<C>> steps) {
        this.steps = steps;
    }

    public SagaRunResult execute(C ctx) {
        List<String> executed = new ArrayList<>();
        List<String> compensated = new ArrayList<>();
        try {
            for (SagaStep<C> step : steps) {
                step.doAction(ctx);
                executed.add(step.name());
            }
            return SagaRunResult.success(executed, compensated);
        } catch (RuntimeException ex) {
            List<SagaStep<C>> toCompensate = new ArrayList<>(steps.subList(0, executed.size()));
            Collections.reverse(toCompensate);
            for (SagaStep<C> step : toCompensate) {
                try {
                    step.compensate(ctx);
                    compensated.add(step.name());
                } catch (RuntimeException ignored) {
                    // В реальной системе сюда добавляют логирование и алерты.
                }
            }
            return SagaRunResult.failure(ex.getMessage(), executed, compensated);
        }
    }

    public record SagaRunResult(
            boolean ok,
            String error,
            List<String> executedSteps,
            List<String> compensatedSteps
    ) {
        static SagaRunResult success(List<String> executed, List<String> compensated) {
            return new SagaRunResult(true, null, executed, compensated);
        }

        static SagaRunResult failure(String error, List<String> executed, List<String> compensated) {
            return new SagaRunResult(false, error, executed, compensated);
        }
    }
}
