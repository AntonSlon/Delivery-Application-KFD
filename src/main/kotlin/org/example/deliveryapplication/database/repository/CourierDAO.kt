package org.example.deliveryapplication.database.repository

import org.example.deliveryapplication.database.entity.Courier
import org.springframework.data.jpa.repository.JpaRepository

interface CourierDAO: JpaRepository<Courier, Long> {
}