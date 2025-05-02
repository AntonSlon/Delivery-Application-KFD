package org.example.deliveryapplication.database.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.springframework.data.annotation.CreatedBy
import java.time.LocalDateTime

@Entity
@Table(name = "reviews")
class Review (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "rating")
    val rating: Int = 0,

    @Lob
    @Column(name = "comment")
    val comment: String = "",

    @Column(name = "created_at")
    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "courier_id")
    val courierId: Long = 0,

    @MapsId
    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    var order: Order,

)