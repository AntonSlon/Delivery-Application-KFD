package org.example.deliveryapplication.Exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.sql.SQLIntegrityConstraintViolationException

class CourierStatusException : RuntimeException()

@ControllerAdvice
class CourierExceptionHandlerAdvice {
    @ExceptionHandler(CourierStatusException::class)
    fun handleException(e: CourierStatusException): ResponseEntity<ApiError> {
        val apiError = ApiError(
            HttpStatus.BAD_REQUEST.value(),
            e::class.java.simpleName,
            "Вы не можете взять сразу несколько заказов"
        )
        return ResponseEntity(apiError, HttpStatus.BAD_REQUEST)
    }
    }
