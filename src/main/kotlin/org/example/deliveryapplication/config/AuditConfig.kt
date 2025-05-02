package org.example.deliveryapplication.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

@Configuration
@EnableJpaAuditing
class AuditConfig {
    @Bean
    fun auditorProvider(): AuditorAware<String> {
        return AuditorAware {
            when (val auth = SecurityContextHolder.getContext().authentication) {
                null -> Optional.of("anonymousUser")
                else -> Optional.of(auth.principal.toString())
            }
        }
    }
}