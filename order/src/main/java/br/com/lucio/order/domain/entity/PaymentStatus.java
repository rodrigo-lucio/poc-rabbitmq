package br.com.lucio.order.domain.entity;

public enum PaymentStatus {
    CREATED,
    RECEIVED,
    PROCESSING,
    CONFIRMED,
    UNAUTHORIZED,
    ERROR
}
