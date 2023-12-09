package io.arrogantprogrammer.domain;

public record OrderRecord(String name, MenuItem menuItem, OrderStatus orderStatus, PaymentStatus paymentStatus, Long id) {
}
