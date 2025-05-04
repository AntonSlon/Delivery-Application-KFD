package org.example.deliveryapplication.mappers

import org.example.deliveryapplication.database.entity.User
import org.example.deliveryapplication.model.request.UserUpdateRequest
import org.springframework.stereotype.Component

@Component
class UserMapper{
    fun updateUser(request: UserUpdateRequest, user: User){
        user.name = request.name
        user.email = request.email
        user.password = request.password
    }
}