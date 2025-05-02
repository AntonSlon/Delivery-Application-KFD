package org.example.deliveryapplication.util

import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.stereotype.Component

@Component
class MailSenderConnector {
    val mailSender = JavaMailSenderImpl()
    fun getConnection(){
        mailSender.host = "smtp.gmail.com"
        mailSender.port = 587
        mailSender.username = "deliveryservicekfd@gmail.com"
        mailSender.password = "gnqx dxaw qoai icjk"
        val props = mailSender.javaMailProperties
        props["mail.transport.protocol"] = "smtp"
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.starttls.enable"] = "true"
        props["mail.debug"] = "true"
    }
}