package org.example.deliveryapplication.database.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime


@EntityListeners(AuditingEntityListener::class)
@Table(name = "audit_log")
class Audit(
    @CreatedBy
    @Column(name = "created_by")
    var createdBy: Long? = null,

    @CreatedDate
    @Column(name = "created_date")
    var createdDate: LocalDateTime? = null,

    @LastModifiedBy
    @Column(name = "updated_date")
    var updatedDate: LocalDateTime? = null,

    @LastModifiedDate
    @Column(name = "updated_at")
    var changedAt: LocalDateTime? = null,

    @Column(name = "entity_id")
    var entity: Long? = null,

    @Column(name = "entity_name")
    var entityName: String? = null,

)