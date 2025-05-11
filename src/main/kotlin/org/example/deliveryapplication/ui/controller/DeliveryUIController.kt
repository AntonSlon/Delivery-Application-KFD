package org.example.deliveryapplication.ui.controller

import org.example.deliveryapplication.model.DeliveryStatus
import org.example.deliveryapplication.service.DeliveryService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/delivery")
class DeliveryUIController(private val deliveryService: DeliveryService) {

    @GetMapping("/{orderId}")
    fun trackDelivery(@PathVariable orderId: Long, model: Model): String {
        val delivery = deliveryService.getDeliveryByOrderId(orderId)
        val statuses = deliveryService.getDeliveryStatuses(orderId)
        
        model.addAttribute("delivery", delivery)
        model.addAttribute("deliveryStatuses", statuses)
        return "delivery"
    }

    @PostMapping("/{orderId}/status")
    fun updateDeliveryStatus(
        @PathVariable orderId: Long,
        @RequestParam status: DeliveryStatus
    ): String {
        deliveryService.updateDeliveryStatus(orderId, status)
        return "redirect:/delivery/$orderId"
    }

    @PostMapping("/{orderId}/courier")
    fun assignCourier(
        @PathVariable orderId: Long,
        @RequestParam courierId: Long
    ): String {
        deliveryService.assignCourier(orderId, courierId)
        return "redirect:/delivery/$orderId"
    }
} 