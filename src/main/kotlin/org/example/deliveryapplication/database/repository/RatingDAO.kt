package org.example.deliveryapplication.database.repository

import org.example.deliveryapplication.database.entity.Rating
import org.springframework.data.jpa.repository.JpaRepository

interface RatingDAO: JpaRepository<Rating, Long> {
    //fun findByCourierId(courierId: Long): Rating
}