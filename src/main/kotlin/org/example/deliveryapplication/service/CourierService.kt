package org.example.deliveryapplication.service

import io.jsonwebtoken.JwtBuilder
import org.example.deliveryapplication.database.entity.Order
import org.example.deliveryapplication.database.entity.Rating
import org.example.deliveryapplication.database.entity.Review
//import org.example.deliveryapplication.database.entity.Rating
import org.example.deliveryapplication.model.request.CourierAcceptOrderRequest
import org.example.deliveryapplication.model.request.CourierUpdateOrderStatusRequest
import org.example.deliveryapplication.model.request.CustomerSelectVehicleRequest
import org.example.deliveryapplication.model.response.CourierAcceptOrderResponse
import org.example.deliveryapplication.model.response.CourierCompletedOrderListResponse
import org.example.deliveryapplication.model.response.CourierScoreRequest
import org.example.deliveryapplication.model.response.CourierUpdateOrderListResponse

interface CourierService {
    fun acceptOrder(orderId: Long): CourierAcceptOrderResponse
    fun updateOrderList(): CourierUpdateOrderListResponse //MutableList<CourierUpdateOrderListResponse>
    fun updateCurrentOrderStatus()
    fun completedOrderList(): CourierCompletedOrderListResponse
    fun getBalance(): Int
    fun getMyScore(): CourierScoreRequest
    fun selectVehicle(customerSelectVehicleRequest: CustomerSelectVehicleRequest)
}