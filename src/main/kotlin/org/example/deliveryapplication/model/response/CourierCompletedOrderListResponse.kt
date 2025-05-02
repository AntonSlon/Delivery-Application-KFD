package org.example.deliveryapplication.model.response

import java.sql.Timestamp
import java.time.LocalDateTime

data class CourierCompletedOrderListResponse(
    val orders: MutableList<CourierCompletedResponse>
)

data class CourierCompletedResponse(
    val id: Long,
    val status: String,
    val startAddress: String,
    val endAddress: String,
    val comment: String,
    val createdAt: LocalDateTime,
    val money: Int,
)