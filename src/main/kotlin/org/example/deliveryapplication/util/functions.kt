package org.example.deliveryapplication.util

import org.example.deliveryapplication.Exceptions.TokenNotFoundException
import org.springframework.security.core.context.SecurityContextHolder

fun getPrincipal(): CustomUserDetails{
    val context = SecurityContextHolder.getContext().authentication
    println("${context.principal}, type = ${context.principal::class.java.name}")
    println("$context, type = ${context::class.java.name}")
    if (context.principal == "anonymousUser")
        throw ClassCastException("AnonymousUserException")
    if (!context.isAuthenticated)
        throw TokenNotFoundException()
        else return context.principal as CustomUserDetails
}