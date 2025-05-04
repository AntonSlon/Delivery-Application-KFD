package org.example.deliveryapplication.controller

import jakarta.servlet.http.HttpSession
import jakarta.validation.Valid
import org.example.deliveryapplication.model.request.CustomerConfirmOrderRequest
import org.example.deliveryapplication.model.request.CustomerCreateOrderRequest
import org.example.deliveryapplication.model.request.CustomerFeedbackRequest
import org.example.deliveryapplication.service.CustomerService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/customers/orders")
class CustomerController(
    private val customerService: CustomerService,
) {
    @PostMapping("/create")
    fun createOrder(@RequestBody customerCreateOrderRequest: CustomerCreateOrderRequest, session: HttpSession) =
        customerService.createOrder(customerCreateOrderRequest)

    @PostMapping("/confirm")
    fun confirmOrder(@RequestBody customerConfirmOrderRequest: CustomerConfirmOrderRequest) = customerService.confirmOrder(customerConfirmOrderRequest)

    @PostMapping("/cancel")
    fun cancelOrder() = customerService.cancelOrder()

    @PostMapping("/feedback")
    fun feedbackOrder(@RequestBody @Valid feedbackRequest: CustomerFeedbackRequest) =
        customerService.feedbackLastOrder(feedbackRequest)

    @GetMapping("/current")
    fun getCurrentOrder() = customerService.getCurrentOrder()

}