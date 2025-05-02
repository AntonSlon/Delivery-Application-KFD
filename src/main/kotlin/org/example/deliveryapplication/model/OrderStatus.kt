package org.example.deliveryapplication.model

enum class OrderStatus {
    WAITING,
    IN_PROGRESS,
    WAITING_FOR_CONFIRM,
    DELIVERED,
    FAILED,
    CANCELED
}