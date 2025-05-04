package org.example.deliveryapplication.database.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime


//@EntityListeners(AuditingEntityListener::class)
@Entity
@Table(name = "audit_log")
class Audit(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "id_user")
    val userId: Long = 0,

    @Column(name = "action")
    val action: String = "",

    @Column(name = "created_at")
    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now()

)