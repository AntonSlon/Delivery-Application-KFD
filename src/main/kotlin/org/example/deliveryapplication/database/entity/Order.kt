package org.example.deliveryapplication.database.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "status")
    var status: String = "",

    @Column(name = "start_address")
    val startAddress: String = "",

    @Column(name = "end_address")
    val endAddress: String = "",

    @Lob
    @Column(name = "comment")
    val comment: String = "",

    @Column(name = "created_at")
    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @ManyToOne
    @JoinColumn(name = "courier_id", referencedColumnName = "id", nullable = true)
    var courier: Courier?,

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    val customer: User,

    @OneToOne
    @JoinColumn(name = "payment_id", referencedColumnName = "id")
    val payment: Payment,

)