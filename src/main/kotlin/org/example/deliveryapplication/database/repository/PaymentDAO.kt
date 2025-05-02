package org.example.deliveryapplication.database.repository

import org.example.deliveryapplication.database.entity.Payment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PaymentDAO: JpaRepository<Payment, Long> {
}