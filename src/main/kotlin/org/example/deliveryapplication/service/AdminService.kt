package org.example.deliveryapplication.service

import org.example.deliveryapplication.database.entity.Order
//import org.example.deliveryapplication.database.entity.Review
import org.example.deliveryapplication.database.entity.User

interface AdminService {
    fun getUsers(): List<User>
    fun getUserByID(id: Long): User?
    fun getOrders(): List<Order>
    fun getOrderById(id: Long): Order?
    fun getReviews(): MutableList<Order>
}