package org.example.deliveryapplication.model.request

data class CourierScoreRequest(
    val id: Long,
    val totalScore: Float,
    val count: Int,
)
