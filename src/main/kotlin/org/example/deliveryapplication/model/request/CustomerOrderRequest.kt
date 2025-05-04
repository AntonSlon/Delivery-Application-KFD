package org.example.deliveryapplication.model.request

import java.time.LocalDateTime

data class CustomerOrderRequest(
    val id: Long,
    val status: String,
    val startAddress: String,
    val endAddress: String,
    val comment: String,
    val createdAt: LocalDateTime,
)
