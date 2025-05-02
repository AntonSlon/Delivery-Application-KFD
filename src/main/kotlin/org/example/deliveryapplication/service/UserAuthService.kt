package org.example.deliveryapplication.service

import org.example.deliveryapplication.database.entity.User
import org.example.deliveryapplication.model.request.UserLoginRequest
import org.example.deliveryapplication.model.request.UserRegisterRequest
import org.example.deliveryapplication.model.request.UserUpdateRequest
import org.example.deliveryapplication.model.response.*
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

interface UserAuthService{
    fun register(request: UserRegisterRequest): UserRegisterResponse
    fun confirmRegister(uuid: String): AuthCodeResponse
    fun getAll(): List<User> //Заменить потом в админскую панель, user не имеет прав
    fun login(request: UserLoginRequest): UserLoginResponse
}