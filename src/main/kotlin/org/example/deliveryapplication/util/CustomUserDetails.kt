package org.example.deliveryapplication.util

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class CustomUserDetails(
    val id: Long,
    val email: String,
    val authorities: List<SimpleGrantedAuthority>
): UserDetails {
    override fun getAuthorities(): Collection<SimpleGrantedAuthority> = authorities
    override fun isEnabled() = true
    override fun getUsername(): String = email
    override fun isCredentialsNonExpired() = true
    override fun getPassword(): String = email
    override fun isAccountNonExpired() = true

}