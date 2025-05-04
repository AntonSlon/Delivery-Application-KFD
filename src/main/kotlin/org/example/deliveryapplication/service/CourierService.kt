package org.example.deliveryapplication.service

//import org.example.deliveryapplication.database.entity.Rating
import org.example.deliveryapplication.model.request.CustomerSelectVehicleRequest
import org.example.deliveryapplication.model.response.CourierAcceptOrderResponse
import org.example.deliveryapplication.model.response.CourierCompletedOrderListResponse
import org.example.deliveryapplication.model.request.CourierScoreRequest
import org.example.deliveryapplication.model.response.CourierUpdateOrderListResponse

interface CourierService {
    fun acceptOrder(orderId: Long): CourierAcceptOrderResponse
    fun updateOrderList(): CourierUpdateOrderListResponse //MutableList<CourierUpdateOrderListResponse>
    fun updateCurrentOrderStatus()
    fun getCompletedOrderList(): CourierCompletedOrderListResponse
    fun getBalance(): Int
    fun getMyScore(): CourierScoreRequest
    fun selectVehicle(customerSelectVehicleRequest: CustomerSelectVehicleRequest)
}