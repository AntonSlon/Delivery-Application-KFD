package org.example.deliveryapplication.model.request

import jakarta.validation.Constraint
import jakarta.validation.constraints.NotBlank

data class CustomerFeedbackRequest(
    @field: NotBlank
    val comment: String,

    val value: Int
)
