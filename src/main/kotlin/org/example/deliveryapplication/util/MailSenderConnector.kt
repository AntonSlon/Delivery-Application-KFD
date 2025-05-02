package org.example.deliveryapplication.util

import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.stereotype.Component

@Component
class MailSenderConnector(@Value("\${mail-sender.secret}") val secret: String = "") {
    val mailSender = JavaMailSenderImpl()
    fun getConnection(){
        mailSender.host = "smtp.gmail.com"
        mailSender.port = 587
        mailSender.username = "deliveryservicekfd@gmail.com"
        mailSender.password = secret
        val props = mailSender.javaMailProperties
        props["mail.transport.protocol"] = "smtp"
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.starttls.enable"] = "true"
        props["mail.debug"] = "true"
    }
}