package com.example.saga.api;

import com.example.saga.core.CheckoutRequest;
import com.example.saga.core.CheckoutResult;
import com.example.saga.core.CheckoutSagaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    private final CheckoutSagaService sagaService;

    public CheckoutController(CheckoutSagaService sagaService) {
        this.sagaService = sagaService;
    }

    @PostMapping
    public ResponseEntity<CheckoutResult> checkout(@RequestBody CheckoutRequest request) {
        CheckoutResult result = sagaService.runCheckout(request);
        return ResponseEntity.status(result.httpStatus()).body(result);
    }
}
