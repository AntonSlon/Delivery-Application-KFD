package org.example.deliveryapplication.controller

import org.example.deliveryapplication.service.AdminService
import org.example.deliveryapplication.service.Impl.AdminServiceImpl
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin")
class AdminController(
    private val adminService: AdminServiceImpl
) {
    @GetMapping("/users")
    fun getUsers() = adminService.getUsers()

    @GetMapping("/user")
    fun getUserById(@RequestParam id: Long) = adminService.getUserByID(id)

    @GetMapping("/orders")
    fun getOrders() = adminService.getOrders()

    @GetMapping("/order")
    fun getOrderById(@RequestParam id: Long) = adminService.getOrderById(id)
}