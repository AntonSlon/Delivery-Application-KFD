package org.example.deliveryapplication.Exceptions

data class ApiError(
    val status: Int,
    val error: String,
    val message: String,
)

