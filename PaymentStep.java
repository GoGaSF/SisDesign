package com.example.saga.steps;

import com.example.saga.core.CheckoutContext;
import com.example.saga.core.SagaStep;

public class PaymentStep implements SagaStep<CheckoutContext> {

    @Override
    public String name() {
        return "PAYMENT";
    }

    @Override
    public void doAction(CheckoutContext ctx) {
        if (ctx.request.fail() != null && ctx.request.fail().paymentFail()) {
            throw new RuntimeException("Payment failed: card declined");
        }
        ctx.paymentCaptured = true;
        ctx.notes.add("payment_captured_" + ctx.request.amountCents());
    }

    @Override
    public void compensate(CheckoutContext ctx) {
        if (ctx.paymentCaptured) {
            ctx.paymentCaptured = false;
            ctx.notes.add("payment_refunded");
        }
    }
}
