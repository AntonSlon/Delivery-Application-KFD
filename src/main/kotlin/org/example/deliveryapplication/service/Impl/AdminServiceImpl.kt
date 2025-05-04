package org.example.deliveryapplication.service.Impl

import org.example.deliveryapplication.database.entity.Order
import org.example.deliveryapplication.database.entity.User
import org.example.deliveryapplication.database.repository.OrderDAO
import org.example.deliveryapplication.database.repository.UserDAO
import org.example.deliveryapplication.service.AdminService
import org.springframework.stereotype.Service

@Service
class AdminServiceImpl(
    private val userDAO: UserDAO,
    private val orderDAO: OrderDAO,
): AdminService{
    override fun getUserByID(id: Long): User? = userDAO.findById(id).get()
    override fun getUsers(): List<User> = userDAO.findAll()
    override fun getOrderById(id: Long): Order? = orderDAO.findById(id).get()
    override fun getOrders(): List<Order> = orderDAO.findAll()
    override fun getReviews(): MutableList<Order> = orderDAO.findAll()

}