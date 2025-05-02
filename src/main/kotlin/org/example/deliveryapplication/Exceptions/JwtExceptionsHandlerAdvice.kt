package org.example.deliveryapplication.Exceptions

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.security.SignatureException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

class TokenNotFoundException: JwtException(null)

@ControllerAdvice
class JwtExceptionsHandlerAdvice{
    @ExceptionHandler(SignatureException::class)
    fun handleSignatureException(e: SignatureException): ResponseEntity<ApiError>{
        val apiError = ApiError(HttpStatus.UNAUTHORIZED.value(), e::class.java.simpleName, "Invalid token")
        return ResponseEntity(apiError, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(ExpiredJwtException::class)
    fun handleExpiredJwtExceptionException(e: ExpiredJwtException): ResponseEntity<ApiError>{
        val apiError = ApiError(HttpStatus.UNAUTHORIZED.value(), e::class.java.simpleName, "Token expired")
        return ResponseEntity(apiError, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(MalformedJwtException::class)
    fun handleMalformedJwtException(e: MalformedJwtException): ResponseEntity<ApiError>{
        val apiError = ApiError(HttpStatus.UNAUTHORIZED.value(), e::class.java.simpleName, "Malformed token")
        return ResponseEntity(apiError, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(TokenNotFoundException::class)
    fun handleTokenNotFoundException(e: TokenNotFoundException): ResponseEntity<ApiError>{
        val apiError = ApiError(HttpStatus.UNAUTHORIZED.value(), e::class.java.simpleName, "Token not found")
        return ResponseEntity(apiError, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(ClassCastException::class)
    fun handleClassCastException(e: ClassCastException): ResponseEntity<ApiError>{
        val apiError = ApiError(HttpStatus.UNAUTHORIZED.value(), e::class.java.simpleName, "Авторизируйтесь")
        return ResponseEntity(apiError, HttpStatus.UNAUTHORIZED)
    }
}