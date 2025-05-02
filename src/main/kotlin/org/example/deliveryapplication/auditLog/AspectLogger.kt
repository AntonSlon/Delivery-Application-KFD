package org.example.deliveryapplication.auditLog

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.example.deliveryapplication.database.entity.Audit
import org.example.deliveryapplication.database.entity.User
import org.example.deliveryapplication.database.repository.AuditDAO
import org.example.deliveryapplication.database.repository.CourierDAO
import org.example.deliveryapplication.database.repository.UserDAO
import org.example.deliveryapplication.util.getPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Aspect
@Component
class AspectLogger(
    private val auditDAO: AuditDAO,
) {
    @Before("execution(* org.example.deliveryapplication.service.Impl.CourierServiceImpl.*(..))")
    fun logCourierBeforePointcut(joinPoint: JoinPoint) {
        val audit = Audit(
            userId = getPrincipal().id,
            action = joinPoint.signature.name,
        )
        auditDAO.save(audit)
    }

    @Before("execution(* org.example.deliveryapplication.service.Impl.CustomerServiceImpl.*(..))")
    fun logCustomerBeforePointcut(joinPoint: JoinPoint) {
        val audit = Audit(
            userId = getPrincipal().id,
            action = joinPoint.signature.name,
        )
        auditDAO.save(audit)
    }

}