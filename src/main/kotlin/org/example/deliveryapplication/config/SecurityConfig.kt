package org.example.deliveryapplication.config

import org.example.deliveryapplication.security.JwtFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfigurationSource

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    val jwtFilter: JwtFilter,
    val corsConfig: CorsConfigurationSource
){
    @Bean
    fun config(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests{
                http.cors { it.configurationSource(corsConfig) }
                it.requestMatchers("http://localhost:8080/admin/**").hasRole("ADMIN")
                it.requestMatchers("http://localhost:8080/courier/**").hasRole("COURIER")
                it.requestMatchers("http://localhost:8080/users/**").hasAnyRole("COURIER", "CUSTOMER")
                it.requestMatchers("http://localhost:8080/customer/**").hasRole("CUSTOMER")
                it.requestMatchers("http://localhost:8080/auth/**").permitAll()
                it.anyRequest().permitAll()
            }
            .formLogin { it.disable() }
            .csrf{ it.disable() }
            .httpBasic { it.disable() }
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }

    @Bean
    fun getEncoder(): PasswordEncoder = BCryptPasswordEncoder()


}