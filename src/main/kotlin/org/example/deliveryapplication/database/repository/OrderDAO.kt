package org.example.deliveryapplication.database.repository

import org.example.deliveryapplication.database.entity.Courier
import org.example.deliveryapplication.database.entity.Order
import org.example.deliveryapplication.database.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderDAO: JpaRepository<Order, Long> {
    fun findByIdAndStatus(id: Long, status: String): Order
    fun findByStatus(status: String): MutableList<Order>
    fun findByCustomerIdAndStatus(userId: Long, status: String): MutableList<Order>
    fun findFirstByOrderByIdDesc(): Order
    fun findByStatusAndCourierId(status: String, courierId: Long): MutableList<Order>
}