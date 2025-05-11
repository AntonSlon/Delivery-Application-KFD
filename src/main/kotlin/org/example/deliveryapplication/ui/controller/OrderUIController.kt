package org.example.deliveryapplication.ui.controller

import org.example.deliveryapplication.model.Order
import org.example.deliveryapplication.model.OrderStatus
import org.example.deliveryapplication.service.OrderService
import org.example.deliveryapplication.service.DeliveryService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@Controller
@RequestMapping("/orders")
class OrderUIController(
    private val orderService: OrderService,
    private val deliveryService: DeliveryService
) {

    @GetMapping
    fun listOrders(model: Model): String {
        model.addAttribute("orders", orderService.getAllOrders())
        return "orders"
    }

    @GetMapping("/{id}")
    fun getOrder(@PathVariable id: Long, model: Model): String {
        val order = orderService.getOrderById(id)
        val delivery = deliveryService.getDeliveryByOrderId(id)
        
        model.addAttribute("order", order)
        model.addAttribute("delivery", delivery)
        return "order-details"
    }

    @PostMapping
    fun createOrder(
        @RequestParam fromAddress: String,
        @RequestParam toAddress: String,
        @RequestParam description: String?
    ): String {
        val order = Order(
            fromAddress = fromAddress,
            toAddress = toAddress,
            description = description,
            status = OrderStatus.PENDING,
            date = LocalDateTime.now()
        )
        orderService.createOrder(order)
        return "redirect:/orders"
    }

    @PostMapping("/{id}/status")
    fun updateOrderStatus(
        @PathVariable id: Long,
        @RequestParam status: OrderStatus
    ): String {
        orderService.updateOrderStatus(id, status)
        return "redirect:/orders/$id"
    }
} 