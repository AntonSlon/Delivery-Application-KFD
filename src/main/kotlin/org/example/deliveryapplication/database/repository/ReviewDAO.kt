package org.example.deliveryapplication.database.repository

import org.example.deliveryapplication.database.entity.Review
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewDAO: JpaRepository<Review, Long> {
    override fun findAll(): List<Review>
    fun findByCourierId(courierId: Long): MutableList<Review>
    //fun findByCourierId(courierId: Long): MutableList<Review>
}