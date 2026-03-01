package com.example.saga.core;

import java.util.List;

public record CheckoutRequest(
        String orderId,
        String userId,
        long amountCents,
        List<String> skuList,
        String address,
        FailureConfig fail
) {
    public record FailureConfig(
            boolean paymentFail,
            boolean inventoryFail,
            boolean shippingFail
    ) { }
}
