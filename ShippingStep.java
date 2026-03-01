package com.example.saga.steps;

import com.example.saga.core.CheckoutContext;
import com.example.saga.core.SagaStep;

public class ShippingStep implements SagaStep<CheckoutContext> {

    @Override
    public String name() {
        return "SHIPPING";
    }

    @Override
    public void doAction(CheckoutContext ctx) {
        if (ctx.request.fail() != null && ctx.request.fail().shippingFail()) {
            throw new RuntimeException("Shipping failed: carrier API unavailable");
        }
        ctx.shippingCreated = true;
        ctx.notes.add("shipping_created_" + ctx.request.address());
    }

    @Override
    public void compensate(CheckoutContext ctx) {
        if (ctx.shippingCreated) {
            ctx.shippingCreated = false;
            ctx.notes.add("shipping_cancelled");
        }
    }
}
