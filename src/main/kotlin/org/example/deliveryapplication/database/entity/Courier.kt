package org.example.deliveryapplication.database.entity

import jakarta.persistence.*
import org.example.deliveryapplication.model.courier.Vehicle
import org.springframework.data.domain.AuditorAware
import java.awt.Point


@Entity
@Table(name = "couriers")
class Courier(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "currentLocation")
    val currentLocation: String = "",

    @Column(name = "status")
    var status: String = "",

    @Column(name = "vehicle")
    var vehicle: String = Vehicle.FOOT.name,

    @MapsId
    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    val user: User,

//    @OneToOne
//    @JoinColumn(name = "id", referencedColumnName = "id")
//    var rating: Rating = Rating(),

)