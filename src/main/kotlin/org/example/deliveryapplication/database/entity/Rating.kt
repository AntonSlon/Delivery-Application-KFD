package org.example.deliveryapplication.database.entity

import jakarta.persistence.*

@Entity(name = "rating")
class Rating(
    @Id
    val id: Long = 0,

    @Column(name = "total_score")
    var totalScore: Float = 0f,

    @Column(name = "count")
    var count: Int = 0,

    @Column(name = "review_id")
    val reviewId: Long = 0,

    @OneToMany
    @JoinColumn(name = "id", referencedColumnName = "review_id")
    var reviews: MutableList<Review> = mutableListOf(),

    @MapsId
    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    val courier: Courier

)