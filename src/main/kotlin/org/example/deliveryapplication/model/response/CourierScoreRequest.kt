package org.example.deliveryapplication.model.response

data class CourierScoreRequest(
    val id: Long,
    val totalScore: Float,
    val count: Int,
)
