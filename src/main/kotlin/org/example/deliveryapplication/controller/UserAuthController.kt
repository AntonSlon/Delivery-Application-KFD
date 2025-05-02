package org.example.deliveryapplication.controller

import jakarta.validation.Valid
import org.example.deliveryapplication.model.request.UserLoginRequest
import org.example.deliveryapplication.model.request.UserRegisterRequest
import org.example.deliveryapplication.model.response.AuthCodeResponse
import org.example.deliveryapplication.model.response.UserLoginResponse
import org.example.deliveryapplication.model.response.UserRegisterResponse
import org.example.deliveryapplication.service.UserAuthService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class UserAuthController(private val userAuthService: UserAuthService) {
    @PostMapping("/register")
    fun registerUser(@RequestBody @Valid userRegisterRequest: UserRegisterRequest): UserRegisterResponse
        = userAuthService.register(userRegisterRequest)

    @PostMapping("/confirm")
    fun confirmRegister(@RequestParam codeUUID: String): AuthCodeResponse{
        println("$codeUUID - uuid в контроллере")
    return userAuthService.confirmRegister(codeUUID)}

    @PostMapping("/login")
    fun loginUser(@RequestBody @Valid userLoginRequest: UserLoginRequest): UserLoginResponse {
        userAuthService.login(userLoginRequest)
        return UserLoginResponse("hmmm", "test")
    }

}