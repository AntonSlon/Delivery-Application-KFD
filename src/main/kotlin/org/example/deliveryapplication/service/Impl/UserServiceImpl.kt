package org.example.deliveryapplication.service.Impl

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.example.deliveryapplication.database.repository.CourierDAO
import org.example.deliveryapplication.database.repository.UserDAO
import org.example.deliveryapplication.mappers.UserMapper
import org.example.deliveryapplication.model.request.UserUpdateRequest
import org.example.deliveryapplication.model.response.UserUpdateResponse
import org.example.deliveryapplication.security.JwtFilter
import org.example.deliveryapplication.security.JwtParser
import org.example.deliveryapplication.service.UserService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service


@Service
class UserServiceImpl(private val userDAO: UserDAO,
                      private val jwtParser: JwtParser,
                      private val httpServletRequest: HttpServletRequest,
    private val userMapper: UserMapper
) : UserService {
    override fun update(request: UserUpdateRequest): UserUpdateResponse {
        val token = jwtParser.getTokenFromCookie(httpServletRequest)
        val user = userDAO.findByEmail(jwtParser.extractEmail(token))

        userMapper.updateUser(request, user!!)
        return UserUpdateResponse("Success")
    }

    fun getCurrentUserId(): Long {
        val principal = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val user = userDAO.findByEmail(principal.username)
        return user!!.id
    }

}