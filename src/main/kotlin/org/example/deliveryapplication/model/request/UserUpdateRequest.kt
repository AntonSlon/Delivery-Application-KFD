package org.example.deliveryapplication.model.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class UserUpdateRequest(
    @field:NotNull
    val name: String,
    @field:NotBlank
    @field:Email
    val email: String,
    @field:NotBlank
    val password: String,
    val telephoneNumber: String
)
