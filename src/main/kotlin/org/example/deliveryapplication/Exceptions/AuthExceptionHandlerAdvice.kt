package org.example.deliveryapplication.Exceptions

import jakarta.persistence.criteria.CriteriaBuilder.In
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

class UserExistException: RuntimeException()

class IncorrectAuthCode: RuntimeException()

class UserDoesntExistException: RuntimeException()

class IncorrectPasswordException: RuntimeException()

@ControllerAdvice
class AuthExceptionHandlerAdvice{
    @ExceptionHandler(UserExistException::class)
    fun handleAuthException(e: UserExistException): ResponseEntity<ApiError>{
        val apiError = ApiError(HttpStatus.BAD_REQUEST.value(), e::class.java.simpleName,"User already exists")
        return ResponseEntity(apiError, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(IncorrectAuthCode::class)
    fun handleAccessDeniedException(e: IncorrectAuthCode): ResponseEntity<ApiError>{
        val apiError = ApiError(HttpStatus.UNAUTHORIZED.value(), e::class.java.simpleName,"Access denied")
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError)
    }

    @ExceptionHandler(UserDoesntExistException::class)
    fun handleUserDoesntExistException(e: UserExistException): ResponseEntity<ApiError> {
        val apiError = ApiError(HttpStatus.BAD_REQUEST.value(), e::class.java.simpleName,"User does not exist")
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError)
    }

    @ExceptionHandler(IncorrectPasswordException::class)
    fun handleInvalidPasswordException(e: IncorrectPasswordException): ResponseEntity<ApiError> {
        val apiError = ApiError(HttpStatus.BAD_REQUEST.value(), e::class.java.simpleName,"Password is invalid")
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError)
    }
}
