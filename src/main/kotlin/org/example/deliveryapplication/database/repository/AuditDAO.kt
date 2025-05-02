package org.example.deliveryapplication.database.repository

import org.example.deliveryapplication.database.entity.Audit
import org.springframework.data.jpa.repository.JpaRepository

interface AuditDAO: JpaRepository<Audit, Long> {
}