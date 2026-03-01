package com.example.saga.core;

import java.util.HashSet;
import java.util.Set;

public class CheckoutContext {
    public final CheckoutRequest request;

    public boolean paymentCaptured;
    public boolean inventoryReserved;
    public boolean shippingCreated;

    public final Set<String> notes = new HashSet<>();

    public CheckoutContext(CheckoutRequest request) {
        this.request = request;
    }
}
