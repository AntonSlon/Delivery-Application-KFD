package org.example.deliveryapplication.database.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "payments")
class Payment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "cost")
    var cost: Int = 0,

    @Column(name = "status")
    var status: String = "",

    @Column(name = "payment_method")
    var paymentMethod: String = "",

    @Column(name = "created_at")
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    val order: Order? = null,
){
}