package org.example.deliveryapplication.Exceptions

import feign.FeignException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

class OrderIsNull: RuntimeException()

class IncorrectRatingException: RuntimeException()

@ControllerAdvice
class CustomerExceptionsHandlerAdvice {
    @ExceptionHandler(OrderIsNull::class)
    fun handleOrderIsNull(e: OrderIsNull): ResponseEntity<ApiError> {
        val apiError = ApiError(HttpStatus.NOT_FOUND.value(), e::class.java.simpleName, "Текущих заказов не найдено")
        return ResponseEntity(apiError, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(FeignException::class)
    fun handleFeignException(e: FeignException): ResponseEntity<ApiError> {
        val apiError = ApiError(HttpStatus.BAD_REQUEST.value(), e::class.java.simpleName, "Ошибка геолокации")
        return ResponseEntity(apiError, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(IncorrectRatingException::class)
    fun handleIncorrectRatingException(e: IncorrectRatingException): ResponseEntity<ApiError> {
        val apiError = ApiError(HttpStatus.BAD_REQUEST.value(), e:: class.java.simpleName, "Неккоректный рейтинг")
        return ResponseEntity(apiError, HttpStatus.BAD_REQUEST)
    }
}