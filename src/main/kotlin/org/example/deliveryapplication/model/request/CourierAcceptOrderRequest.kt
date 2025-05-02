package org.example.deliveryapplication.model.request

import org.example.deliveryapplication.model.OrderStatus

data class CourierAcceptOrderRequest(
    val status: String,
)
