package org.example.deliveryapplication.controller

import org.example.deliveryapplication.model.request.CustomerSelectVehicleRequest
import org.example.deliveryapplication.model.response.CourierCompletedOrderListResponse
import org.example.deliveryapplication.service.CourierService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/couriers")
class CourierController(
    private val courierService: CourierService,
) {
    @PostMapping("/orders/accept")
    fun acceptOrder(@RequestParam orderId: Long) = courierService.acceptOrder(orderId)

    @GetMapping("/orders")
    fun updateOrderList() = courierService.updateOrderList()

    @GetMapping("/orders/me")
    fun getCompletedOrders(): CourierCompletedOrderListResponse =
       courierService.getCompletedOrderList()

    @GetMapping("/balance")
    fun getBalance(): Int = courierService.getBalance()

    @PostMapping("/orders/update")
    fun updateOrderStatus() = courierService.updateCurrentOrderStatus()

    @PostMapping("/vehicle")
    fun selectVehicle(@RequestBody courierSelectVehicleRequest: CustomerSelectVehicleRequest) {
        courierService.selectVehicle(courierSelectVehicleRequest)
    }

    @GetMapping("/score")
    fun getMyScore() = courierService.getMyScore()
}