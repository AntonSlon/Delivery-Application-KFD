package org.example.deliveryapplication.model.response

data class CourierUpdateOrderListResponse(
    val orders: MutableList<OrderResponse>
)

data class OrderResponse(
    val id: Long,
    val status: String,
    val startAddress: String,
    val endAddress: String,
    val comment: String,
)

//data class CourierUpdateOrderListResponse(
//    val id: Long,
//    val status: String,
//    val startAddress: String,
//    val endAddress: String,
//    val comment: String,
//)