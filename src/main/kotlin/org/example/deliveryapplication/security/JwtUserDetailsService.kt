package org.example.deliveryapplication.security

import org.example.deliveryapplication.Exceptions.IncorrectPasswordException
import org.example.deliveryapplication.Exceptions.UserExistException
import org.example.deliveryapplication.database.repository.UserDAO
import org.example.deliveryapplication.model.request.UserLoginRequest
import org.example.deliveryapplication.util.CustomUserDetails
import org.springframework.boot.autoconfigure.security.SecurityProperties.User
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class JwtUserDetailsService(
    private val userDAO: UserDAO
): UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val user = userDAO.findByEmail(email) ?: throw UserExistException()
        println("JwtUSerDetailsService")
        val authorities = listOf(SimpleGrantedAuthority("ROLE_${user.role}"))

        return CustomUserDetails(user.id, user.email, authorities)
    }

}