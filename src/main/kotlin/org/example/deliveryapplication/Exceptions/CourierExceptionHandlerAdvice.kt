package org.example.deliveryapplication.Exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.sql.SQLIntegrityConstraintViolationException

@ControllerAdvice
class CourierExceptionHandlerAdvice {
//    @ExceptionHandler(SQLIntegrityConstraintViolationException::class)
//    fun handleException(e: SQLIntegrityConstraintViolationException): ResponseEntity<ApiError> {
//        val apiError = ApiError(HttpStatus.BAD_REQUEST.value(), e::class.java.simpleName, "Возможен только один отзыв на один заказ")
//        return ResponseEntity(apiError, HttpStatus.BAD_REQUEST)
    }
