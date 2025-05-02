package org.example.deliveryapplication.controller

import jakarta.validation.Valid
import org.example.deliveryapplication.database.entity.User
import org.example.deliveryapplication.graphhopper.GraphhopperService
import org.example.deliveryapplication.model.request.UserUpdateRequest
import org.example.deliveryapplication.model.response.UserUpdateResponse
import org.example.deliveryapplication.service.UserAuthService
import org.example.deliveryapplication.service.UserService
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/users")
class UserController(
    private val userAuthService: UserAuthService,
    private val userService: UserService
) {
    @GetMapping("/users")
    fun getUsers(): List<User> {
        return userAuthService.getAll()
    }

    @PatchMapping("/update")
    fun updateUser(@RequestBody userUpdateRequest: UserUpdateRequest): UserUpdateResponse =
        userService.update(userUpdateRequest)


}