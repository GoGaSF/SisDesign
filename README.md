Checkout Saga (single microservice)

This project demonstrates the Saga pattern implemented inside a single Spring Boot microservice.
The checkout workflow has three steps: Payment, Inventory, and Shipping.
Each step implements two actions: doAction() and compensate().

The SagaOrchestrator runs steps in order and records which steps completed successfully.
If any step throws an exception, the orchestrator compensates all previously completed steps in reverse order.
This models typical microservice Saga behavior without using distributed ACID transactions.

Run:
mvn spring-boot:run

Test success:
POST /checkout
{
  "orderId":"o-1",
  "userId":"u-1",
  "amountCents":1999,
  "skuList":["SKU-1","SKU-2"],
  "address":"Almaty",
  "fail":{"paymentFail":false,"inventoryFail":false,"shippingFail":false}
}

Test failure in shipping (forces compensation of inventory then payment):
POST /checkout
{
  "orderId":"o-2",
  "userId":"u-1",
  "amountCents":1999,
  "skuList":["SKU-1","SKU-2"],
  "address":"Almaty",
  "fail":{"paymentFail":false,"inventoryFail":false,"shippingFail":true}
}
