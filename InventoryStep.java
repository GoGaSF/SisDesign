package com.example.saga.steps;

import com.example.saga.core.CheckoutContext;
import com.example.saga.core.SagaStep;

public class InventoryStep implements SagaStep<CheckoutContext> {

    @Override
    public String name() {
        return "INVENTORY";
    }

    @Override
    public void doAction(CheckoutContext ctx) {
        if (ctx.request.fail() != null && ctx.request.fail().inventoryFail()) {
            throw new RuntimeException("Inventory failed: out of stock");
        }
        ctx.inventoryReserved = true;
        ctx.notes.add("inventory_reserved_" + ctx.request.skuList().size());
    }

    @Override
    public void compensate(CheckoutContext ctx) {
        if (ctx.inventoryReserved) {
            ctx.inventoryReserved = false;
            ctx.notes.add("inventory_released");
        }
    }
}
