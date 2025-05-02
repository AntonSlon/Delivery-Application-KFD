package org.example.deliveryapplication.service

import io.jsonwebtoken.JwtBuilder
import jakarta.servlet.http.HttpSession
import org.example.deliveryapplication.model.request.CourierUpdateOrderStatusRequest
import org.example.deliveryapplication.model.request.CustomerConfirmOrderRequest
import org.example.deliveryapplication.model.request.CustomerCreateOrderRequest
import org.example.deliveryapplication.model.request.CustomerFeedbackRequest
import org.example.deliveryapplication.model.response.CustomerConfirmOrderResponse
import org.example.deliveryapplication.model.response.CustomerCreateOrderResponse
import org.example.deliveryapplication.model.response.CustomerOrderRequest

interface CustomerService {
    fun createOrder(customerCreateOrderRequest: CustomerCreateOrderRequest): CustomerCreateOrderResponse
    fun confirmOrder(customerConfirmOrderRequest: CustomerConfirmOrderRequest): CustomerConfirmOrderResponse
    fun feedbackLastOrder(customerFeedbackRequest: CustomerFeedbackRequest)
    fun cancelOrder()
    //fun updateOrder(updateOrderStatusRequest: CourierUpdateOrderStatusRequest)
    fun getCurrentOrder(): CustomerOrderRequest
}