package com.example.saga.core;

import java.util.List;

public record CheckoutResult(
        String orderId,
        String status,
        String message,
        List<String> executedSteps,
        List<String> compensatedSteps,
        int httpStatus
) { }
