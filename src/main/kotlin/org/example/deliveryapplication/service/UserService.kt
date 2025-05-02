package org.example.deliveryapplication.service

import org.example.deliveryapplication.model.request.UserUpdateRequest
import org.example.deliveryapplication.model.response.UserUpdateResponse

interface UserService {
    fun update(request: UserUpdateRequest): UserUpdateResponse
}