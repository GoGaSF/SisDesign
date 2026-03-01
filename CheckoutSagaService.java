package com.example.saga.core;

import com.example.saga.steps.InventoryStep;
import com.example.saga.steps.PaymentStep;
import com.example.saga.steps.ShippingStep;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckoutSagaService {

    public CheckoutResult runCheckout(CheckoutRequest request) {
        CheckoutContext ctx = new CheckoutContext(request);

        SagaOrchestrator<CheckoutContext> orchestrator = new SagaOrchestrator<>(
                List.of(
                        new PaymentStep(),
                        new InventoryStep(),
                        new ShippingStep()
                )
        );

        SagaOrchestrator.SagaRunResult result = orchestrator.execute(ctx);

        if (result.ok()) {
            return new CheckoutResult(
                    request.orderId(),
                    "SUCCESS",
                    "Checkout completed",
                    result.executedSteps(),
                    result.compensatedSteps(),
                    200
            );
        }

        return new CheckoutResult(
                request.orderId(),
                "FAILED",
                "Checkout failed and compensations executed. Reason: " + result.error(),
                result.executedSteps(),
                result.compensatedSteps(),
                409
        );
    }
}
