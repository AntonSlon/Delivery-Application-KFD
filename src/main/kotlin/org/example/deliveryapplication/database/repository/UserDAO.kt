package org.example.deliveryapplication.database.repository
import org.example.deliveryapplication.database.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserDAO: JpaRepository<User, Long>{
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): User?
}