package org.example.deliveryapplication.model.response

data class UserRegisterResponse(
    val id: Long,
    val name: String,
    val email: String,
    val status: String
)
