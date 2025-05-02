package org.example.deliveryapplication.model.request

data class CustomerCreateOrderRequest(
    val startAddress: String,
    val endAddress: String,
    val comment: String,
)