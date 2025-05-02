package org.example.deliveryapplication.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.example.deliveryapplication.Exceptions.ApiError
import org.example.deliveryapplication.Exceptions.TokenNotFoundException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import kotlin.jvm.Throws

@Component
class JwtFilter(
    val jwtParser: JwtParser,
    val userDetails: JwtUserDetailsService
) : OncePerRequestFilter() {

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return request.servletPath.equals("/login") || request.servletPath.equals("/register") ||
                request.servletPath.equals("/test")
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (shouldNotFilter(request)) {
            filterChain.doFilter(request, response)
            return
        }

        val authorizationHeader: String? = request.getHeader("Authorization")

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                val token: String = request.getHeader("Authorization").substringAfter("Bearer ")
                val email = jwtParser.extractEmail(token)

                if (SecurityContextHolder.getContext().authentication == null) {
                    val userDetails = userDetails.loadUserByUsername(email)

                    if (jwtParser.validateToken(token)) {
                        val authToken = UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.authorities)

                        SecurityContextHolder.getContext().authentication = authToken
                    }
                }

            }catch (ex: Exception){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.message)
            }
        }

        filterChain.doFilter(request, response)
    }

}