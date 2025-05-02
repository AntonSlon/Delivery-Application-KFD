package org.example.deliveryapplication.database.repository

import com.fasterxml.jackson.databind.ObjectMapper
import org.example.deliveryapplication.database.entity.User
import org.example.deliveryapplication.model.request.UserRegisterRequest
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class TempStorage(
    val redisTemplate: RedisTemplate<String, User>,
    private val objectMapper: ObjectMapper
) {

    fun addAuthCode(uuid: String, user: User){
        redisTemplate.opsForValue().set(uuid, user)
        redisTemplate.expire(uuid, 900_000, TimeUnit.MILLISECONDS)
    }

    fun getUser(uuid: String): User?
         = redisTemplate.opsForValue().get(uuid)

    fun deleteAuthCode(email: String)
        = redisTemplate.delete(email)

}