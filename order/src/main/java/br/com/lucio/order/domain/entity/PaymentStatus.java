package br.com.lucio.order.domain.entity;

public enum PaymentStatus {
    CREATED,
    PROCESSING,
    WAITING_CONFIRMATION,
    CONFIRMED,
    UNAUTHORIZED
}
