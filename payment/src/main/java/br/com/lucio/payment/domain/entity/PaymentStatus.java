package br.com.lucio.payment.domain.entity;

public enum PaymentStatus {
    CREATED,
    RECEIVED,
    PROCESSING,
    CONFIRMED,
    UNAUTHORIZED,
    ERROR
}
